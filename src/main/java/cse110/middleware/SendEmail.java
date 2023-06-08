package cse110.middleware;


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendEmail {


   public static String getSubject(String contents) {
       int indexOfColon = contents.indexOf(":");
       int indexOfDear = contents.indexOf("Dear");
       String subject = contents.substring(indexOfColon + 2, indexOfDear);
       return subject;
   }


   public static String getBody(String contents) {
       int indexOfDear = contents.indexOf("Dear");
       String body = contents.substring(indexOfDear, contents.length());
       return body;
   }


   public static String distinguishToEmail(String recieverEmail) {
       String newEmail = recieverEmail;
       if(recieverEmail.indexOf("at") != -1) {
           String replaceAt = " at ";
           int indexOfAt = recieverEmail.indexOf(replaceAt);
           newEmail = recieverEmail.substring(0, indexOfAt) + "@" + recieverEmail.substring(indexOfAt+4,recieverEmail.length());
       }
       int indexOfTo = recieverEmail.indexOf("to");
       newEmail = newEmail.substring(indexOfTo+3, newEmail.length());
       return newEmail;
   }


   public static String sendEmail(Session session, String toEmail, String subject, String body){
       try
       {
         System.out.println("debug begin");
         MimeMessage msg = new MimeMessage(session);
         //set message headers
         msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
         msg.addHeader("format", "flowed");
         msg.addHeader("Content-Transfer-Encoding", "8bit");


         msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));


         msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));


         msg.setSubject(subject, "UTF-8");


         msg.setText(body, "UTF-8");


         msg.setSentDate(new Date());


         msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
         System.out.println("Message is ready");
         Transport.send(msg); 


         System.out.println("Email Sent Successfully!!");


         return "Email successfully sent";
       }
       catch (Exception e) {
         System.out.println("you have failed");
         e.printStackTrace();
         return "Error with SMTP Host";
       }
   }


   public static String createAndSendEmail(String fromEmail, String password, String toEmail, String emailContents, String smtpHost, String tlsPort){
      
       System.out.println("TLSEmail Start");
       Properties props = new Properties();
       props.put("mail.smtp.host", smtpHost); //SMTP Host
       props.put("mail.smtp.port", tlsPort); //TLS Port
       props.put("mail.smtp.auth", "true"); //enable authentication
       props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

       //create Authenticator object to pass in Session.getInstance argument
       Authenticator auth = new Authenticator() {
           //override the getPasswordAuthentication method
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(fromEmail, password);
           }
       };
       Session session = Session.getInstance(props, auth);
       System.out.println("about to send email");
       return sendEmail(session, distinguishToEmail(toEmail),getSubject(emailContents), getBody(emailContents));
   }
}



