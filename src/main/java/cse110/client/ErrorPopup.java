package cse110.client;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.JButton; 

public class ErrorPopup {
    JLabel label;
    JButton acceptBtn; 
    Popup p;
    ErrorPopup(JFrame appFrame) {
        // create a panel
        JPanel panel = new JPanel();
        // create a label
        label = new JLabel("Error", JLabel.RIGHT);
        label.setFont(new Font("Verdana", Font.PLAIN, 35));
        panel.add(label);
        //create a button
        acceptBtn = new JButton("ok"); 
        panel.add(acceptBtn);
        // create a popup
        PopupFactory pf = new PopupFactory();
        p = pf.getPopup(appFrame, panel, AppFrame.WIDTH/2-150, AppFrame.HEIGHT/2-150);
    }
    void setMessage(String error) {
        label.setText(error);
    }
    void show() {
        p.show();
    }
    void hide() {
        p.hide();
    }

    public JButton getBtn() {
        return acceptBtn; 
    }
}
