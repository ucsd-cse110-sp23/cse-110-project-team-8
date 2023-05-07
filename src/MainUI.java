package src;

/**
 * This code was refactored from the original code found at:
 * https://copyassignment.com/to-do-list-app-in-java/
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

class Question extends JPanel {

  JLabel index;
  JTextField QuestionName;

  Color gray = new Color(218, 229, 234);
  Color green = new Color(188, 226, 158);

  private boolean markedDone;

  Question() {
    this.setPreferredSize(new Dimension(400, 20)); // set size of Question
    this.setBackground(gray); // set background color of Question

    this.setLayout(new BorderLayout()); // set layout of Question

    index = new JLabel(""); // create index label
    index.setPreferredSize(new Dimension(20, 20)); // set size of index label
    index.setHorizontalAlignment(JLabel.CENTER); // set alignment of index label
    this.add(index, BorderLayout.WEST); // add index label to Question

    QuestionName = new JTextField(""); // create Question name text field
    QuestionName.setBorder(BorderFactory.createEmptyBorder()); // remove border of text field
    QuestionName.setBackground(gray); // set background color of text field

    this.add(QuestionName, BorderLayout.CENTER);
  }

  public void changeIndex(int num) {
    this.index.setText(num + ""); // num to String
    this.revalidate(); // refresh
  }

}

class List extends JPanel {
  
  Color backgroundColor = new Color(240, 248, 255);

  List() {
    GridLayout layout = new GridLayout(10, 1);
    layout.setVgap(5); // Vertical gap

    this.setLayout(layout); // 10 Questions
    this.setPreferredSize(new Dimension(400, 560));
    this.setBackground(backgroundColor);
  }


  /**
   * Loads Questions from a file called "Questions.txt"
   * @return an ArrayList of Question
   */
  public ArrayList<Question> loadQuestions() {
    String currentLine;
    ArrayList<Question> Questions = new ArrayList<Question>();
    try{

      FileReader file = new FileReader("Questions.txt");
      BufferedReader reader = new BufferedReader(file);
      while ((currentLine = reader.readLine()) != null){
        Question CurrentQuestion = new Question();
        CurrentQuestion.QuestionName.setText(currentLine);
        System.out.println(currentLine);
        Questions.add(CurrentQuestion);

      }
      reader.close();
      file.close();
      return Questions;

    }
    catch (Exception e){
      System.out.println("Uh-Oh, you are bad at loading Questions");
      return Questions;
    }
    // hint 1: use try-catch block
    // hint 2: use BufferedReader and FileReader
    // hint 3: Question.QuestionName.setText(line) sets the text of the Question
    // System.out.println("loadQuestions() not implemented");
    // return null;
  }

  // TODO: Complete this method
  /**
   * Saves Questions to a file called "Questions.txt"
   */
  public void saveQuestions() {
    try {
        //creating new file to write to
        FileWriter file = new FileWriter("Questions.txt"); //not in src
        //iterating through questions to save them 
        Component[] listItems = this.getComponents(); 
        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof Question) {
                file.write(((Question)listItems[i]).QuestionName.getText() + "\n"); 
            }
            file.close();
        }
    } catch (Exception e) {
        System.out.println("Your questions aren't saving :("); 
    }
  }
}

class Footer extends JPanel {

  JButton askButton;
//   JButton loadButton;
//   JButton saveButton;

  Color backgroundColor = new Color(240, 248, 255);
  Border emptyBorder = BorderFactory.createEmptyBorder();

  Footer() {
    this.setPreferredSize(new Dimension(400, 60));
    this.setBackground(backgroundColor);
    this.setLayout(new GridLayout(1, 1));
    
    askButton = new JButton("Add Question"); // add Question button
    askButton.setFont(new Font("Sans-serif", Font.ITALIC, 10)); // set font
    this.add(askButton); // add to footer


    // loadButton = new JButton("Load Questions"); // load Question button
    // loadButton.setFont(new Font("Sans-serif", Font.ITALIC, 10)); // set font
    // this.add(loadButton); // add to footer



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

class AppFrame extends JFrame {

  private Header header;
  private Footer footer;
  private List list;

  private JButton askButton;



  AppFrame() {
    this.setSize(400, 600); // 400 width and 600 height
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit

    header = new Header();
    footer = new Footer();
    list = new List();

    this.add(header, BorderLayout.NORTH); // Add title bar on top of the screen
    this.add(footer, BorderLayout.SOUTH); // Add footer on bottom of the screen
    this.add(list, BorderLayout.CENTER); // Add list in middle of footer and title

    askButton = footer.getQuestionButton();
    //clearButton = footer.getClearButton(); 
    
    addListeners();
    this.setVisible(true); // Make visible

  }

  public void addListeners() {
    //creating AudioRecorder
    AudioRecorder audio = new AudioRecorder(); 
    String currPrompt; 
    askButton.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e) {
                //comparing label of button to see if recording or not
                //note file name for recording is "recording.wav"
                if (((footer.getQuestionButton()).getText()).compareTo("Add Question") == 0) {
                    audio.startRecording("recording.wav"); 
                    footer.getQuestionButton().setText("End Question"); 
                } else {
                    audio.stopRecording(); 
                    //after recording ends, we can save the text of the question before another question is recorded
                    currPrompt = transcribe("recording.wav");
                    
                    // TODO: Pass transcription to ChatGPT function
                    footer.getQuestionButton().setText("Add Question"); 
                }
        }
            

            // Later:
                // TODO: When asking a question, reuse save question prompt for storing history to file
                // TODO: Modify history by deleting that line from the file 
                    // (Maybe using indicies of question as refering to line numbers in file?)
                // TODO: View history by opening sidebar which loads the saved file (THIS IS UNDER THE BACK-END Story Task!!)


        //   Question Question = new Question();
        //   list.add(Question); // Add new Question to list
        //   list.updateNumbers(); // Updates the numbers of the Questions

        //   JButton doneButton = Question.getDone();
        //   doneButton.addMouseListener(
        //     new MouseAdapter() {
        //       @override
        //       public void mousePressed(MouseEvent e) {
        //         Question.changeState(); // Change color of Question
        //         list.updateNumbers(); // Updates the numbers of the Questions
        //         revalidate(); // Updates the frame
        //       }
        //     }
        //   ); 
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
