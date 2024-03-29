package cse110.server;

/**
 * Access Whisper API
 */
import java.io.*;
import org.json.JSONException;

public class Whisper {
    private static final String MODEL = "whisper-1";

    public static String transcribe(String filepath) {
        try {
            File file = new File(filepath);
            String result = WhisperAPI.transcribe(file, MODEL);

            return result;
        } catch (IOException | JSONException e) {
            System.out.println("Error: " + e.getMessage());
            return "Error: Couldn't Transcribe File";
        }
    }

}
