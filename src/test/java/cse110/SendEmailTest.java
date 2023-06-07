package cse110;


import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;

import cse110.middleware.SendEmail; 

public class SendEmailTest {




   @Test
   void testDistinguishToEmail() {
       String prompt = "Send to emna056 at gmail.com";
       String answer = "emna056@gmail.com";
       String emailRecieved = SendEmail.distinguishToEmail(prompt);
       assertTrue(answer.equals(emailRecieved));
   }


   @Test
   void testDistinguishToEmail2() {
       String prompt = "Send email to clairemconner at gmail.com";
       String answer = "clairemconner@gmail.com";
       String emailRecieved = SendEmail.distinguishToEmail(prompt);
       assertTrue(answer.equals(emailRecieved));
   }


   @Test
   void testGetSubject() {
       String prompt = "Subject Line: Question Regarding Types of WhalesDear Professor,I was recently researching whales and was wondering: how many species of whales are there? I've tried searching online, but I haven't been able to find an answer. Any information or resources you can share would be greatly appreciated.Best Regards,Claire";
       String answer = "Question Regarding Types of Whales";
       String proposedAnswer = SendEmail.getSubject(prompt);
       System.out.println(proposedAnswer);
       assertTrue(answer.equals(proposedAnswer));
   }


   @Test
   void testGetSubject2() {
       String prompt = "Subject Line: Please workDear Claire, if this doesn't work, you suck. Best regards, yourself";
       String answer = "Please work";
       String proposedAnswer = SendEmail.getSubject(prompt);
       assertTrue(answer.equals(proposedAnswer));
   }


   @Test
   void testGetBody() {
       String prompt = "Subject Line: Question Regarding Types of WhalesDear Professor,I was recently researching whales and was wondering: how many species of whales are there? I've tried searching online, but I haven't been able to find an answer. Any information or resources you can share would be greatly appreciated.Best Regards,Claire";
       String answer = "Dear Professor,I was recently researching whales and was wondering: how many species of whales are there? I've tried searching online, but I haven't been able to find an answer. Any information or resources you can share would be greatly appreciated.Best Regards,Claire";
       String proposedAnswer = SendEmail.getBody(prompt);
       assertTrue(answer.equals(proposedAnswer));
   }


   @Test
   void testGetBody2() {
       String prompt = "Subject Line: Please workDear Claire, if this doesn't work, you suck. Best regards, yourself";
       String answer = "Dear Claire, if this doesn't work, you suck. Best regards, yourself";
       String proposedAnswer = SendEmail.getBody(prompt);
       assertTrue(answer.equals(proposedAnswer));
   }


   @Test
   void testCreateAndSendEmail() {
       String fromEmail = "airereads@gmail.com";
       String password = "kjvqxznjsuokrqfh";
       String toEmail = "Send to cconner at ucsd.edu"; //PUT YOUR EMAIL HERE W the character "at" as @
       String emailContents = "Subject Line: Please workDear Claire, if this doesn't work, you suck. Best regards, yourself";
       String smtpHost = "smtp.gmail.com";
       String tlsPort = "587"; 
       String expected = "Email successfully sent"; 
       assertTrue(expected.equals(SendEmail.createAndSendEmail(fromEmail, password, toEmail, emailContents, smtpHost, tlsPort)));
   } 
}



