package test;

import org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import src.Whisper;

import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

public class IntegrationTest {
    private String testAudio;

    @BeforeEach
    void setUp() {
        testAudio = "testRecording.wav";
    }

    @Test
    void testWhisperChatGPTIntegration() {
        String prompt = Whisper.transcribe(testAudio);
        System.out.println(prompt);
        assertEquals(prompt, "Who is the tallest man in the world");
    }
}