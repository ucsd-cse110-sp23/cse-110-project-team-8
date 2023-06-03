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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class SetupEmailPanel extends JPanel{
    private JTextArea firstName; 
    private JTextArea lastName; 
    private JTextArea displayName; 
    private JTextArea emailAddress; 
    private JTextArea smtpHost; 
    private JTextArea tlsPort; 
    private JTextArea emailPassword; 
    SetupEmailPanel() {
      firstName = new JTextArea(); 
      firstName.setLineWrap(true);

      lastName = new JTextArea(); 
      lastName.setLineWrap(true); 

      displayName = new JTextArea(); 
      displayName.setLineWrap(true); 

      emailAddress = new JTextArea(); 
      emailAddress.setLineWrap(true); 

      smtpHost = new JTextArea(); 
      smtpHost.setLineWrap(true); 

      tlsPort = new JTextArea(); 
      tlsPort.setLineWrap(true); 

      emailPassword = new JTextArea(); 
      emailPassword.setLineWrap(true); 

      this.add(firstName);
      this.add(lastName); 
      this.add(displayName); 
      this.add(smtpHost);
      this.add(emailAddress); 
      this.add(tlsPort); 
      this.add(emailPassword);
    }
}
