package cse110.client;

/**
 * This code was refactored from the original code found at:
 * https://copyassignment.com/to-do-list-app-in-java/
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

import cse110.middleware.AccountCommunication;
import cse110.middleware.EmailInfo;
import cse110.middleware.EmailInfoCommuncation;
import cse110.middleware.ServerCommunication;
import cse110.middleware.ResponseStrings;

import java.awt.*;
import javax.swing.*;
import java.io.*;

class Footer extends JPanel {
  //JButton askButton;

  Color backgroundColor = new Color(240, 248, 255);
  Border emptyBorder = BorderFactory.createEmptyBorder();

  Footer() {
    this.setPreferredSize(new Dimension(400, 60));
    this.setBackground(backgroundColor);
    this.setLayout(new GridLayout(1, 1));
  }
}

class Header extends JPanel {

  Color backgroundColor = new Color(240, 248, 255);

  Header() {
    this.setPreferredSize(new Dimension(400, 60)); // Size of the header
    this.setBackground(backgroundColor);
    JLabel titleText = new JLabel("SayIt App"); // Text of the header
    titleText.setPreferredSize(new Dimension(200, 60));
    titleText.setFont(new Font("Sans-serif", Font.BOLD, 20));
    titleText.setHorizontalAlignment(JLabel.CENTER); // Align the text to the center
    this.add(titleText); // Add the text to the header
  }
}


class AppFrame extends JFrame {
  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;

  private ErrorPopup errorPopup;

  private final String fileName = "lib/recording.wav";
  private int maxTokens = 1000;
  private String currUserId = "";

  //JPanel manager CardLayout
  private CardLayout cards; 
  private JPanel card; 

  //basic account panelUI variables
  private AccountPanel accountPanel; 
  private SecondAccountPanel createAccountPanel;
  private SecondAccountPanel loginPanel; 
  private JButton createAccountBtn; 
  private JButton loginBtn; 
  private JButton createToMainBtn; 
  private JButton loginToMainBtn; 
  private JButton backToAccountBtnfromLogin;
  private JButton backToAccountBtnfromCreate;
  private JCheckBox autoCheck;

  //basic main panelUI variables
  private Header header;
  private Footer footer;
  private MainPanel questionPanel; 
  private JButton askButton;

  //basic email UI variables
  private SetupEmailPanel setupEmailPanel; 
  
  //basic question/answer variables
  private String currPrompt;
  private String currResponse; 
  private AudioRecorder audio; 

  //basic sidebar variables
  private SidebarUI sidebar; 

  AppFrame() {
    this.setSize(WIDTH, HEIGHT); 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit

    //Creating text labels and setting default text
    currPrompt = "Press \"Add Question\" to begin recording your next question \n"; 
    currResponse = "..."; 

    //setting up basic account home page objects
    accountPanel = new AccountPanel(); 
    createAccountPanel = new SecondAccountPanel(); 
    loginPanel = new SecondAccountPanel(); 

    //setting up basic main panel question answer objects 
    questionPanel = new MainPanel(currPrompt, currResponse);
    audio = new AudioRecorder(); 
    header = new Header();
    footer = new Footer();
    
    //creating SetupEmail Email Panel
    setupEmailPanel = new SetupEmailPanel(this); 

    //creating JPanel cards which holds and manages all JPanels
    cards = new CardLayout(); 
    card = new JPanel(cards); 
    card.setBounds(0, 0, 800, 600);
    card.add(accountPanel, "accountPanel"); 
    card.add(createAccountPanel, "createPanel"); 
    card.add(loginPanel, "loginPanel");
    card.add(questionPanel, "questionPanel"); 
    card.add(setupEmailPanel, "setupEmailPanel"); 

    // Add panels, header, footer to app frame
    this.add(header, BorderLayout.NORTH); // Add title bar on top of the screen
    this.add(footer, BorderLayout.SOUTH); // Add footer on bottom of the screen
    this.add(card); 

    //creating and modifying account buttons
    createAccountBtn = accountPanel.getCreateButton(); 
    createAccountBtn.setPreferredSize(new Dimension (800, 200));
    loginBtn = accountPanel.getLoginButton(); 
    loginBtn.setPreferredSize(new Dimension(800, 200));
    createToMainBtn = createAccountPanel.getToMainPanelButton(); 
    loginToMainBtn = loginPanel.getToMainPanelButton(); 
    backToAccountBtnfromLogin = loginPanel.getToAccountPanel();
    backToAccountBtnfromCreate = createAccountPanel.getToAccountPanel();
    autoCheck = loginPanel.isSelectedBox();

    //creating and modifying askButton
    askButton = questionPanel.getQuestionButton();
    askButton.setPreferredSize(new Dimension(430,50));

    addListeners();
    this.setVisible(true); // Make visible
  }

  /** 
   * Add listeners to button for starting and stopping recording
   */
  public void addListeners() { 
    askButton.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e) {
                //comparing label of button to see if recording or not
                //note file name for recording is "recording.wav"
                if (((questionPanel.getQuestionButton()).getText()).compareTo("Add Question") == 0) {
                    questionPanel.setResponseText("Recording");
                   
                    audio.startRecording(fileName);
                     
                    questionPanel.getQuestionButton().setText("End Question"); 
                } else {
                    audio.stopRecording(); 

                    questionPanel.getQuestionButton().setText("Add Question"); 
                    //after we have finished recording a question:
                    Thread t2 = new Thread(
                      new Runnable(){
                        @Override
                        public void run(){
                          try {
                            questionPanel.setResponseText("Transcribing");
                            currPrompt = transcribePrompt(); //transcribe
                            //runnning handleCommand and returning and error
                            //if invalid command
                            if(handleCommand(currPrompt) != "Success") {
                              questionPanel.setResponseText(handleCommand(currPrompt));  
                            }
                          } catch (Exception e) {
                            e.printStackTrace(System.out);
                          }
                        }
                      }
                    );
                    t2.start();
              }
        } 
      }
    );

    createAccountBtn.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e) {
          cards.show(card, "createPanel"); 
        } 
      }
    );

    loginBtn.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e) {
          try (Scanner scanner = new Scanner(new File("credentials.txt"))) {
            if (scanner.hasNextLine() && "True".equals(scanner.nextLine())) {
                System.out.println("Check Found AutoLogin Enabled");
                if(scanner.hasNextLine()) {
                  System.out.println("Checking Credentials");
                  String username = scanner.nextLine();
                    if(scanner.hasNextLine()) {
                        String password = scanner.nextLine();
                        System.out.println("Sending Request");
                        JsonObject res = AccountCommunication.sendLoginRequest(username, password);
                        if (res.get("response").getAsString().equals(ResponseStrings.LOGIN_SUCCESS)) {
                            currUserId = username;
                            System.out.println("Current user: " + currUserId);
                            DataManager.setData(res.get("body").getAsJsonObject());

                            createSidebarUI();

                            // Switch to question panel if user is created
                            cards.show(card, "questionPanel"); 
                        } else{
                          showPopup(res.get("response").getAsString());
                        }
                    } else System.out.println("Password not found");
                }
            } else {
              cards.show(card, "loginPanel"); 
            }
          } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
          }  
          
        } 
      }
    );

    backToAccountBtnfromLogin.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          cards.show(card, "accountPanel");
        }
      }
    );
    
    backToAccountBtnfromCreate.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          cards.show(card, "accountPanel");
        }
      }
    );

    loginToMainBtn.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          // Check if auto-login is enabled
          JsonObject res = AccountCommunication.sendLoginRequest(loginPanel.getUsername(), loginPanel.getPassword());
          System.out.println(res);
          boolean autoChecker = autoCheck.isSelected();
          // if checkBox is selected, remember user
          if (autoChecker){
            System.out.println("AutoLogin Enabled");
            try (FileWriter writer = new FileWriter("credentials.txt")) {
                writer.write("True\n" + loginPanel.getUsername() + "\n" + loginPanel.getPassword() + "\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
          }

          if (res.get("response").getAsString().equals(ResponseStrings.LOGIN_SUCCESS)) {
            currUserId = loginPanel.getUsername();
            System.out.println("Current user: " + currUserId);
            System.out.println(res.toString());
            DataManager.setData(res.get("body").getAsJsonObject());
            createSidebarUI();
            // Switch to question panel if user is created
            goToQuestionPanel();
          }else{
            showPopup(res.get("response").getAsString());
          }
        } 
      }
    );

    createToMainBtn.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e) {
          // Send message to server to create account
          JsonObject res = AccountCommunication.sendCreateRequest(createAccountPanel.getUsername(), createAccountPanel.getPassword());

          if (res.get("response").getAsString().equals(ResponseStrings.DATABASE_WRITE_SUCCESS)) {
            currUserId = createAccountPanel.getUsername();
            System.out.println("Current user: " + currUserId);
            createSidebarUI();
            // Switch to question panel if user is created
            goToQuestionPanel();
          }else{
            showPopup(res.get("response").getAsString());
          }
        } 
      }
    );
  }

  void createSidebarUI() {
    sidebar = new SidebarUI(questionPanel, DataManager.getQuestionData()); 
    sidebar.setBounds(0, 0, 350, 500);
    revalidate();
  }

  String transcribePrompt() {
    return ServerCommunication.sendTranscribeRequest(fileName);
  }

  void savePrompt(String currPrompt,String currResponse){
    //save question
    JsonObject userData = DataManager.getData();
    JsonObject newquestion = new JsonObject();
    newquestion.addProperty("prompt", currPrompt);
    newquestion.addProperty("response", currResponse);
    JsonArray promptHistory;
    if (userData.get("promptHistory") != null){
      promptHistory = userData.get("promptHistory").getAsJsonArray();
    }else{
      promptHistory = new JsonArray();
    }
    promptHistory.add(newquestion);
    userData.add("promptHistory", promptHistory);
    System.out.println(DataManager.getData().toString());
    ServerCommunication.sendPostRequest(DataManager.getData());
    sidebar.addItem(currPrompt);
  }

  String getGPTResponse(String prompt) {
    return ServerCommunication.sendResponseRequest(prompt, maxTokens);
  }
  
  //if returns false, then not valid command or empty 
  String handleCommand(String command) {
    //handle for when prompt is null 
    if (command == null || command.isEmpty()) {
      return "Error: Command is null or empty."; 
    }
    //handle for when command is setup email
    if (command.equalsIgnoreCase("Set up email.")) {
      setupEmailPanel.updateDisplay();
      cards.show(card, "setupEmailPanel"); 
      System.out.println("set up email");
      return "Success";
    } else if (command.equals("Question.")) {
      //when command is Question, but there is no prompt
      return "Error: Command is 'Question.' but there is no prompt."; 
    } else if (command.indexOf("Question") == 0) {
      //handle when command is for a question
      questionPanel.setQuestionText(currPrompt + "\n"); 
      System.out.println("\nPrompt" + currPrompt);
      currResponse = getGPTResponse(currPrompt); //get chat gpt response
      System.out.println("\nResponse:" + currResponse);
      questionPanel.setResponseText(currResponse);  

      //save question
      savePrompt(currPrompt, currResponse);
      return "Success"; 
    } else if (command.equalsIgnoreCase("Clear all.")) { 
      //handle for command clear all
      sidebar.clearAll();
      return "Success";
    } else if (command.equalsIgnoreCase("Delete prompt.")) {
      //handle for command delete prompt
      sidebar.deleteItem(); 
      return "Success"; 
    } else if (command.equalsIgnoreCase("Create email.")) {
      return "Error: 'Create email to [Recipient]' is the proper format."; 
    } else if (command.toLowerCase().indexOf("create email") == 0) {
      //handle for create email 
      JsonObject jsonObj = EmailInfoCommuncation.sendGetEmailInfo(this.getCurrUserId());
      if (jsonObj.has("error")) {
        // Error getting email info
        currPrompt = "Please Set up email info first.";
        questionPanel.setQuestionText(currPrompt + "\n"); 
        return "Error: Failed to get email info. Please Set up email info first.";
      }

      questionPanel.setQuestionText(currPrompt + "\n"); 
      // Set currPrompt as the email draft from ChatGPT
      String newPrompt = currPrompt + ". Make email and end the email with the words: Best Regards, " + jsonObj.get(EmailInfo.displayNameKey).getAsString();
      System.out.println(newPrompt);
      newPrompt += "Do not include 'From [email]' or 'To [email]'.";
      currResponse= getGPTResponse(newPrompt);
      System.out.println("\nResponse:" + currResponse);
      questionPanel.setResponseText(currResponse);  

      // Save email draft in Server
      //ServerCommunication.sendPostRequest(DataManager.getData());
      savePrompt(currPrompt, currResponse);

      return "Success";
    }

    return "Error: Command not recognized.";
}

  void goToQuestionPanel() {
    cards.show(card, "questionPanel");
  }

  String getCurrUserId() {
    return this.currUserId;
  }

  public void showPopup(String text) {
    if (errorPopup == null) errorPopup = new ErrorPopup(this);
    errorPopup.setMessage(text);
    errorPopup.show();
  }
}

public class MainUI {
  public static void main(String args[]) {
    new AppFrame(); // Create the frame
  }
}

@interface override {
}