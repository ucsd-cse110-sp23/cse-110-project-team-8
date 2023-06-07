package cse110.middleware;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.*;


public class ServerCommunication {
  public static final String URL = "http://localhost:8100/";
  public static final String transcriptionURL = URL+"transcribe";
  public static final String responseURL = URL+"response";

  public static boolean isConnectError(Exception e) {
    return e instanceof ConnectException;
  }

  public static boolean checkServerConnection() {
    try {
        // Setup the server address with queries for prompt and tokens
        URL url = new URL(URL);

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
    } catch (Exception e) {
        e.printStackTrace();
        return !isConnectError(e);
    }
    return true;
  }

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
    return "";
  }

  /**
   * Given an audio file, returns the text transcription of the audio
   * @param filePath
   * @return Text transcription of the Audio
   */
  public static String sendTranscribeRequest(String filePath) {
    try {
        // Setup the server address
        URL url = new URL(transcriptionURL + "?=" + filePath);

        // Create a HttpURLConnection object
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set method to GET
        conn.setRequestMethod("GET");

        // Get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("GET Response Code: " + responseCode);

        // Get response from server
        BufferedReader in = new BufferedReader(
          new InputStreamReader(conn.getInputStream())
        );
        String response = in.readLine();
        in.close();

        System.out.println("Transcribed prompt: " + response);
        return response;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "";
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
