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
        try {
            String prompt = Whisper.transcribe(testAudio);
            System.out.println(prompt);
            String response = ChatGPT.getResponse(prompt, 1000); 
            assertEquals(prompt, "Who was the tallest man alive?");
            //assertEquals(response, "The tallest man ever recorded was Robert Wadlow, an American man who stood 8 feet 11.1 inches (2.72 meters) tall. He lived from 1918 to 1940."); 
            assertEquals(response, "At the time of writing, the tallest man alive was Sultan Kösen, a Turkish farmsman who measured 8 feet and 3 inches tall. He held the Guinness World Record for the tallest living man since 2009."); 
        } catch (Exception e) {
            System.out.println("You suck bro"); 
        }
    }
}
