package cse110.server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cse110.middleware.ResponseStrings;

import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;


public class CreateDB extends DBAccess {

    public static String addUser(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("accounts");
            MongoCollection<Document> userCollection = accountsDB.getCollection("loginInfo");

            // Check that username is not already taken
            Document findUser = userCollection.find(eq("username", username)).first();
            if (findUser != null) {
                // Username already exists
                return ResponseStrings.USERNAME_TAKEN;
            }

            Document user = new Document("_id", new ObjectId());
            user.append("username", username)
                   .append("password", password);
            userCollection.insertOne(user);
            return ResponseStrings.ADDED_USER;
        }
    }
}

