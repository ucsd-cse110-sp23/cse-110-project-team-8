package cse110.client;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

// Adapted from https://stackoverflow.com/questions/58988210/storing-data-in-json-file-using-java
public class DataManager {
    protected static String dataFile = "data.json";

    private static ArrayList<QuestionData> data;
    private static Gson gson = new Gson();

    public static ArrayList<QuestionData> loadData(){
        try (FileReader file = new FileReader(dataFile)) {
            QuestionData[] readdata = gson.fromJson(file,QuestionData[].class);
            if (readdata == null) return null;
            data = new ArrayList<>(Arrays.asList(readdata));
        }catch(Exception e){
            e.printStackTrace(System.out);
            return null;
        }

        return data;
    }

    public static boolean existsData(String prompt) {
        loadData();
        for (QuestionData qd: data) {
            if (qd.getPrompt().equals(prompt)) return true;
        }
        return false;
    }
    
    public static QuestionData findData(String prompt) {
        loadData();
        for (QuestionData qd: data) {
            if (qd.getPrompt() == prompt) return qd;
        }
        return new QuestionData("", "");
    }

    public static boolean saveData(){
        try (FileWriter file = new FileWriter(dataFile)){
            file.write(gson.toJson(data));
        }catch(Exception e){
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    public static boolean addData(QuestionData qd) {
        data = loadData();
    
        // Check if data is still null after trying to load
        if (data == null) {
            // If it's still null, initialize it as an empty ArrayList
            data = new ArrayList<>();
        }
    
        data.add(qd);
        return saveData();
    }

    public static boolean removeData(int index) {
        data = loadData();

        // If data is still null after trying to load, return false
        if (data == null) {
            return false;
        }

        // Check if index is valid
        if (index < 0 || index >= data.size()) {
            return false;
        }

        // Remove the question at the specified index
        data.remove(index);

        // Save the updated data
        return saveData();
    }

    public static ArrayList<QuestionData> getData(){
        loadData();
        return data;
    }

    public static boolean setData(ArrayList<QuestionData> newdata){
        data = newdata;
        return saveData();
    }

}
