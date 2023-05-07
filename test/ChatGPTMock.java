package test;
import src.ChatGPT;

public class ChatGPTMock extends ChatGPT{
    public static String getResponse(String prompt, int maxtokens){
        return "[Answer to: " + prompt + "]";
    }
}