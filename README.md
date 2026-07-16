# Simple Java Swing Calculator

## Description
A clean, modern desktop calculator built entirely from scratch using Java Swing and AWT. This project was developed to solidify core Object-Oriented Programming (OOP) concepts, event-driven programming, and graphical user interface (GUI) design without relying on external libraries or drag-and-drop builders.

## Features
* **Basic Arithmetic:** Addition, subtraction, multiplication, and division.
* **Decimal Support:** Accurate floating-point calculations.
* **Error Handling:** Safely handles division by zero without crashing.
* **Modern UI:** Dark theme layout with distinct, color-coded action buttons.
* **Responsive Layout:** Utilizes `BorderLayout` and `GridLayout` to maintain structure.

## Technologies Used
* **Java (JDK 17+)**
* **Java Swing** (GUI toolkit)
* **Java AWT** (Layout managers and events)

## Project Structure
SimpleCalculator/
├── src/
│   ├── Main.java        # Entry point of the application
│   └── Calculator.java  # Core GUI and calculation logic
├── README.md
└── .gitignore

## How to Run
1. Clone this repository to your local machine.
2. Open the project in IntelliJ IDEA (Community Edition).
3. Navigate to `src/Main.java`.
4. Click the green "Run" button to launch the application.

## Future Improvements
* Add keyboard support (KeyListener) to type numbers directly.
* Include advanced mathematical functions (square root, power).
* Add a history log of previous calculations.

## What I Learned
Through this project, I gained hands-on experience with:
* **Event Handling:** Wiring up `ActionListener` to respond to user input.
* **State Management:** Tracking variables (`currentNumber`, `selectedOperator`) across different method calls.
* **Layout Managers:** Combining `BorderLayout` and `GridLayout` to organize UI components programmatically.

## License
MIT License - Free to use and modify.