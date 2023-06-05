package cse110;
import com.google.gson.JsonObject;

public class handleCommandsMock {
    public static boolean parseCommand(String command){
        String currResponse; 
        //handle for when prompt is null 
        if (command == null || command.isEmpty()) {
            return false; 
        }
        //handle for when command is setup email
        if (command.equalsIgnoreCase("Set up email.")) {
            System.out.println("set up email");
            return true;
        } else if (command.equalsIgnoreCase("Question.")) {
            //handle command for question with no prompt
            return false; 
        }else if (command.indexOf("Question") == 0) {
            //handle when command is for a question
            System.out.println("\nPrompt" + command);
            currResponse = "you've got a response pal"; //get chat gpt response
            System.out.println("\nResponse:" + currResponse);
            //save question 
            return true; 
        } else if (command.equalsIgnoreCase("Clear all.")) { 
            //handle for command clear all
            return true;
        } else if (command.equalsIgnoreCase("Delete prompt.")) {
            //hadndle command for delete
            return true; 
        } else if (command.equalsIgnoreCase("Create email.")) {
            //handle command for create email with no prompt
            return false; 
        } else if (command.indexOf("Create email") == 0) {
            //handle command for create email with prompt
            return true; 
        }
        //invalid prompt but not empty 
        return false; 
    }

    public static boolean parseCreateEmail(String command,String username) {
        if (command == null) {
            return false;
        } else if (command.equalsIgnoreCase("Create email.")) {
            //handle command for create email with no prompt
            return false; 
        } else if (command.indexOf("Create email") == 0) {
            //handle command for create email with prompt
            JsonObject jsonObj = EmailInfoCommuncation.sendGetEmailInfo(username);
            if (jsonObj.has("error")) {
              // Error getting email info
              command = "Please Set up email info first.";
              System.out.println(command); 
              return false;
            }
            /* 
            System.out.println(command); 
            // Set currPrompt as the email draft from ChatGPT
            String newPrompt = command + ". Make email and end the email with the words: Best Regards, " + jsonObj.get(EmailInfo.displayNameKey).getAsString();
            System.out.println(newPrompt);
            newPrompt += "Do not include 'From [email]' or 'To [email]'.";
            String currResponse = ChatGPTMock.getResponse(newPrompt,1000);
            System.out.println("\nResponse:" + currResponse);
            */
            return true; 
        }
        //invalid prompt but not empty 
        return false; 
    }
}
