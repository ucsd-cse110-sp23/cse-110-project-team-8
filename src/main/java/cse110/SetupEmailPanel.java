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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SetupEmailPanel extends JPanel {
    AppFrame appFrame;

    // UI elements
    private JTextArea firstName;
    private JTextArea lastName;
    private JTextArea displayName;
    private JTextArea emailAddress;
    private JTextArea smtpHost;
    private JTextArea tlsPort;
    private JTextArea emailPassword;
    private JButton saveButton;
    private JButton cancelButton;

    // Constructor
    public SetupEmailPanel(AppFrame appFrame) {
        this.appFrame = appFrame;
        // Instantiate UI elements
        firstName = new JTextArea();
        lastName = new JTextArea();
        displayName = new JTextArea();
        emailAddress = new JTextArea();
        smtpHost = new JTextArea();
        tlsPort = new JTextArea();
        emailPassword = new JTextArea();

        // Set border to JTextAreas
        firstName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lastName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        displayName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        emailAddress.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        smtpHost.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tlsPort.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        emailPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Setup layout (you can adjust layout to your preference)
        this.setLayout(new GridLayout(9, 1)); // Set grid layout
        this.add(new JLabel("First Name:"));
        this.add(firstName);
        this.add(new JLabel("Last Name:"));
        this.add(lastName);
        this.add(new JLabel("Display Name:"));
        this.add(displayName);
        this.add(new JLabel("Email Address:"));
        this.add(emailAddress);
        this.add(new JLabel("SMTP Host:"));
        this.add(smtpHost);
        this.add(new JLabel("TLS Port:"));
        this.add(tlsPort);
        this.add(new JLabel("Email Password:"));
        this.add(emailPassword);

        // Add save button and its action listener
        this.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
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
        this.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cancel action 
                // Go back to main question panel
                appFrame.goToQuestionPanel();
                System.out.println("Action Cancelled");
            }
        });
    }
}