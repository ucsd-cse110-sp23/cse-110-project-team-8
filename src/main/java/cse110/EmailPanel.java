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

public class EmailPanel extends JPanel {
    private JTextArea firstName; 
    private JTextArea lastName; 
    private JTextArea displayName; 
    private JTextArea emailAddress; 
    private JTextArea smtpHost; 
    private JTextArea tlsPort; 
    private JPasswordField emailPassword; 
    private JButton toAccountPanel;
    private JButton toMainPanel; 
    private JCheckBox autoCheck;

    EmailPanel() {
      firstName = new JTextArea(); 
      firstName.setBounds(360, 0, 430, 200);
      firstName.setLineWrap(true);

      lastName = new JTextArea(); 
      lastName.setBounds(360, 210, 430, 200);
      lastName.setLineWrap(true); 

      displayName = new JTextArea(); 
      displayName.setBounds(360, 420, 430, 200);
      displayName.setLineWrap(true); 

      emailAddress = new JTextArea(); 
      emailAddress.setBounds(360, 630, 430, 200);
      emailAddress.setLineWrap(true); 

      smtpHost = new JTextArea(); 
      smtpHost.setBounds(360, 840, 430, 200);
      smtpHost.setLineWrap(true); 

      tlsPort = new JTextArea(); 
      tlsPort.setBounds(360, 1050, 430, 200);
      tlsPort.setLineWrap(true); 

      emailPassword = new JPasswordField(); 
      emailPassword.setBounds(360, 1260, 430, 200);

      toMainPanel = new JButton("Login"); 
      toMainPanel.setFont(new Font("Sans-serif", Font.ITALIC, 50)); // set font

      toAccountPanel = new JButton("Back to Home"); 
      toAccountPanel.setFont(new Font("Sans-serif", Font.ITALIC, 50)); // set font

      autoCheck = new JCheckBox("Automatic Login");
      autoCheck.setFont(new Font("Sans serif", Font.ITALIC, 10));

      this.add(firstName);
      this.add(lastName); 
      this.add(displayName); 
      this.add(emailAddress); 
      this.add(smtpHost); 
      this.add(tlsPort); 
      this.add(emailPassword); 
      this.add(toMainPanel); 
      this.add(toAccountPanel);
      this.add(autoCheck);
    }
}
