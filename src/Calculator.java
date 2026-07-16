package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator implements ActionListener {

    // 1. Declare Variables (State of the Calculator)
    private JFrame window;
    private JTextField displayField;
    
    private String currentNumber = "";
    private String previousNumber = "";
    private String selectedOperator = "";
    private boolean isError = false;

    // 2. Constructor (Sets up the application when created)
    public Calculator() {
        // --- MACOS FIX --- 
        // Force the application to use the standard cross-platform Java look
        // rather than the strict macOS look, allowing custom button colors.
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the main window
        window = new JFrame("Simple Java Calculator");
        window.setSize(400, 550);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null); // Centers the window on the screen
        window.setResizable(false);
        window.setLayout(new BorderLayout()); // Divides window into North, South, East, West, Center

        // Create the display at the top
        displayField = new JTextField();
        displayField.setPreferredSize(new Dimension(400, 100));
        displayField.setBackground(Color.BLACK);
        displayField.setForeground(Color.WHITE);
        displayField.setFont(new Font("Arial", Font.BOLD, 48));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false); // Prevents user from typing with keyboard
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the display to the top (North) of the window
        window.add(displayField, BorderLayout.NORTH);

        // Create the panel for buttons
        JPanel buttonPanel = new JPanel();
        // GridLayout: 5 rows, 4 columns, 10px horizontal gap, 10px vertical gap
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(new Color(32, 32, 32)); // Dark charcoal background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Array representing the labels of all our buttons
        String[] buttonLabels = {
            "AC", "", "", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };

        // Loop through the labels to create buttons
        for (int i = 0; i < buttonLabels.length; i++) {
            String label = buttonLabels[i];
            
            // If the label is empty, we create an invisible label to fill the grid space
            if (label.equals("")) {
                buttonPanel.add(new JLabel(""));
                continue;
            }

            // Create the button
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setFocusPainted(false); // Removes the ugly square border when clicked
            
            // Apply specific colors based on the button type
            if (label.equals("=")) {
                button.setBackground(Color.RED);
                button.setForeground(Color.WHITE);
            } else if (label.equals("AC") || label.equals("/") || label.equals("*") || label.equals("-") || label.equals("+")) {
                button.setBackground(Color.DARK_GRAY);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(64, 64, 64)); // Lighter gray for numbers
                button.setForeground(Color.WHITE);
            }

            // --- MACOS BUTTON COLOR FIX ---
            // These three lines force the OS to paint the background color we requested
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            // ------------------------------

            // Tell the button to send click events to this class
            button.addActionListener(this);

            // Add the button to our grid panel
            buttonPanel.add(button);
        }

        // Add the button panel to the center of the window
        window.add(buttonPanel, BorderLayout.CENTER);

        // Finally, make the window visible
        window.setVisible(true);
    }

    // 3. Event Handling (What happens when a button is clicked)
    @Override
    public void actionPerformed(ActionEvent event) {
        // Get the text of the button that was clicked
        String command = event.getActionCommand();

        // If the calculator is in an error state, lock it until AC is pressed
        if (isError) {
            if (command.equals("AC")) {
                resetCalculator();
            }
            return; // Stop processing other clicks
        }

        // Handle "Clear" button
        if (command.equals("AC")) {
            resetCalculator();
        } 
        // Handle "Equals" button
        else if (command.equals("=")) {
            performCalculation();
        } 
        // Handle Math Operators
        else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
            // Only set the operator if we have a number to work with
            if (!currentNumber.isEmpty()) {
                previousNumber = currentNumber;
                currentNumber = "";
                selectedOperator = command;
            }
        } 
        // Handle Numbers and Decimal Point
        else {
            // Prevent multiple decimal points in one number
            if (command.equals(".") && currentNumber.contains(".")) {
                return; // Do nothing
            }
            
            // Append the clicked number to our current number string
            currentNumber = currentNumber + command;
            displayField.setText(currentNumber);
        }
    }

    // 4. Calculation Logic
    private void performCalculation() {
        // Ensure we have all necessary parts to calculate
        if (previousNumber.isEmpty() || currentNumber.isEmpty() || selectedOperator.isEmpty()) {
            return;
        }

        try {
            // Convert our strings into decimal numbers
            double num1 = Double.parseDouble(previousNumber);
            double num2 = Double.parseDouble(currentNumber);
            double result = 0.0;

            // Perform math based on the selected operator
            switch (selectedOperator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        handleError();
                        return; // Stop calculation
                    }
                    result = num1 / num2;
                    break;
            }

            // Check if the result is a whole number (e.g., 5.0). If so, format it as an integer (5)
            if (result % 1 == 0) {
                int intResult = (int) result;
                currentNumber = String.valueOf(intResult);
            } else {
                currentNumber = String.valueOf(result);
            }

            // Update the display and reset background variables
            displayField.setText(currentNumber);
            selectedOperator = "";
            previousNumber = "";

        } catch (NumberFormatException exception) {
            // If something goes wrong parsing the numbers, safely error out
            handleError();
        }
    }

    // 5. Helper Methods (Keeps our code clean and readable)
    private void resetCalculator() {
        currentNumber = "";
        previousNumber = "";
        selectedOperator = "";
        isError = false;
        displayField.setText("");
    }

    private void handleError() {
        isError = true;
        displayField.setText("Error");
    }
}