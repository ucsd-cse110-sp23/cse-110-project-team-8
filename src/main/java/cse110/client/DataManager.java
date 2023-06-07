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
            System.out.println("Should be null");
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

    // public static ArrayList<QuestionData> loadData(){
    //     try (FileReader file = new FileReader(dataFile)) {
    //         QuestionData[] readdata = gson.fromJson(file,QuestionData[].class);
    //         if (readdata == null) return null;
    //         data = new ArrayList<>(Arrays.asList(readdata));
    //     }catch(Exception e){
    //         e.printStackTrace(System.out);
    //         return null;
    //     }

    //     return data;
    // }

    // public static boolean existsData(String prompt) {
    //     loadData();
    //     for (QuestionData qd: data) {
    //         if (qd.getPrompt().equals(prompt)) return true;
    //     }
    //     return false;
    // }
    
    // // public static QuestionData findData(String prompt) {
    // //     loadData();
    // //     for (QuestionData qd: data) {
    // //         if (qd.getPrompt() == prompt) return qd;
    // //     }
    // //     return new QuestionData("", "");
    // // }

    // public static boolean saveData(){
    //     try (FileWriter file = new FileWriter(dataFile)){
    //         file.write(gson.toJson(data));
    //     }catch(Exception e){
    //         e.printStackTrace(System.out);
    //         return false;
    //     }
    //     return true;
    // }

    // public static boolean addData(QuestionData qd) {
    //     data = loadData();
    
    //     // Check if data is still null after trying to load
    //     if (data == null) {
    //         // If it's still null, initialize it as an empty ArrayList
    //         data = new ArrayList<>();
    //     }
    
    //     data.add(qd);
    //     return saveData();
    // }

    // public static boolean removeData(int index) {
    //     data = loadData();

    //     // If data is still null after trying to load, return false
    //     if (data == null) {
    //         return false;
    //     }

    //     // Check if index is valid
    //     if (index < 0 || index >= data.size()) {
    //         return false;
    //     }

    //     // Remove the question at the specified index
    //     data.remove(index);

    //     // Save the updated data
    //     return saveData();
    // }

    // public static ArrayList<QuestionData> getData(){
    //     loadData();
    //     return data;
    // }

    // public static boolean setData(ArrayList<QuestionData> newdata){
    //     data = newdata;
    //     return saveData();
    // }

}
