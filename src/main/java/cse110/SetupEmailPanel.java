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
  AppFrame appFrame;
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
      firstName = addText(firstName,firstNameLabel,"First Name");
      lastName = addText(lastName, lastNameLabel, "Last Name");
      displayName = addText(displayName, displayNameLabel, "Display Name");
      emailAddress = addText(emailAddress, emailAddressLabel, "Email Address");
      smtpHost = addText(smtpHost, smtpHostLabel, "SMTP Host");
      tlsPort = addText(tlsPort, tlsPortLabel, "TLS Port");
      emailPassword = addText(emailPassword, emailPasswordLabel, "Email Password");

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
                .setFirstName(firstName.getText().toString())
                .setLastName(lastName.getText().toString())
                .setdisplayName(displayName.getText().toString())
                .setEmailAddress(emailAddress.getText().toString())
                .setSmtp(smtpHost.getText().toString())
                .setTls(tlsPort.getText().toString())
                .setPassword(emailPassword.getText().toString());

              // Save entered data 
              EmailInfoCommuncation.sendEmailInfo(ei);
              System.out.println("Data Saved");

              // Go back to main question panel
              appFrame.goToQuestionPanel();
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

    public JTextField addText(JTextField text, JLabel label, String nameLabel) {
      label = new JLabel(nameLabel); 
      this.add(label); 
      text = new JTextField(); 
      text.setColumns(70); 
      this.add(text); 
      return text;
    }
}