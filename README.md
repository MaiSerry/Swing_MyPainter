# 🎨 Swing Painter App

![Java](https://img.shields.io/badge/Java-Swing-blue)

A feature-rich drawing application built using **Java Swing**, designed to mimic classic paint tools.  
This project demonstrates solid **object-oriented design**, separating UI, canvas logic, and drawable objects into clean, maintainable classes.

---

## ✨ Features

### 🖌 Drawing Tools
- **Line** – Draw straight lines
- **Rectangle** – Draw rectangles (solid or outline)
- **Oval** – Draw ovals (solid or outline)
- **Pencil** – Freehand drawing
- **Eraser** – Freehand erasing

### 🎨 Styles
- **Solid / Outline** toggle
- **Dotted / Solid stroke** toggle
- Color selection:
  - Black
  - Red
  - Green
  - Blue

### 🧩 Canvas Utilities
- **Undo** – Removes the last drawn action
- **Clear** – Clears the entire canvas

### 💾 File Operations
- **SAVE**
  - Exports the full canvas as an image
  - Captures **all drawings, shapes, and inserted images**
- **OPEN**
  - Loads external images into the canvas
  - Allows drawing **on top of images**
  - Images can be moved when image mode is active

---

## 🛠 Requirements

- **Java Development Kit (JDK)** 8 or higher
- **Java Swing** (included with JDK)

---

## ▶️ Installation & Running

### 1️⃣ Save Files
Ensure all `.java` files are placed in the same directory.

### 2️⃣ Compile
```bash
javac *.java
```

### 3. Run

Execute the main class:

```bash
java MyPainter
```

## Usage

1.  **Select a Mode:** Click on a mode button (e.g., `Line`, `Rectangle`, `Pencil`).
2.  **Select Style/Color:** Choose a color and toggle the `Solid` or `Dotted` radioboxes as desired.
3.  **Draw:** Click and drag the mouse on the white canvas area.
   <img width="1431" height="641" alt="image" src="https://github.com/user-attachments/assets/4783aaa8-6fb6-49a7-a117-625d4733ff63" />

5.  **Manage:** Use the `Undo` button to reverse the last action or `Clear` to wipe the canvas clean.
6.  **Save:** Use the `Save` button to save all drawings in your panel.
7.  **Open:** Use the `Open` button to drag one or more images from your local PC.
