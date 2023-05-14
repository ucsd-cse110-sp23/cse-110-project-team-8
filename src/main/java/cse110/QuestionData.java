package cse110;

public class QuestionData{
    private String prompt;
    private String response;

    public QuestionData(String prompt, String response) {
        this.prompt = prompt;
        this.response = response;
    }

    public String getPrompt(){
        return prompt;
    }

    public String getResponse(){
        return response;
    }
}