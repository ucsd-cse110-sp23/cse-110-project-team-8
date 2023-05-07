package src;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Access ChatGPT API
 */
public class ChatGPT {
    
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";

    /*
     * Returns a response to a prompt from ChatGPT
     */
    public static String getResponse(String prompt,int maxtokens) throws IOException, InterruptedException{

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt",prompt);
        requestBody.put("max_tokens",maxtokens);
        requestBody.put("temperature",1.0);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type","application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        JSONObject responseJson = new JSONObject(response.body());
        JSONArray choices = responseJson.getJSONArray("choices");
        String answer = choices.getJSONObject(0).getString("text");

        return answer;
    }
}
