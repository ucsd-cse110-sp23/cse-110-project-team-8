package cse110.server;

import com.sun.net.httpserver.*;

import cse110.middleware.RequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.*;


public class Server {

 // initialize server port and hostname
 private static final int SERVER_PORT = 8100;
 private static final String SERVER_HOSTNAME = "localhost";


 public static void main(String[] args) throws IOException {
    // create a thread pool to handle requests
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    // create a server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0
    );
    
    // Request handler for data handling
    server.createContext("/", new RequestHandler());
    // Request handler for audio transcription
    server.createContext("/transcribe", new TranscriptionHandler());
    // Request handler for prompt response
    server.createContext("/response", new ResponseHandler());
    // Request handler for creating an account
    server.createContext("/account", new AccountHandler());
    // Request handler for email info
    server.createContext("/emailInfo", new EmailInfoHandler());

    server.setExecutor(threadPoolExecutor);
    server.start();

    System.out.println("Server started on port " + SERVER_PORT);

 }
}