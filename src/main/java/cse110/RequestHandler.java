package cse110;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.util.*;


public class RequestHandler implements HttpHandler {

    public RequestHandler() {
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(httpExchange);
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

    private String handleClear() throws IOException {
        DataManager.setData(new ArrayList<QuestionData>());
        if (!DataManager.saveData()) return "Could not save cleared data";
        return "Cleared data";
    }

    private String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid DELETE request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String stringValue = query.substring(query.indexOf("=") + 1);
            if (stringValue.equals("all")) {
                return this.handleClear();
            }

            int value = Integer.parseInt(stringValue);

            // Value should be index of DataManger.data
            if (DataManager.removeData(value)) {
                response = "Deleted entry {" + value + "}";
                System.out.println(response);
            } else {
                response = "Could not delete data at index " + value;
            }
        }
        return response;
    }

    private String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String prompt = postData.substring(
            0,
            postData.indexOf(",")
        ), answer = postData.substring(postData.indexOf(",") + 1);

        String response = "";
        if (DataManager.existsData(prompt)) {
            response = "Updated entry {" + prompt + ", " + answer + "}";
        } else {
            response = "Added entry {" + prompt + ", " + answer + "}";
        }
        // Store data 
        if (!DataManager.addData(new QuestionData(prompt, answer))) {
            response = "Could not save added data.";
        }

        System.out.println(response);
        scanner.close();

        return response;
    }

    private String handleGetAll() throws IOException {
        ArrayList<QuestionData> list = DataManager.getData();
        Gson gson = new Gson();
        String gsonList = gson.toJson(list);
        System.out.println("gson list: " + gsonList);
        return gsonList;
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            if (value.equals("all")) {
                return handleGetAll();
            }

            int index = Integer.parseInt(value);
            System.out.println("found index: "+index);
            int dataSize = DataManager.getData().size();
            if (index<dataSize && index >= dataSize) return "No data for this index";

            QuestionData qd = DataManager.getData().get(index); // Retrieve data 
            // Set response to be returned as json object
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("prompt", qd.getPrompt());
            jsonObj.addProperty("response", qd.getResponse());
            System.out.println("jsobob: " +jsonObj.toString());

            response = jsonObj.toString();
            System.out.println(response);
        }
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        JsonObject jsonObj = JsonParser.parseString(postData).getAsJsonObject();

        String prompt = jsonObj.get("prompt").getAsString();
        String answer = jsonObj.get("response").getAsString();
        if (!DataManager.addData(new QuestionData(prompt, answer))) {
            scanner.close();
            return "Could not add data";
        }

        String response = "Posted entry {" + prompt + ", " + answer + "}";
        System.out.println(response);
        scanner.close();

        return response;
    }

}