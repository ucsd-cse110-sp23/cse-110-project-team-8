/*This file contains the UI panels for accounts, specifically: 
  * the home account page, where you can either choose to make an account or login
  * the create account page
  * the login page
*/ 
package cse110;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

//the home page
class AccountPanel extends JPanel {
    private JButton createAccountButton; 
    private JButton loginButton; 
    AccountPanel() {
      createAccountButton = new JButton("Create Account"); 
      loginButton = new JButton("Login"); // add Question button
      createAccountButton.setFont(new Font("Sans-serif", Font.ITALIC, 50)); // set font
      loginButton.setFont(new Font("Sans-serif", Font.ITALIC, 50)); // set font
      this.add(createAccountButton); // add to panel
      this.add(loginButton);
    }

    public JButton getCreateButton() {
      return createAccountButton;
    }

    public JButton getLoginButton() {
      return loginButton; 
    }
}

//creating an account 
class SecondAccountPanel extends JPanel {
    private JTextArea newUsername; 
    private JTextArea newPassword; 
    private JButton toAccountPanel;
    private JButton toMainPanel; 
    private JCheckBox autoCheck;
    SecondAccountPanel() {
      newUsername = new JTextArea(); 
      newUsername.setBounds(360, 0, 430, 200);
      newUsername.setLineWrap(true);

      newPassword = new JTextArea(); 
      newPassword.setBounds(360,210, 430, 200);
      newPassword.setLineWrap(true); 

      toMainPanel = new JButton("Login"); 
      toMainPanel.setFont(new Font("Sans-serif", Font.ITALIC, 50)); // set font

      toAccountPanel = new JButton("Back to Home"); 
      toAccountPanel.setFont(new Font("Sans-serif", Font.ITALIC, 50)); // set font

      autoCheck = new JCheckBox("Automatic Login");
      autoCheck.setFont(new Font("Sans serif", Font.ITALIC, 10));
      
      this.add(newUsername);
      this.add(newPassword); 
      this.add(toMainPanel); 
      this.add(toAccountPanel);
      this.add(autoCheck);
    }

    public JButton getToMainPanelButton() {
      return toMainPanel; 
    }

    public String getUsername() {
      return this.newUsername.getText();
    }

    public String getPassword() {
      return this.newPassword.getText();
    }


}


