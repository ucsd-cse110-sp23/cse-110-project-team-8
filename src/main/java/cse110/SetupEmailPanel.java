package cse110;

import javax.swing.JPanel;
import javax.swing.JTextArea;


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
