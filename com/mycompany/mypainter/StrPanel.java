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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    JRadioButton solidCheck;
    JRadioButton dottedCheck;

    // Bonus Buttons (Placeholder functionality)
    JButton saveButton;
    JButton openButton;

    public StrPanel(Canvas canvas) {
        this.canvas = canvas;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // --- Functions ---
        add(new JLabel("Functions:  "));
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> canvas.clear());
        add(clearButton);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> canvas.undo());
        add(undoButton);

        // --- Paint Mode ---
        add(new JLabel("    Paint Mode:  "));
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
        solidCheck = new JRadioButton("Solid");
        solidCheck.addActionListener(e -> {
            if (solidCheck.isSelected()) {
                dottedCheck.setSelected(false); // deselect the other
                canvas.setSolid(true);
                canvas.setDotted(false);
            } else {
                canvas.setSolid(false);
            }
        });
        add(solidCheck);

        dottedCheck = new JRadioButton("Dotted");
        dottedCheck.addActionListener(e -> {
            if (dottedCheck.isSelected()) {
                solidCheck.setSelected(false); // deselect the other
                canvas.setDotted(true);
                canvas.setSolid(false);
            } else {
                canvas.setDotted(false);
            }
        });
        add(dottedCheck);

        // Separator Between Colors and Buttons
        add(new JLabel(" | "));

        // --- Colors ---
        add(new JLabel("  Colors:  "));

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

        // Separator Between Colors and Buttons
        add(new JLabel(" | "));

        // --- Bonus Buttons 
        // OpenButton
        openButton = new JButton("OPEN");
        openButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(StrPanel.this); // To Check Image Filtration
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                canvas.loadImage(selectedFile);
            }
        });
        add(openButton);

        // SaveButton
        saveButton = new JButton("SAVE");
        saveButton.addActionListener(e -> {
            // To choose where to cave
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Canvas As Image");
            fileChooser.setSelectedFile(new File("drawing.png"));
            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                // --- Important: capture only the Canvas, not the toolbar ---
                BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = image.createGraphics();

                // optional: fill background
                g2.setColor(canvas.getBackground());
                g2.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Draw the panel on the BufferedImage
                canvas.paintComponent(g2);
                g2.dispose();

                try {
                    // Save as PNG
                    ImageIO.write(image, "png", fileToSave);
                    JOptionPane.showMessageDialog(this, "Saved successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
                }
            }
        });
        add(saveButton);

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
