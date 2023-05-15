package cse110;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;

import java.util.ArrayList;


public class DataManagerTest {
    @AfterAll
    static void tearDown() {
        DataManager.setData(new ArrayList<>());
    }

    @Test
    void testSetData() {
        ArrayList<QuestionData> testData = new ArrayList<>();
        QuestionData qd1 = new QuestionData("prompt1", "answer1");
        QuestionData qd2 = new QuestionData("prompt2", "answer2\ntesting newline");
        testData.add(qd1);
        testData.add(qd2);

        DataManager.setData(testData);
        DataManager.saveData();
        
        assertEquals(2, DataManager.getData().size());
        assertEquals(qd1.getPrompt(), DataManager.getData().get(0).getPrompt());
        assertEquals(qd1.getResponse(), DataManager.getData().get(0).getResponse());
    }

    @Test
    void testAddData() {
        ArrayList<QuestionData> testData = new ArrayList<>();
        QuestionData qd1 = new QuestionData("prompt1", "answer1");
        QuestionData qd2 = new QuestionData("prompt2", "answer2\ntesting newline");
        testData.add(qd1);

        DataManager.setData(testData);
        DataManager.saveData();

        DataManager.addData(qd2);
        
        assertEquals(2, DataManager.getData().size());
        assertEquals(qd2.getPrompt(), DataManager.getData().get(1).getPrompt());
        assertEquals(qd2.getResponse(), DataManager.getData().get(1).getResponse());
    }

    @Test
    void testRemoveData() {
        ArrayList<QuestionData> testData = new ArrayList<>();
        QuestionData qd1 = new QuestionData("prompt1", "answer1");
        QuestionData qd2 = new QuestionData("prompt2", "answer2\ntesting newline");
        testData.add(qd1);
        testData.add(qd2);

        DataManager.setData(testData);
        DataManager.saveData();

        DataManager.removeData(0);
        
        assertEquals(1, DataManager.getData().size());
        assertEquals(qd2.getPrompt(), DataManager.getData().get(0).getPrompt());
        assertEquals(qd2.getResponse(), DataManager.getData().get(0).getResponse());
    }

    @Test
    void testSaveAndLoadData() {
        ArrayList<QuestionData> testData = new ArrayList<>();
        QuestionData qd1 = new QuestionData("prompt1", "answer1");
        QuestionData qd2 = new QuestionData("prompt2", "answer2\ntesting newline");
        testData.add(qd1);
        testData.add(qd2);

        // Save data
        DataManager.setData(testData);
        DataManager.saveData();

        // Load in saved data
        DataManager.loadData();

        assertEquals(2, DataManager.getData().size());
        assertEquals(qd1.getPrompt(), DataManager.getData().get(0).getPrompt());
        assertEquals(qd1.getResponse(), DataManager.getData().get(0).getResponse());
        assertEquals(qd2.getPrompt(), DataManager.getData().get(1).getPrompt());
        assertEquals(qd2.getResponse(), DataManager.getData().get(1).getResponse());
    }
}
