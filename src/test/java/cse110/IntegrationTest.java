package cse110;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

public class IntegrationTest {
    private String testAudio;

    @BeforeEach
    void setUp() {
        testAudio = "lib/testRecording.wav";
    }

    @Test
    void testWhisperChatGPTIntegration() {
        try {
            String prompt = WhisperMock.transcribe(testAudio);
            assertEquals(prompt, "[Audio of: "+testAudio+"]");

            String response = ChatGPTMock.getResponse(prompt, 1000); 
            String expected = "[Answer to: " + prompt + "]";
            assertEquals(expected, response); 
        } catch (Exception e) {
            System.out.println("You suck bro"); 
        }
    }
}
