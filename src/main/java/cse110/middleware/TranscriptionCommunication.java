package cse110.middleware;

import java.io.*;
import java.net.*;


/**
 * Send Http server requests to /transcribe endpoint.
 */
public class TranscriptionCommunication extends ServerCommunication {
  public static final String transcriptionURL = URL+"transcribe";

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
    return ResponseStrings.SERVER_ERROR;
  }
}
