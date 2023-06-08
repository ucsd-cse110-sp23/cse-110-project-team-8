package cse110.server;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;
import org.bson.Document;

import java.io.*;
import java.util.*;


public class RequestHandler implements HttpHandler {

    public RequestHandler() {
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }

        //Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        Document userData = Document.parse(postData);
        JsonObject response = DBWrite.setUserData(userData);

        System.out.println(response.toString());
        scanner.close();
        return response.get("response").getAsString();
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        return handlePut(httpExchange);
    }

}