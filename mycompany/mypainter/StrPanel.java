/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mypainter;

/**
 *
 * @author HELLo
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Structure Class
public class StrPanel extends JPanel {

    private Canvas canvas;

    // Colors Buttons
    JButton blackButton;
    JButton redButton;
    JButton greenButton;
    JButton blueButton;

    // Mode Buttons
    JToggleButton lineButton;
    JToggleButton rectButton;
    JToggleButton ovalButton;
    JToggleButton pencilButton;
    JToggleButton eraserButton;

    // Functions Buttons
    JButton clearButton;
    JButton undoButton;

    // CheckBox Buttons (using JCheckBox for Swing consistency)
    JCheckBox solidCheck;
    JCheckBox dottedCheck;

    // Bonus Buttons (Placeholder functionality)
    JButton saveButton;
    JButton openButton;


    public StrPanel(Canvas canvas) {
        this.canvas = canvas;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // --- Functions ---
        add(new JLabel("Functions:"));
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> canvas.clear());
        add(clearButton);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> canvas.undo());
        add(undoButton);

        // --- Paint Mode ---
        add(new JLabel("Paint Mode:"));
        ButtonGroup modeGroup = new ButtonGroup();

        // Helper to create mode buttons
        ActionListener modeListener = e -> {
            String command = ((JToggleButton) e.getSource()).getText();
            canvas.setMode(command);
        };

        lineButton = new JToggleButton(Canvas.MODE_LINE, true);
        lineButton.addActionListener(modeListener);
        modeGroup.add(lineButton);
        add(lineButton);
        canvas.setMode(Canvas.MODE_LINE); // Default mode

        rectButton = new JToggleButton(Canvas.MODE_RECTANGLE);
        rectButton.addActionListener(modeListener);
        modeGroup.add(rectButton);
        add(rectButton);

        ovalButton = new JToggleButton(Canvas.MODE_OVAL);
        ovalButton.addActionListener(modeListener);
        modeGroup.add(ovalButton);
        add(ovalButton);

        pencilButton = new JToggleButton(Canvas.MODE_PENCIL);
        pencilButton.addActionListener(modeListener);
        modeGroup.add(pencilButton);
        add(pencilButton);

        eraserButton = new JToggleButton(Canvas.MODE_ERASER);
        eraserButton.addActionListener(modeListener);
        modeGroup.add(eraserButton);
        add(eraserButton);

        // --- Style (Solid/Dotted) ---
        solidCheck = new JCheckBox("Solid");
        solidCheck.addActionListener(e -> canvas.setSolid(solidCheck.isSelected()));
        add(solidCheck);

        dottedCheck = new JCheckBox("Dotted");
        dottedCheck.addActionListener(e -> canvas.setDotted(dottedCheck.isSelected()));
        add(dottedCheck);

        // --- Colors ---
        add(new JLabel("Colors:"));

        // Black Button (Default color)
        blackButton = createColorButton(Color.WHITE, "WHITE", canvas);
        blackButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3)); // Highlight default
        add(blackButton);

        // Red Button
        redButton = createColorButton(Color.RED, "Red", canvas);
        add(redButton);

        // Green Button
        greenButton = createColorButton(Color.GREEN, "Green", canvas);
        add(greenButton);

        // Blue Button
        blueButton = createColorButton(Color.BLUE, "Blue", canvas);
        add(blueButton);

        // --- Bonus Buttons (Placeholder) ---
        add(new JLabel(" | ")); // Separator
        saveButton = new JButton("SAVE");
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Save functionality not implemented."));
        add(saveButton);

        openButton = new JButton("OPEN");
        openButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Open functionality not implemented."));
        add(openButton);
    }

    private JButton createColorButton(Color color, String name, Canvas canvas) {
        JButton button = new JButton();
        button.setBackground(color);
        button.setPreferredSize(new Dimension(20, 20));
        button.setToolTipText(name);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        button.addActionListener(e -> {
            canvas.setColor(color);
            // Reset borders of all color buttons
            JButton[] colorButtons = {blackButton, redButton, greenButton, blueButton};
            for (JButton b : colorButtons) {
                if (b != null) {
                    b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
            // Highlight the selected button
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        });
        return button;
    }
}
