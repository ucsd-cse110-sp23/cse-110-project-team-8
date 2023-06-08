package cse110.middleware;

import java.io.*;
import java.net.*;


/**
 * Send Http server requests to /response endpoint.
 */
public class ResponseCommunication extends ServerCommunication {
  public static final String responseURL = URL+"response";

  /**
   * Given a prompt, returns the response to that prompt
   * @param prompt
   * @param maxTokens
   * @return String response to the given prompt
   */
  public static String sendResponseRequest(String prompt, int maxTokens) {
    StringBuilder promptBuilder = new StringBuilder();
    // Set prompt as valid string first
    for (int i = 0 ; i < prompt.length(); i++) {
      if (prompt.charAt(i) == ' ') {
        promptBuilder.append('+');
      } else {
        promptBuilder.append(prompt.charAt(i));
      }
    }
    prompt = promptBuilder.toString();

    try {
        // Setup the server address with queries for prompt and tokens
        URL url = new URL(responseURL + "?prompt=" + prompt + "&tokens=" + maxTokens);

        // Create a HttpURLConnection object
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set method to GET
        conn.setRequestMethod("GET");

        // Get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("GET Response Code: " + responseCode);

        // Get response from server
        BufferedReader reader = new BufferedReader(
          new InputStreamReader(conn.getInputStream())
        );
        String response = reader.readLine();
        String in;
        while ((in = reader.readLine())!=null){
          response+=in;
        }
        reader.close();

        System.out.println("Response: " + response);
        return response;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return ResponseStrings.SERVER_ERROR;
  }
}
