package cse110;


import com.mongodb.client.*;
import org.bson.Document;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;


public class ReadDB {
    static String DBusername = "team8";
    static String DBpassword = "TGnSnsFqsz41yroz";
    public static String uri = "mongodb+srv://"+DBusername+":"+DBpassword+"@sayit.nxzoquh.mongodb.net/?retryWrites=true&w=majority";

    public static String checkLogin(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("accounts");
            MongoCollection<Document> userCollection = accountsDB.getCollection("loginInfo");

            // find one document with Filters.eq()
            Document user = userCollection.find(eq("username", username)).first();

            if (user == null) {
                // This username does not exist
                return "Error: This username does not exist.";
            }

            System.out.println("user: " + user.toJson());

            if (user.get("password") != password) {
                // Password does not match
                return "Error: Password does not match.";
            }
            return "Success: Match";
        }
    }
}


