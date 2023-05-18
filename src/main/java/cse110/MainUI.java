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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import java.io.*;


class Footer extends JPanel {
  JButton askButton;

  Color backgroundColor = new Color(240, 248, 255);
  Border emptyBorder = BorderFactory.createEmptyBorder();

  Footer() {
    this.setPreferredSize(new Dimension(400, 60));
    this.setBackground(backgroundColor);
    this.setLayout(new GridLayout(1, 1));
    
    askButton = new JButton("Add Question"); // add Question button
    askButton.setFont(new Font("Sans-serif", Font.ITALIC, 10)); // set font
    this.add(askButton); // add to footer
  }

  public JButton getQuestionButton() {
    return askButton;
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

/**
 * Panel for displaying prompt and response
 */
class MainPanel extends JPanel {
  private JTextArea questionText; 
  private JTextArea responseText; 

  MainPanel(String currPrompt, String currResponse) {
    questionText = new JTextArea(currPrompt); 
    questionText.setBounds(360, 0, 430, 200);

    responseText = new JTextArea(currResponse); 
    responseText.setBounds(360,210, 430, 350);

    questionText.setLineWrap(true);
    responseText.setLineWrap(true); 

    this.add(questionText);
    this.add(responseText);
  }

  public void setResponseText(String text) {
    this.responseText.setText(text);
  }

  public void setQuestionText(String text) {
    this.questionText.setText(text);
  }

  public void updateData(String question, String response) {
    this.responseText.setText(response);
    this.questionText.setText(question);
  }
}

class AppFrame extends JFrame {
  private final String fileName = "lib/recording.wav";
  private int maxTokens = 1000;

  //basic main panelUI variables
  private Header header;
  private Footer footer;
  private MainPanel panel; 
  private JButton askButton;
  
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

    //setting up basic question answer objects 
    panel = new MainPanel(currPrompt, currResponse); 
    audio = new AudioRecorder(); 
    header = new Header();
    footer = new Footer();

    // setting up basic sidebar
    historyList = ServerCall.sendGetAllRequest();

    sidebar = new SidebarUI(panel, historyList); 

    // Add panels to app frame
    this.add(header, BorderLayout.NORTH); // Add title bar on top of the screen
    this.add(footer, BorderLayout.SOUTH); // Add footer on bottom of the screen
    this.add(panel); 
    this.add(sidebar, BorderLayout.WEST); 

    askButton = footer.getQuestionButton();
    
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
                if (((footer.getQuestionButton()).getText()).compareTo("Add Question") == 0) {
                    panel.setResponseText("Recording");
                   
                    audio.startRecording(fileName);
                     
                    footer.getQuestionButton().setText("End Question"); 
                } else {
                    audio.stopRecording(); 

                    footer.getQuestionButton().setText("Add Question"); 
                    //after we have finished recording a question:
                    Thread t2 = new Thread(
                      new Runnable(){
                        @Override
                        public void run(){
                          try {
                            panel.setResponseText("Transcribing");
                            currPrompt = transcribePrompt(); //transcribe
                            panel.setQuestionText(currPrompt + "\n"); //set field to transcribed question
                            System.out.println("\nPrompt" + currPrompt);

                            currResponse = getGPTResponse(currPrompt); //get chat gpt response
                            System.out.println("\nResponse:" + currResponse);

                            panel.setResponseText(currResponse);

                            // Save new question
                            ServerCall.sendPostRequest(currPrompt, currResponse);
                            sidebar.addItem(currPrompt);
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
  }

  String transcribePrompt() {
    return ServerCall.transcribeAudio(fileName);
  }

  String getGPTResponse(String prompt) {
    return ServerCall.getResponse(prompt, maxTokens);
  }
}

public class MainUI {
  public static void main(String args[]) {
    new AppFrame(); // Create the frame
  }
}

@interface override {
}