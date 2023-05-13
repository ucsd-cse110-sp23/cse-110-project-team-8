package cse110;

import com.google.gson.Gson;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

// Adapted from https://stackoverflow.com/questions/58988210/storing-data-in-json-file-using-java
public class DataManager {

    private static ArrayList<QuestionData> data;
    private static Gson gson = new Gson();

    public static ArrayList<QuestionData> loadData(){
        try (FileReader file = new FileReader("data.json")){
            QuestionData[] readdata = gson.fromJson(file,QuestionData[].class);
            if (readdata == null) return null;
            data = new ArrayList<>(Arrays.asList(readdata));
        }catch(Exception e){
            e.printStackTrace(System.out);
            return null;
        }

        return data;
    }

    public static boolean saveData(){
        try (FileWriter file = new FileWriter("data.json")){
            file.write(gson.toJson(data));
        }catch(Exception e){
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    public static boolean removeData(int index) {
        // Check if data is loaded, if not load the data
        if (data == null) {
            data = loadData();
        }

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
        return data;
    }

    public static void setData(ArrayList<QuestionData> newdata){
        data = newdata;
    }

}
