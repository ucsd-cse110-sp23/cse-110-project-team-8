package cse110.middleware;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.*;


public class ServerCommunication {
  public static final String URL = "http://localhost:8100/";
  public static final String responseURL = URL+"response";

  public static boolean isConnectError(Exception e) {
    return e instanceof ConnectException;
  }

  /**
   * Saves the prompt and response in data file
   * @param prompt
   * @param response
   */
  public static void sendPostRequest(JsonObject userData) {
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
        out.write(userData.toString());
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
