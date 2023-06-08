package cse110.middleware;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;


public class AccountCommunication extends ServerCommunication {
  public static final String accountURL = URL+"account";

    /**
     * Creates account or returns error message if invalid info
     * @param username
     * @param password
     * @return Message string
     */
    public static JsonObject sendCreateRequest(String username, String password) {
        JsonObject json = new JsonObject();
        try {
            // Setup the server address
            URL url = new URL(accountURL);

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
            jsonObj.addProperty("username", username);
            jsonObj.addProperty("password", password);
            out.write(jsonObj.toString());
            out.flush();

            // Get the response code
            int responseCode = conn.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

            out.close();

            // Get response from server
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
            );
            JsonObject response = JsonParser.parseReader(reader).getAsJsonObject();
            System.out.println("Response: " + response.toString());
            reader.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.addProperty("response", ResponseStrings.SERVER_ERROR);
        return json;
    }

    /**
     * Checks login info
     * @param username
     * @param password
     * @return Message string 
     */
    public static JsonObject sendLoginRequest(String username, String password) {
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
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0 ; i < password.length(); i++) {
        if (password.charAt(i) == ' ') {
            passwordBuilder.append('+');
        } else {
            passwordBuilder.append(password.charAt(i));
        }
        }
        password = passwordBuilder.toString();

        JsonObject json = new JsonObject();
        try {
            // Setup the server address with queries for prompt and tokens
            URL url = new URL(accountURL+ "?username=" + username + "&password=" + password);

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
            
            JsonObject response = JsonParser.parseReader(reader).getAsJsonObject();
            System.out.println("Response: " + response.toString());
            reader.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.addProperty("response", ResponseStrings.SERVER_ERROR);
        return json;
    }
}
