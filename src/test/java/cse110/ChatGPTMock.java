package cse110;

import cse110.server.ChatGPT;

public class ChatGPTMock extends ChatGPT{
    public static String getResponse(String prompt, int maxtokens){
        return "[Answer to: " + prompt + "]";
    }
}