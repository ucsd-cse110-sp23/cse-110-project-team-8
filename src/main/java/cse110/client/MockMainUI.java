package cse110.client;

class MockAppFrame extends AppFrame {
    @override
    String transcribePrompt() {
        return "Question. What is 5 inches in centimeters";
    }

    @override
    String getGPTResponse(String prompt) {
        return "5 inches is 12.7 centimeters.";
    }
}

public class MockMainUI {
    public static void main(String args[]) {
        new MockAppFrame();  
    }
}
