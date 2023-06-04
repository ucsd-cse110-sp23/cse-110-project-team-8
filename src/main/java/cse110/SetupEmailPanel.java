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
import javax.swing.JTextField;
import javax.swing.JButton; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SetupEmailPanel extends JPanel{
Appframe appFrame;
    private JTextField firstName; 
    private JTextField lastName; 
    private JTextField displayName; 
    private JTextField emailAddress; 
    private JTextField smtpHost; 
    private JTextField tlsPort; 
    private JTextField emailPassword; 

    private JLabel firstNameLabel; 
    private JLabel lastNameLabel; 
    private JLabel displayNameLabel; 
    private JLabel emailAddressLabel; 
    private JLabel smtpHostLabel; 
    private JLabel tlsPortLabel; 
    private JLabel emailPasswordLabel; 

    private JButton saveBtn; 
    private JButton cancelBtn; 

    SetupEmailPanel(AppFrame appFrame) {
     this.appFrame = appFrame;
      addText(firstName,firstNameLabel,"First Name");
      addText(lastName, lastNameLabel, "Last Name");
      addText(displayName, displayNameLabel, "Display Name");
      addText(emailAddress, emailAddressLabel, "Email Address");
      addText(smtpHost, smtpHostLabel, "SMTP Host");
      addText(tlsPort, tlsPortLabel, "TLS Port");
      addText(emailPassword, emailPasswordLabel, "Email Password");

      saveBtn = new JButton("save"); 
      cancelBtn = new JButton("cancel"); 
      this.add(saveBtn);
      this.add(cancelBtn); 
      
      // Add save button and its action listener
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              // Set entered data as EmailInfo object
              EmailInfo ei = new EmailInfo()
                .setUserId(appFrame.getCurrUserId())
                .setFirstName(firstName.getText())
                .setLastName(lastName.getText())
                .setdisplayName(displayName.getText())
                .setEmailAddress(emailAddress.getText())
                .setSmtp(smtpHost.getText())
                .setTls(tlsPort.getText())
                .setPassword(emailPassword.getText());
              // Save entered data 
              EmailInfoCommuncation.sendEmailInfo(ei);
              System.out.println("Data Saved");
            }
        });

        // Add cancel button and its action listener
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cancel action 
                // Go back to main question panel
                appFrame.goToQuestionPanel();
                System.out.println("Action Cancelled");
            }
        });
    }

    public void addText(JTextField text, JLabel label, String nameLabel) {
      label = new JLabel(nameLabel); 
      this.add(label); 
      text = new JTextField(); 
      text.setColumns(100); 
      this.add(text); 
    }
}