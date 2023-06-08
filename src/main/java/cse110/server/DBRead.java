package cse110.server;

import com.mongodb.client.*;

import cse110.middleware.ResponseStrings;

import org.bson.Document;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static com.mongodb.client.model.Filters.*;


public class DBRead extends DBCredentials {

    public static JsonObject getUserData(String username, String password){
        JsonObject json = new JsonObject();
        System.out.println(DBCredentials.uri);
        try (MongoClient mongoClient = MongoClients.create(DBCredentials.uri)) {
            System.out.println(DBCredentials.uri + " made connection");
            MongoDatabase userDB = mongoClient.getDatabase("users");
            System.out.println(DBCredentials.uri + " made database");
            MongoCollection<Document> userDataCollection = userDB.getCollection("userData");

            System.out.println(DBCredentials.uri + " made collection");
            // Get document with matching username and password
            Document userData = userDataCollection.find(eq("username", username)).first();
            System.out.println(DBCredentials.uri + "find in made collection");

            if (userData == null){
                System.out.println(DBCredentials.uri + "is null");
                json.addProperty("response", ResponseStrings.USERNAME_ERROR);
                System.out.println(DBCredentials.uri + "set response");
                return json;
            }

            if (!userData.get("password").equals(password)){
                json.addProperty("response", ResponseStrings.PASSWORD_ERROR);
                return json;
            }

            json.addProperty("response", ResponseStrings.DATABASE_READ_SUCCESS);
            json.add("body",JsonParser.parseString(userData.toJson()).getAsJsonObject());
            return json;
        }catch(Exception e){
            json.addProperty("response", ResponseStrings.DATABASE_ERROR);
            return json;
        }
    }
}