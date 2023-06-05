package cse110;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

public class WhisperTest {
    private String testAudio;

    @BeforeEach
    void setUp() {
        testAudio = "lib/testRecording.wav";
    }

    @Test
    void testWhisper() {
        String prompt = WhisperMock.transcribe(testAudio);
        System.out.println(prompt);
        assertEquals(prompt, "[Audio of: "+testAudio+"]");
    }
}
