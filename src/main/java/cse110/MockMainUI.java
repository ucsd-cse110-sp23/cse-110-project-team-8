package cse110;

class MockAppFrame extends AppFrame {
    @override
    String transcribePrompt() {
        return "What is 5 inches in centimeters";
    }

    @override
    String getGPTResponse(String prompt) {
        return "Centimeters";
    }
}

public class MockMainUI {
    public static void main(String args[]) {
        new MockAppFrame();  
    }
}
