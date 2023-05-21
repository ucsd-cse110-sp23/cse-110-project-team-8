package cse110;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;


public class ResponseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
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
    
    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        // Get prompt 
        String prompt = "";
        int tokens = 0;
        if (query != null) {
            prompt = query.substring(query.indexOf("prompt=") + 7, query.indexOf("&tokens="));
            tokens = Integer.parseInt(query.substring(query.indexOf("tokens=")+7));
        }

        // Encode prompt
        StringBuilder promptBuilder = new StringBuilder();
        // Set prompt as valid string first
        for (int i = 0 ; i < prompt.length(); i++) {
            if (prompt.charAt(i) == '+') {
            promptBuilder.append(' ');
            } else {
            promptBuilder.append(prompt.charAt(i));
            }
        }
        prompt = promptBuilder.toString();
        System.out.println("prompt: " + prompt);

        // Use ChatGPT API to get response
        try {
            response = ChatGPT.getResponse(prompt, tokens);
            System.out.println("ChatGPT response" + response);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            response = "Error: Couldn't get response";
        }

        return response;
    }
}