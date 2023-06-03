package cse110;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

import java.net.URLEncoder;


@TestMethodOrder(OrderAnnotation.class)
public class ServerTest {
    private static final int SERVER_PORT = 8100;
    private static final String SERVER_HOSTNAME = "localhost";
    private static HttpServer server;

    @BeforeAll
    static void setUp() {
        try {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

            // create a server
            server = HttpServer.create(
                new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
                0
            );
            server.createContext("/", new RequestHandler());
            server.setExecutor(threadPoolExecutor);
            server.start();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        if (server != null) {
            server.stop(0);
        }
        DataManager.setData(new ArrayList<>());
    }

    @Test
    public void testPostRequest() {
        HttpClient client = HttpClient.newHttpClient();
        String json = "{\"prompt\": \"question\", \"response\": \"answer\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + SERVER_HOSTNAME + ":" + SERVER_PORT + "/"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String encodedPathParameter = URLEncoder.encode("question", StandardCharsets.UTF_8);
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://" + SERVER_HOSTNAME + ":" + SERVER_PORT + "/" + encodedPathParameter))
                .DELETE()
                .build();
        try {
            client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        // Assert that the response message is as expected
        assertEquals("Posted entry {question, answer}", response.body());
    }

    @Test
    public void testGetRequest_1() {
        // Now, make a get request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://" + SERVER_HOSTNAME + ":" + SERVER_PORT + "/?index=0"))
                .build();
        HttpResponse<String> getResponse = null;
        try {
            getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Ensure there is at least one data entry
        DataManager.addData(new QuestionData("question", "answer"));
        // Compare with response from DataManager
        QuestionData qd = DataManager.getData().get(0);
        JsonObject jsobObj = new JsonObject();
        jsobObj.addProperty("prompt", qd.getPrompt());
        jsobObj.addProperty("response", qd.getResponse());
        assertEquals(jsobObj.toString(), getResponse.body());
    }

    @Test
    public void testPutRequest() {
        HttpClient client = HttpClient.newHttpClient();
        String json = "{\"prompt\": \"test question\", \"response\": \"test answer\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + SERVER_HOSTNAME + ":" + SERVER_PORT + "/"))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String encodedPathParameter = URLEncoder.encode("question", StandardCharsets.UTF_8);
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://" + SERVER_HOSTNAME + ":" + SERVER_PORT + "/" + encodedPathParameter))
                .DELETE()
                .build();
        try {
            client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that the response code is as expected
        assertEquals(200, response.statusCode());
    }

    @Test
    public void testDeleteRequest() {
        HttpClient client = HttpClient.newHttpClient();
        String encodedPathParameter = URLEncoder.encode("test question", StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + SERVER_HOSTNAME + ":" + SERVER_PORT + "/" + encodedPathParameter))
                .DELETE()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that the response code is as expected
        assertEquals(200, response.statusCode());
    }
}
