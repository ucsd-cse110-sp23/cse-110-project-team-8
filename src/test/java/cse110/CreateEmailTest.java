package cse110;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreateEmailTest {

    //test for a registered fake email and correct prompt
    /* 
    @Test 
    void testCreateEmailCorrect() {
        String prompt = "Create email to the TA can you please give me an A on this project";
        String username = "bogus"; 
        System.out.println(prompt);
        assertTrue(handleCommandsMock.parseCreateEmail(prompt, username)); 
    }
    */

    //test for a registered fake email and an incorrect prompt
    @Test 
    void testCreateEmailNoPrompt() {
        String prompt = "Create email.";
        String username = "bogus"; 
        System.out.println(prompt);
        assertFalse(handleCommandsMock.parseCreateEmail(prompt, username)); 
    }

    //test for correct prompt but an email that is not setup properly
    @Test 
    void testCreateEmailNotSetup() {
        String prompt = "Create email.";
        String username = "bot"; 
        System.out.println(prompt);
        assertFalse(handleCommandsMock.parseCreateEmail(prompt, username)); 
    }
}