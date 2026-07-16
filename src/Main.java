package src;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // We use SwingUtilities.invokeLater to ensure our GUI runs on the Event Dispatch Thread.
        // This is a Java best practice to prevent the application from freezing or crashing.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create a new instance of our Calculator object
                new Calculator();
            }
        });
    }
}