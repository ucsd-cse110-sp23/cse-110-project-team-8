package cse110;

class MockAppFrame extends AppFrame {
    @override
    String transcribePrompt() {
        return "New question what is 5 inches in centimeters";
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
