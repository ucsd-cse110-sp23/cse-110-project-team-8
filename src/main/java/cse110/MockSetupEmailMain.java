package cse110;

class MockSetupFrame extends AppFrame {
    @override
    String transcribePrompt() {
        return "Set up email.";
    }
}

public class MockSetupEmailMain {
    public static void main(String args[]) {
        new MockSetupFrame();
    }
}
