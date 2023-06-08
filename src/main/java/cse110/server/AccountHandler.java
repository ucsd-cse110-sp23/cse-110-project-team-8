package cse110.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.*;
import org.bson.Document;

import cse110.middleware.ResponseStrings;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class AccountHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        JsonObject response;
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = new JsonObject();
            response.addProperty("response",ResponseStrings.SERVER_INVALID_ERROR);
            e.printStackTrace();
        }

        // Sending back response to the client
        String responsestring = response.toString();
        httpExchange.getResponseHeaders().set("Content-Type","application/json");
        httpExchange.sendResponseHeaders(200, responsestring.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(responsestring.getBytes());
        outStream.close();
        
    }

    private JsonObject handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        JsonObject jsonObj = JsonParser.parseString(postData).getAsJsonObject();
        scanner.close();

        String username = jsonObj.get("username").getAsString();
        String password = jsonObj.get("password").getAsString();

        JsonObject response = DBRead.getUserData(username, password);
        System.out.println(response.toString());
        if (!response.get("response").getAsString().equals(ResponseStrings.USERNAME_ERROR)){
            response = new JsonObject();
            response.addProperty("response", ResponseStrings.USERNAME_TAKEN);
            return response;
        }
        Document userData = new Document()
            .append("username", username)
            .append("password",password);

        return DBWrite.setUserData(userData);
    }
    
    private JsonObject handleGet(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        // Get prompt 
        String username = "";
        String password = "";
        if (query != null) {
            username = query.substring(query.indexOf("username=") + "username=".length(), query.indexOf("&password="));
            password = query.substring(query.indexOf("password=")+"password=".length());
        }

        JsonObject response = new JsonObject();
        JsonObject databaseresponse = DBRead.getUserData(username, password);
        if (databaseresponse.get("response").getAsString().equals(ResponseStrings.DATABASE_READ_SUCCESS)){
            response.addProperty("response", ResponseStrings.LOGIN_SUCCESS);
            response.add("body",databaseresponse.get("body"));
        }else{
            response.addProperty("response", databaseresponse.get("response").getAsString());
        }

        return response;
    }
}