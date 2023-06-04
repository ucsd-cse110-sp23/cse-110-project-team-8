package cse110.middleware;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;


public class EmailInfoCommuncation extends ServerCommunication {
    public static final String emailInfoURL = URL+"emailInfo";

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
