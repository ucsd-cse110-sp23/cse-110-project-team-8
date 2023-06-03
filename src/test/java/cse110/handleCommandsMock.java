package cse110;

public class handleCommandsMock {
    public static boolean parseCommand(String command){
        String currResponse; 
            //handle for when prompt is null 
        if (command == null || command.isEmpty()) {
            return false; 
        }
        //handle for when command is setup email
        if (command.equalsIgnoreCase("Setup email.")) {
            System.out.println("set up email");
            return true;
        } else if (command.indexOf("Question") == 0) {
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
            return true; 
        }
        //invalid prompt but not empty 
        return false; 
    }
}
