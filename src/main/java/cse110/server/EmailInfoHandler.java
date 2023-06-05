package cse110.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.*;

import cse110.middleware.EmailInfo;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class EmailInfoHandler implements HttpHandler {
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

    /**
     * Put email info into database
     * @param httpExchange
     * @return 
     * @throws IOException
     */
    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        JsonObject jsonObj = JsonParser.parseString(postData).getAsJsonObject();
        EmailInfo ei = EmailInfo.fromJson(jsonObj);
        
        // Delete from database, then create new document
        DeleteEmailDB.deleteEmailInfo(ei.getUserId());
        String response = CreateEmailDB.addEmailInfo(ei);

        scanner.close();
        return response;
    }
    
    /**
     * Get email info from database.
     * @param httpExchange
     * @return
     * @throws IOException
     */
    private String handleGet(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        // Get prompt 
        String userId = "";
        if (query != null) {
            userId = query.substring(query.indexOf("userId=") + "userId=".length());
        }

        if (!ReadEmailDB.existsEmailInfo(userId)) {
            // No Email info for this account
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("error", "No email info for this account");
            return jsonObj.toString();
        }

        // Return email info as json object as string
        return ReadEmailDB.getEmailInfo(userId).toJson().toString();
    }
}