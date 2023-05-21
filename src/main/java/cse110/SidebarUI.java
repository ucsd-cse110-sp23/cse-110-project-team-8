package cse110;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SidebarUI extends JPanel implements ListSelectionListener {
    private MainPanel mainPanel;
    private JList<String> jlist;
    private ArrayList<String> historyList;
    private int selectedIndex;
    private int UNSELECTED = -1;

    Color gray = new Color(218, 229, 234);
    int panelWidth = 350;
    int panelHeight = 400;
    int listWidth = panelWidth;
    int listHeight = panelHeight-80;

    public SidebarUI(MainPanel mainPanel ,ArrayList<QuestionData> dataList) {
        this.mainPanel = mainPanel;

        ArrayList<String> newlist = new ArrayList<>();
        if (dataList != null){
            for (int i=0; i<dataList.size();i++){
                newlist.add(dataList.get(i).getPrompt());
            }
        }

        this.historyList = newlist;
        this.selectedIndex = UNSELECTED;

        this.setPreferredSize(new Dimension(panelWidth, panelHeight)); // set size of task
        this.setBackground(gray); // set background color of task
        this.setLayout(new BorderLayout()); // set layout of task

        this.jlist = new JList<String>((String[]) historyList.toArray(new String[historyList.size()]));
        this.jlist.setPreferredSize(new Dimension(listWidth, listHeight));
        this.jlist.addListSelectionListener(this);
        this.add(this.jlist, BorderLayout.NORTH);

        SidebarButtonPanel buttonPanel = new SidebarButtonPanel(panelWidth, panelHeight-listHeight);
        buttonPanel.clearButton.addActionListener(
            (ActionEvent e) -> {
                // Clear historyList
                this.historyList.clear();
                
                // Clear jlist
                this.jlist.setListData(new String[0]);
                this.jlist.revalidate();
                this.jlist.repaint();

                // Clear stored data and save
                ServerCommunication.sendClearRequest();
            }
        );
        buttonPanel.removeButton.addActionListener(
            (ActionEvent e) -> {
                if (this.selectedIndex == UNSELECTED) return;
                this.deleteItem(this.selectedIndex);
            }
        );
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public String deleteItem(int index) {
        String deletedString = this.historyList.remove(index);
        this.jlist.setListData(this.historyList.toArray(new String[0]));
        jlist.revalidate();
        jlist.repaint();

        // Remove the corresponding data from the JSON file
        ServerCommunication.sendRemoveRequest(index);

        return deletedString;
    }

    public boolean addItem(String prompt) {
        boolean addedPrompt = this.historyList.add(prompt);
        this.jlist.setListData(this.historyList.toArray(new String[this.historyList.size()]));
        jlist.revalidate();
        jlist.repaint();
        return addedPrompt;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Set selectedIndex for history item deletion
        this.selectedIndex = this.jlist.getSelectedIndex();

        // Check if an item is selected
        if (this.selectedIndex != UNSELECTED) {
            QuestionData qd = ServerCommunication.sendGetRequest(this.selectedIndex);
            this.mainPanel.updateData(qd.getPrompt(), qd.getResponse());
        }
    }

    class SidebarButtonPanel extends JPanel {
        JButton clearButton;
        JButton removeButton;

        SidebarButtonPanel(int width, int height) {
            this.setPreferredSize(new Dimension(width, height)); // set size of task
            this.setBackground(gray); // set background color of task
            this.setLayout(new BorderLayout()); // set layout of task

            clearButton = new JButton("Clear");
            clearButton.setPreferredSize(new Dimension(width/2, height));
            this.add(clearButton, BorderLayout.WEST);

            removeButton = new JButton("Remove");
            removeButton.setPreferredSize(new Dimension(width/2, height));
            this.add(removeButton, BorderLayout.EAST);
        }
    }
}
