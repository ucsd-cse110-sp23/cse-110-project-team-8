package cse110;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SidebarUI extends JPanel implements ListSelectionListener {
    private MainPanel mainPanel;
    private JList<String> jlist;
    private JScrollPane scrollPane;
    private ArrayList<String> historyList;
    private int selectedIndex;
    private int UNSELECTED = -1;
    Color gray = new Color(218, 229, 234);
    int panelWidth = 350;
    int panelHeight = 600;
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
        // this.jlist.setPreferredSize(new Dimension(listWidth, listHeight));

        // Create a JScrollPane and add the JList to it
        this.scrollPane = new JScrollPane(this.jlist);
        scrollPane.setPreferredSize(new Dimension(listWidth, listHeight));
        this.add(scrollPane, BorderLayout.NORTH);
                        
        this.jlist.addListSelectionListener(this);
        /* 
        buttonPanel.removeButton.addActionListener(
            (ActionEvent e) -> {
                if (this.selectedIndex == UNSELECTED) return;
                this.deleteItem(this.selectedIndex);
            }
        );
        */
        mainPanel.add(this); 
    }

    public void clearAll() {
        // Clear historyList
        this.historyList.clear();

        // Clear jlist
        this.jlist.setListData(new String[0]);
        this.jlist.revalidate();
        this.jlist.repaint();

        // Clear stored data and save
        ServerCommunication.sendClearRequest();      
    }

    public String deleteItem() {
        int index = this.selectedIndex; 
        String deletedString = this.historyList.remove(index);
        this.jlist.setListData(this.historyList.toArray(new String[0]));

        // call revalidate() and repaint() on scrollPane
        this.scrollPane.revalidate();
        this.scrollPane.repaint();

        // Remove the corresponding data from the JSON file
        ServerCommunication.sendRemoveRequest(index);

        return deletedString;
    }

    public boolean addItem(String prompt) {
        boolean addedPrompt = this.historyList.add(prompt);
        this.jlist.setListData(this.historyList.toArray(new String[this.historyList.size()]));

        // call revalidate() and repaint() on scrollPane
        this.scrollPane.revalidate();
        this.scrollPane.repaint();

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

}
