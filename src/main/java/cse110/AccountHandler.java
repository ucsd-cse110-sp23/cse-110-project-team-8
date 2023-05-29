package cse110;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;


public class AccountHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
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
            response = e.toString();
            e.printStackTrace();
        }

        // Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        JsonObject jsonObj = JsonParser.parseString(postData).getAsJsonObject();

        String username = jsonObj.get("username").toString();
        String password = jsonObj.get("password").toString();
        
        String response = CreateDB.addUser(username, password);
        System.out.println(response);
        scanner.close();

        return response;
    }
    
    private String handleGet(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        // Get prompt 
        String username = "";
        String password = "";
        if (query != null) {
            username = query.substring(query.indexOf("username=") + "username=".length(), query.indexOf("&password="));
            password = query.substring(query.indexOf("tokens=")+"tokens=".length());
        }

        return ReadDB.checkLogin(username, password);
    }
}