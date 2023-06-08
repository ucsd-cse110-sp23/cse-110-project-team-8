package cse110.server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import cse110.middleware.ResponseStrings;

import org.bson.Document;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Write user data into MongoDB.
 */
public class DBWrite extends DBCredentials {

    public static JsonObject setUserData(Document data){
        JsonObject json = new JsonObject();
        try (MongoClient mongoClient = MongoClients.create(DBCredentials.uri)) {
            MongoDatabase userDB = mongoClient.getDatabase("users");
            MongoCollection<Document> userDataCollection = userDB.getCollection("userData");

            if (userDataCollection.find(eq("_id",data.get("_id"))).first() != null) {
                userDataCollection.replaceOne(eq("_id",data.get("_id")),data);
            } else {
                // insert
                userDataCollection.insertOne(data);
            }

            json.addProperty("response", ResponseStrings.DATABASE_WRITE_SUCCESS);
            json.add("body",JsonParser.parseString(data.toJson()).getAsJsonObject());
            return json;
        }catch(Exception e){
            json.addProperty("response", ResponseStrings.DATABASE_ERROR);
            return json;
        }
    }
}

