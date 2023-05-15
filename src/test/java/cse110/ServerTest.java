package cse110;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.concurrent.*;

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
    }

    // @Test
    // public void testGetRequest() {
    //     HttpClient client = HttpClient.newHttpClient();
    //     HttpRequest request = HttpRequest.newBuilder()
    //             .uri(URI.create("http://" + SERVER_HOSTNAME + ":" + SERVER_PORT + "/?index=0"))
    //             .build();
    //     HttpResponse<String> response = null;
    //     try {
    //         response = client.send(request, HttpResponse.BodyHandlers.ofString());
    //     } catch (IOException | InterruptedException e) {
    //         e.printStackTrace();
    //     }

    //     // Assuming that the DataManager is initially empty,
    //     // the response should indicate that there's no data for the requested index
    //     assertEquals("No data for this index", response.body());
    // }

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

        // Assert that the response message is as expected
        assertEquals("Posted entry {\"question\", \"answer\"}", response.body());
    }


}
