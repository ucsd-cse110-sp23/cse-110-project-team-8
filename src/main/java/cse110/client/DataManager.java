package cse110.client;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

// Adapted from https://stackoverflow.com/questions/58988210/storing-data-in-json-file-using-java
public class DataManager {
    protected static String dataFile = "data.json";

    private static JsonObject data;

    public static void setData(JsonObject newdata){
        data = newdata;
    }

    public static JsonObject getData(){
        return data;
    }

    public static ArrayList<QuestionData> getQuestionData(){
        JsonArray promptHistory;

        if (getData() == null) {
            // Create new data if data is null
            data = new JsonObject();
        }

        JsonObject userData = getData();
        if (userData.get("promptHistory") != null){
            promptHistory = userData.get("promptHistory").getAsJsonArray();
            System.out.println(promptHistory.toString());
        }else{
            promptHistory = new JsonArray();
        }

        ArrayList<QuestionData> questions = new ArrayList<>();
        for (int i=0; i<promptHistory.size(); i++){
            JsonObject question = promptHistory.get(i).getAsJsonObject();
            QuestionData newquestion = new QuestionData(question.get("prompt").toString(),question.get("response").toString());
            questions.add(newquestion);
        }
        return questions;
    }
}
