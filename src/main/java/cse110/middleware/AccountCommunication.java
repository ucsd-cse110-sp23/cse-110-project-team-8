package cse110.middleware;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.*;


public class AccountCommunication extends ServerCommunication {

    /**
     * Creates account or returns error message if invalid info
     * @param username
     * @param password
     * @return Message string
     */
    public static String sendCreateRequest(String username, String password) {
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

    /**
     * Checks login info
     * @param username
     * @param password
     * @return Message string 
     */
    public static String sendLoginRequest(String username, String password) {
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
