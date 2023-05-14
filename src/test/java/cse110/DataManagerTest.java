package cse110;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;


public class DataManagerTest {
    String jsonFile;

    static class TestableDataManager extends DataManager {
        public static void setDataFile(String fileName) {
            dataFile = fileName;
        }
    }

    @BeforeEach
    void setUp() {
        jsonFile = "lib/testData.json";
        TestableDataManager.setDataFile(jsonFile);
    }

    @Test
    void testSetData() {
        ArrayList<QuestionData> testData = new ArrayList<>();
        QuestionData qd1 = new QuestionData("prompt1", "answer1");
        QuestionData qd2 = new QuestionData("prompt2", "answer2\ntesting newline");
        testData.add(qd1);
        testData.add(qd2);

        TestableDataManager.setData(testData);
        TestableDataManager.saveData();
        
        assertEquals(2, TestableDataManager.getData().size());
        assertEquals(qd1.getPrompt(), TestableDataManager.getData().get(0).getPrompt());
        assertEquals(qd1.getResponse(), TestableDataManager.getData().get(0).getResponse());
    }

    @Test
    void testAddData() {
        ArrayList<QuestionData> testData = new ArrayList<>();
        QuestionData qd1 = new QuestionData("prompt1", "answer1");
        QuestionData qd2 = new QuestionData("prompt2", "answer2\ntesting newline");
        testData.add(qd1);

        TestableDataManager.setData(testData);
        TestableDataManager.saveData();

        TestableDataManager.addData(qd2);
        
        assertEquals(2, TestableDataManager.getData().size());
        assertEquals(qd2.getPrompt(), TestableDataManager.getData().get(1).getPrompt());
        assertEquals(qd2.getResponse(), TestableDataManager.getData().get(1).getResponse());
    }

    @Test
    void testRemoveData() {
        ArrayList<QuestionData> testData = new ArrayList<>();
        QuestionData qd1 = new QuestionData("prompt1", "answer1");
        QuestionData qd2 = new QuestionData("prompt2", "answer2\ntesting newline");
        testData.add(qd1);
        testData.add(qd2);

        TestableDataManager.setData(testData);
        TestableDataManager.saveData();

        TestableDataManager.removeData(0);
        
        assertEquals(1, TestableDataManager.getData().size());
        assertEquals(qd2.getPrompt(), TestableDataManager.getData().get(0).getPrompt());
        assertEquals(qd2.getResponse(), TestableDataManager.getData().get(0).getResponse());
    }

    @Test
    void testSaveAndLoadData() {
        ArrayList<QuestionData> testData = new ArrayList<>();
        QuestionData qd1 = new QuestionData("prompt1", "answer1");
        QuestionData qd2 = new QuestionData("prompt2", "answer2\ntesting newline");
        testData.add(qd1);
        testData.add(qd2);

        // Save data
        TestableDataManager.setData(testData);
        TestableDataManager.saveData();

        // Clear list in data variable
        TestableDataManager.setData(new ArrayList<>());
        assertEquals(0, TestableDataManager.getData().size());

        // Load in saved data
        TestableDataManager.loadData();

        assertEquals(2, TestableDataManager.getData().size());
        assertEquals(qd1.getPrompt(), TestableDataManager.getData().get(0).getPrompt());
        assertEquals(qd1.getResponse(), TestableDataManager.getData().get(0).getResponse());
        assertEquals(qd2.getPrompt(), TestableDataManager.getData().get(1).getPrompt());
        assertEquals(qd2.getResponse(), TestableDataManager.getData().get(1).getResponse());
    }
}
