
/**
 * Access Whisper API
 */
import java.io.*;
import org.json.JSONException;
import org.json.JSONObject;

public class Whisper {
    private static final String MODEL = "whisper-1";

    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            String result = WhisperAPI.transcribe(file, MODEL);
            System.out.println("Transcription Result: " + result);
        } catch (IOException | JSONException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
