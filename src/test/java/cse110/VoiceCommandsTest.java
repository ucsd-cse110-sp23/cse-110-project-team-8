package cse110;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class VoiceCommandsTest {

    @Test 
    void testSetUpEmail() {
        String prompt = "Set up email.";
        System.out.println(prompt);
        assertTrue(handleCommandsMock.parseCommand(prompt));      
    }

    @Test
    void testQuestion() {
        String prompt = "Question bla bla bla";
        System.out.println(prompt);
        assertTrue(handleCommandsMock.parseCommand(prompt));
    }

    @Test 
    void testDelete() {
        String prompt = "Delete prompt.";
        System.out.println(prompt);
        assertTrue(handleCommandsMock.parseCommand(prompt));       
    }

    @Test 
    void testClear() {
        String prompt = "Clear all.";
        System.out.println(prompt);
        assertTrue(handleCommandsMock.parseCommand(prompt));       
    }

    @Test 
    void testEmpty() {
        String prompt = "";
        System.out.println(prompt);
        assertFalse(handleCommandsMock.parseCommand(prompt));       
    }

    @Test 
    void testQuestionNoCommand() {
        String prompt = "how old are you";
        System.out.println(prompt);
        assertFalse(handleCommandsMock.parseCommand(prompt));       
    }

    @Test 
    void testContainsWordQuestion() {
        String prompt = "what if I ask a question";
        System.out.println(prompt);
        assertFalse(handleCommandsMock.parseCommand(prompt));       
    }
}