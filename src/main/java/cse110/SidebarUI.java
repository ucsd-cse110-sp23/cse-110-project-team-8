package cse110;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SidebarUI extends JPanel implements ListSelectionListener {
    private JList<String> jlist;
    private ArrayList<String> historyList;
    private int selectedIndex;
    private int UNSELECTED = -1;

    Color gray = new Color(218, 229, 234);
    int panelWidth = 150;
    int panelHeight = 400;
    int listWidth = panelWidth;
    int listHeight = panelHeight-80;

    public SidebarUI(ArrayList<QuestionData> dataList) {
        // TODO: Converts to arraylist of prompts, refactor to just use the QuestionDatas
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

        // new String[map1.size()];
        this.jlist = new JList<String>((String[]) historyList.toArray(new String[historyList.size()]));
        this.jlist.setPreferredSize(new Dimension(listWidth, listHeight));
        this.jlist.addListSelectionListener(this);
        this.add(this.jlist, BorderLayout.NORTH);

        SidebarButtonPanel buttonPanel = new SidebarButtonPanel(panelWidth, panelHeight-listHeight);
        buttonPanel.clearButton.addActionListener(
            (ActionEvent e) -> {
                // Clear jlist
                this.historyList.clear();
                this.jlist = new JList<>();

                // TODO: Clear history database
            }
        );
        buttonPanel.removeButton.addActionListener(
            (ActionEvent e) -> {
                if (this.selectedIndex == UNSELECTED) return;
                this.deleteItem(this.selectedIndex);

                // TODO: Remove from history database
            }
        );
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public String deleteItem(int index) {
        String deletedString = this.historyList.remove(index);
        this.jlist = new JList<String>(this.historyList.toArray(new String[0]));
        return deletedString;
    }

    public boolean addItem(String prompt) {
        boolean addedPrompt = this.historyList.add(prompt);
        this.jlist = new JList<String>(this.historyList.toArray(new String[0]));
        return addedPrompt;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Set selectedIndex for history item deletion
        this.selectedIndex = this.jlist.getSelectedIndex();

        // TODO: Set UI of MainUI for selected prompt.
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
