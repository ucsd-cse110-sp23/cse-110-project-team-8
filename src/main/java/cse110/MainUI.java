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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.io.*;
import java.net.*;


// class Question extends JPanel {
//   JLabel index;
//   JTextField QuestionName;

//   Color gray = new Color(218, 229, 234);
//   Color green = new Color(188, 226, 158);

//   Question() {
//     this.setPreferredSize(new Dimension(400, 20)); // set size of Question
//     this.setBackground(gray); // set background color of Question

//     this.setLayout(new BorderLayout()); // set layout of Question

//     index = new JLabel(""); // create index label
//     index.setPreferredSize(new Dimension(20, 20)); // set size of index label
//     index.setHorizontalAlignment(JLabel.CENTER); // set alignment of index label
//     this.add(index, BorderLayout.WEST); // add index label to Question

//     QuestionName = new JTextField(""); // create Question name text field
//     QuestionName.setBorder(BorderFactory.createEmptyBorder()); // remove border of text field
//     QuestionName.setBackground(gray); // set background color of text field

//     this.add(QuestionName, BorderLayout.CENTER);
//   }

//   public void changeIndex(int num) {
//     this.index.setText(num + ""); // num to String
//     this.revalidate(); // refresh
//   }

// }

// class List extends JPanel {
//   Color backgroundColor = new Color(240, 248, 255);
//   ArrayList<String> history;
//   List() {
//     GridLayout layout = new GridLayout(10, 1);
//     layout.setVgap(5); // Vertical gap

//     this.setLayout(layout); // 10 Questions
//     this.setPreferredSize(new Dimension(400, 560));
//     this.setBackground(backgroundColor);
//   }

//   /**
//    * Loads Questions from a file called "Questions.txt"
//    * @return an ArrayList of Question
//    */
//   public ArrayList<Question> loadQuestions() {
//     String currentLine;
//     ArrayList<Question> Questions = new ArrayList<Question>();
//     try{

//       FileReader file = new FileReader("Questions.txt");
//       BufferedReader reader = new BufferedReader(file);
//       while ((currentLine = reader.readLine()) != null){
//         Question CurrentQuestion = new Question();
//         CurrentQuestion.QuestionName.setText(currentLine);
//         System.out.println(currentLine);
//         Questions.add(CurrentQuestion);
//       }
//       reader.close();
//       file.close();
//       return Questions;

//     }
//     catch (Exception e){
//       System.out.println("Uh-Oh, you are bad at loading Questions");
//       return Questions;
//     }
//   }

// }

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
  public static final String URL = "http://localhost:8100/";

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

    //setting up basic sidebar
    historyList = DataManager.loadData(); 
    // TODO: Use Http Server API
    // URL url = new URL(URL);
    // HttpURLConnection conn = (HttpURLConnection) url.openConnection();

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

  public static void sendPostRequest(String prompt, String response) {
    try {
        // Setup the server address
        URL url = new URL(URL);

        // Create a HttpURLConnection object
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set method to POST
        conn.setRequestMethod("POST");

        // To send a POST request, we must set DoOutput to true
        conn.setDoOutput(true);

        // Write the request content
        OutputStreamWriter out = new OutputStreamWriter(
              conn.getOutputStream()
            );
        out.write(prompt + "," + response);
        out.flush();
        out.close();

        // Get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("POST Response Code: " + responseCode);

    } catch (Exception e) {
        e.printStackTrace();
    }
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
                            currPrompt = Whisper.transcribe(fileName); //transcribe
                            panel.setQuestionText(currPrompt + "\n"); //set field to transcribed question
                            System.out.println("\nPrompt" + currPrompt);

                            currResponse = ChatGPT.getResponse(currPrompt, 1000); //get chat gpt response
                            System.out.println("\nResponse:" + currResponse);
      
                            panel.setResponseText(currResponse);

                            // Save new question
                            // DataManager.addData(new QuestionData(currPrompt, currResponse));
                            // TODO: Use Http Server API
                            sendPostRequest(currPrompt, currResponse);
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
}

public class MainUI {
  public static void main(String args[]) {
    new AppFrame(); // Create the frame
  }
}

@interface override {
}