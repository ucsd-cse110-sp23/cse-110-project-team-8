package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import src.Whisper;
import src.ChatGPT; 

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

public class IntegrationTest {
    private String testAudio;

    @BeforeEach
    void setUp() {
        testAudio = "lib/testRecording.wav";
    }

    @Test
    void testWhisperChatGPTIntegration() {
        String prompt = Whisper.transcribe(testAudio);
        System.out.println(prompt);
        String response = ChatGPT.getResponse(prompt, 100); 
        //assertEquals(prompt, "Who was the tallest man alive?");
        //assertEquals(response, ""); 
    }
}
