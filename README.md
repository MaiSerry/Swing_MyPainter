# Swing Painter App

A simple, feature-rich drawing application built using Java Swing, designed to mimic the functionality of classic painting tools. This project demonstrates object-oriented design principles in Java, utilizing separate classes for the canvas, control panel, and different drawable shapes.

## ✨ Features

The application provides a full set of drawing tools and controls, organized by category:

### Functions
*   **Clear**: Clears all drawn shapes from the canvas.
*   **Undo**: Removes the last drawn shape or action (including eraser strokes).

### Paint Modes
*   **Line**: Draws straight lines between the mouse press and release points.
*   **Rectangle**: Draws rectangles defined by the drag area.
*   **Oval**: Draws ovals defined by the drag area.
*   **Pencil**: Allows freehand drawing (a series of connected points).
*   **Eraser**: Allows freehand erasing by drawing with the background color.

### Style
*   **Solid** (Checkbox): Toggles between drawing filled (solid) or outlined shapes (for Rectangle and Oval modes).
*   **Dotted** (Checkbox): Toggles between solid and dotted line strokes.

### Colors
*   **Black**, **Red**, **Green**, **Blue**: Selects the color for the next shape to be drawn.

### Bonus Features
*   **SAVE**, **OPEN**: Placeholder buttons for future file I/O functionality.

## 🛠️ Prerequisites

To compile and run this application, you need:

*   **Java Development Kit (JDK)**: Version 8 or higher.
*   **Java Swing**: Included in the standard JDK.

## 📁 File Structure

The project is structured into multiple files, with each class in its own file for clarity and maintainability:

*   **`MyPainter.java`**: Contains the `main` method and sets up the primary `JFrame`.
*   **`DrawingCanvas.java`**: The main `JPanel` where all drawing occurs. Handles mouse events and manages the list of drawn shapes.
*   **`StrPanel.java`**: The control panel (`JPanel`) containing all buttons, checkboxes, and their action listeners.
*   **`DrawnShape.java`**: Interface defining the `draw(Graphics g)` method for all drawable objects.
*   **`AbstractShape.java`**: Abstract base class for shapes with start/end points, handling color and stroke setup.
*   **`DrawnLine.java`**: Implementation for drawing a line.
*   **`DrawnRectangle.java`**: Implementation for drawing a rectangle (outlined or solid).
*   **`DrawnOval.java`**: Implementation for drawing an oval (outlined or solid).
*   **`PencilStroke.java`**: Implementation for freehand drawing and erasing (stores a list of points).

## 🚀 Installation and Running

Follow these steps to get the application running on your local machine.

### 1. Save Files

Ensure all nine `.java` files are saved in the same directory.

### 2. Compile

Open your terminal or command prompt, navigate to the directory, and compile all source files:

```bash
javac *.java
```

### 3. Run

Execute the main class:

```bash
java MyPainter
```

## 📝 Usage

1.  **Select a Mode:** Click on a mode button (e.g., `Line`, `Rectangle`, `Pencil`).
2.  **Select Style/Color:** Choose a color and toggle the `Solid` or `Dotted` checkboxes as desired.
3.  **Draw:** Click and drag the mouse on the white canvas area.
4.  **Manage:** Use the `Undo` button to reverse the last action or `Clear` to wipe the canvas clean.
