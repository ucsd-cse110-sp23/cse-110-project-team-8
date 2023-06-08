package cse110;

import cse110.server.Whisper;

public class WhisperMock extends Whisper{
    public static String transcribe(String filepath){
        return "[Audio of: "+filepath+"]";
    }
}