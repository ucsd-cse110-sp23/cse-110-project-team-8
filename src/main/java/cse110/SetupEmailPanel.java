package cse110;

import javax.swing.JLabel; 
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton; 


public class SetupEmailPanel extends JPanel{
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

    SetupEmailPanel() {
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
    }

    public void addText(JTextField text, JLabel label, String nameLabel) {
      label = new JLabel(nameLabel); 
      this.add(label); 
      text = new JTextField(); 
      text.setColumns(100); 
      this.add(text); 
    }
}
