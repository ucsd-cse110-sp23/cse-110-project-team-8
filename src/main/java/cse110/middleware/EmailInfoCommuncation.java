package cse110.middleware;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;


/**
 * Send Http server requests to /emailInfo endpoint.
 */
public class EmailInfoCommuncation extends ServerCommunication {
    public static final String emailInfoURL = URL+"emailInfo";

    public static JsonObject sendGetEmailInfo(String username) {
        // Set username and password as valid string first (in case they have space ' ')
        StringBuilder usernameBuilder = new StringBuilder();
        for (int i = 0 ; i < username.length(); i++) {
        if (username.charAt(i) == ' ') {
            usernameBuilder.append('+');
        } else {
            usernameBuilder.append(username.charAt(i));
        }
        }
        username = usernameBuilder.toString();

        try {
            // Setup the server address with queries for prompt and tokens
            URL url = new URL(emailInfoURL + "?userId=" + username);

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
            return JsonParser.parseString(response).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("error", "Could not get info");
        return jsonObj;
    }

    public static String sendEmailInfo(EmailInfo ei) {
        try {
            // Setup the server address
            URL url = new URL(emailInfoURL);

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
            JsonObject jsonObj = ei.toJson();
            out.write(jsonObj.toString());
            out.flush();
            out.close();

            // Get the response code
            int responseCode = conn.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

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
        return "Server Error";
    }
}
