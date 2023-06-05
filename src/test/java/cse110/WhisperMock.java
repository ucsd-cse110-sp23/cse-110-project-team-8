package cse110;

import cse110.middleware.Whisper;

public class WhisperMock extends Whisper{
    public static String transcribe(String filepath){
        return "[Audio of: "+filepath+"]";
    }
}