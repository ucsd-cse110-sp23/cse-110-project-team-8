package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import src.Whisper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

public class WhisperTest {
    private String testAudio;

    @BeforeEach
    void setUp() {
        testAudio = "lib/testRecording.wav";
    }

    @Test
    void testWhisper() {
        String prompt = Whisper.transcribe(testAudio);
        System.out.println(prompt);
        assertEquals(prompt, "Who was the tallest man alive?");
    }
}
