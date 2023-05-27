package cse110;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;


public class CreateDB extends DBAccess {
    static String USERNAME_TAKEN = "Error: Username already taken";
    static String ADDED_USER = "Success: Added User";

    public static String addUser(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("accounts");
            MongoCollection<Document> userCollection = accountsDB.getCollection("loginInfo");

            // Check that username is not already taken
            Document findUser = userCollection.find(eq("username", username)).first();
            if (findUser != null) {
                // Username already exists
                return USERNAME_TAKEN;
            }

            Document user = new Document("_id", new ObjectId());
            user.append("username", username)
                   .append("password", password);
            userCollection.insertOne(user);
            return ADDED_USER;
        }
    }
}

