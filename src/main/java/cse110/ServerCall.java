package cse110;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class ServerCall {
  public static final String URL = "http://localhost:8100/";

  public static void sendClearRequest() {
    try {
        // Setup the server address
        URL url = new URL(URL + "?=" + "all");

        // Create a HttpURLConnection object
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set method to CLEAR 
        conn.setRequestMethod("DELETE");

        // Get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("DELETE Response Code: " + responseCode);

    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  public static ArrayList<QuestionData> sendGetAllRequest() {
    try {
        // Setup the server address
        URL url = new URL(URL + "?=" + "all");

        // Create a HttpURLConnection object
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set method to POST
        conn.setRequestMethod("GET");

        // Get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("GET Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(
          new InputStreamReader(conn.getInputStream())
        );
        String response = in.readLine();
        in.close();

        Gson gson = new Gson();
        return gson.fromJson(response,new TypeToken<ArrayList<QuestionData>>() {}.getType());
    } catch (Exception e) {
        e.printStackTrace();
    }
    return new ArrayList<QuestionData>();
}

  public static QuestionData sendGetRequest(int index) {
    try {
        // Setup the server address
        URL url = new URL(URL + "?=" + String.valueOf(index));

        // Create a HttpURLConnection object
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set method to POST
        conn.setRequestMethod("GET");

        // Get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("GET Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(
          new InputStreamReader(conn.getInputStream())
        );
        String response = in.readLine();
        in.close();

        JsonObject jsonObj = JsonParser.parseString(response).getAsJsonObject();
        System.out.println("returned object is: " + jsonObj.toString());
        return new QuestionData(jsonObj.get("prompt").toString(), jsonObj.get("response").toString());
    } catch (Exception e) {
        e.printStackTrace();
    }
    return new QuestionData("invalid prompt", "invalid response");
}

  public static void sendRemoveRequest(int index) {
    try {
        // Setup the server address
        URL url = new URL(URL + "?=" + String.valueOf(index));

        // Create a HttpURLConnection object
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set method to POST
        conn.setRequestMethod("DELETE");

        // Get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("DELETE Response Code: " + responseCode);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

  public static void sendPostRequest(String prompt, String response) {
    try {
        // Setup the server address
        URL url = new URL(URL);

        // Create a HttpURLConnection object
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set method to POST
        conn.setRequestMethod("POST");

        // To send a POST request, we must set DoOutput to true
        conn.setDoOutput(true);

        // Write the request content
        OutputStreamWriter out = new OutputStreamWriter(
              conn.getOutputStream()
            );
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("prompt", prompt);
        jsonObj.addProperty("response", response);
        out.write(jsonObj.toString());
        out.flush();
        out.close();

        // Get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("POST Response Code: " + responseCode);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
