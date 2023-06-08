package cse110.client;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Panel for displaying prompt and response
 */
class MainPanel extends JPanel {
    private JTextArea questionText; 
    private JTextArea responseText; 
    private JButton askButton; //NEW
  
    MainPanel(String currPrompt, String currResponse) {
      questionText = new JTextArea(currPrompt); 
  
      responseText = new JTextArea(currResponse); 
  
      questionText.setLineWrap(true);
      responseText.setLineWrap(true); 
      
      //NEW
      askButton = new JButton("Add Question"); // add Question button
      askButton.setFont(new Font("Sans-serif", Font.ITALIC, 10)); // set font
      this.add(askButton); // add to footer
  
      this.add(questionText);
      this.add(responseText);
      this.add(askButton); //NEW
      questionText.setPreferredSize(new Dimension(430, 200));
      responseText.setPreferredSize(new Dimension(430, 350)); 
      responseText.setBounds(360,0, 430, 200);
      responseText.setBounds(360,210, 430, 350);
    }

    public JButton getQuestionButton() {
        return askButton;
    }

    public JTextArea getQuestionArea() {
      return questionText; 
    }
    
    public JTextArea getResponseArea() {
      return responseText; 
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
