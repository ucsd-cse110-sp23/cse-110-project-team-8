package cse110;

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
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
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
  private ArrayList<QuestionData> historyList; 
  private SidebarUI sidebar; 

  AppFrame() {
    this.setSize(800, 600); // 400 width and 600 height
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

    // setting up basic sidebar
    historyList = ServerCommunication.sendGetAllRequest();
    sidebar = new SidebarUI(questionPanel, historyList); 
    sidebar.setBounds(0, 0, 350, 600);
    
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
                            if(handleCommand(currPrompt) == false) {
                              questionPanel.setResponseText("Invalid command");  
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
                        String res = AccountCommunication.sendLoginRequest(username, password);
                        if (ReadDB.LOGIN_SUCCESS.equals(res)) {
                            currUserId = username;
                            System.out.println("Current user: " + currUserId);
                            // Switch to question panel if user is created
                            cards.show(card, "questionPanel"); 
                        } else System.out.println("Request Failed");
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
          String res = AccountCommunication.sendLoginRequest(loginPanel.getUsername(), loginPanel.getPassword());
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
          // TODO: handle errors in login

          if (ReadDB.LOGIN_SUCCESS.equals(res)) {
            currUserId = loginPanel.getUsername();
            System.out.println("Current user: " + currUserId);
            // Switch to question panel if user is created
            goToQuestionPanel();
          }
        } 
      }
    );

    createToMainBtn.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e) {
          // Send message to server to create account
          String res = AccountCommunication.sendCreateRequest(createAccountPanel.getUsername(), createAccountPanel.getPassword());
          System.out.println(res);

          // TODO: handle errors in account creation

          if (res.equals(CreateDB.ADDED_USER)) {
            currUserId = createAccountPanel.getUsername();
            System.out.println("Current user: " + currUserId);
            // Switch to question panel if user is created
            goToQuestionPanel();
          }
        } 
      }
    );
  }

  String transcribePrompt() {
    return ServerCommunication.sendTranscribeRequest(fileName);
  }

  String getGPTResponse(String prompt) {
    return ServerCommunication.sendResponseRequest(prompt, maxTokens);
  }
  
  //if returns false, then not valid command or empty 
  boolean handleCommand(String command) {
    //handle for when prompt is null 
    if (command == null || command.isEmpty()) {
      return false; 
    }
    //handle for when command is setup email
    if (command.equalsIgnoreCase("Set up email.")) {
      cards.show(card, "setupEmailPanel"); 
      System.out.println("set up email");
      return true;
    } else if (command.equals("Question.")) {
      //when commmand is Question, but there is no prompt
      return false; 
    } else if (command.indexOf("Question") == 0) {
      //handle when command is for a question
      questionPanel.setQuestionText(currPrompt + "\n"); 
      System.out.println("\nPrompt" + currPrompt);
      currResponse = getGPTResponse(currPrompt); //get chat gpt response
      System.out.println("\nResponse:" + currResponse);
      questionPanel.setResponseText(currResponse);  
      //save question 
      ServerCommunication.sendPostRequest(currPrompt, currResponse);
      sidebar.addItem(currPrompt);
      return true; 
    } else if (command.equalsIgnoreCase("Clear all.")) { 
      //handle for command clear all
      sidebar.clearAll();
      return true;
    } else if (command.equalsIgnoreCase("Delete prompt.")) {
      sidebar.deleteItem(); 
      return true; 
    } else if (command.indexOf("Create email") == 0) {
      // Set currPrompt as the email draft from ChatGPT
      currResponse= getGPTResponse(currPrompt + " Make email from " + this.getCurrUserId());
      System.out.println("\nResponse:" + currResponse);
      questionPanel.setResponseText(currResponse);  

      // Save email draft in Server
      ServerCommunication.sendPostRequest(currPrompt, currResponse);

      sidebar.addItem(currPrompt);
      return true;
    }
    //invalid prompt but not empty 
    return false; 
  }
  void goToQuestionPanel() {
    cards.show(card, "questionPanel");
  }

  String getCurrUserId() {
    return this.currUserId;
  }
}

public class MainUI {
  public static void main(String args[]) {
    new AppFrame(); // Create the frame
  }
}

@interface override {
}