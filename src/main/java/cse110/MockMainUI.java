package cse110;

class MockAppFrame extends AppFrame {
    @override
    String transcribePrompt() {
        return "testPrompt";
    }

    @override
    String getGPTResponse(String prompt) {
        return "testResponse";
    }
}

public class MockMainUI {
    public static void main(String args[]) {
        new MockAppFrame();  
    }
}
