package cse110;

public class WhisperMock extends Whisper{
    public static String transcribe(String filepath){
        return "[Audio of: "+filepath+"]";
    }
}