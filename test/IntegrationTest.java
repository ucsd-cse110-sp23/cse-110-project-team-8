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
        String response = ChatGPT.getResponse(prompt, 1000); 
        assertEquals(prompt, "Who was the tallest man alive?");
        assertEquals(response, "The tallest man ever recorded was Robert Wadlow, an American man who stood 8 feet 11.1 inches (2.72 meters) tall. He lived from 1918 to 1940."); 
    }
}
