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
import java.util.ArrayList;
import java.util.List;


public class Canvas extends JPanel {

    // drawing modes  
    public static final String MODE_LINE = "Line";
    public static final String MODE_RECTANGLE = "Rectangle";
    public static final String MODE_OVAL = "Oval";
    public static final String MODE_PENCIL = "Pencil";
    public static final String MODE_ERASER = "Eraser";

    private List<Shape> shapes = new ArrayList<>();
    private Shape currentShape = null;
    private String currentMode = MODE_LINE;
    private Color currentColor = Color.WHITE;
    private boolean isSolid = false;
    private boolean isDotted = false;

    private int x1, y1; // Start point for drag operations

    // Strokes
    private static final BasicStroke SOLID_STROKE = new BasicStroke(2);
    private static final BasicStroke DOTTED_STROKE = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    private static final BasicStroke PENCIL_STROKE = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final BasicStroke ERASER_STROKE = new BasicStroke(18, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);


    public Canvas() {
        setBackground(Color.BLACK);

        // mouse Listeners for drawing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();

                if (currentMode.equals(MODE_PENCIL) || currentMode.equals(MODE_ERASER)) {
                    Color drawColor = currentMode.equals(MODE_ERASER) ? getBackground() : currentColor;
                    Stroke stroke = currentMode.equals(MODE_ERASER) ? ERASER_STROKE : PENCIL_STROKE;
                    currentShape = new PencilStroke(drawColor, stroke);
                    ((PencilStroke) currentShape).addPoint(x1, y1);
                    shapes.add(currentShape);
                } else {
                    
                    currentShape = null;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if ((currentMode.equals(MODE_PENCIL) || currentMode.equals(MODE_ERASER))) {
                    // Pencil/Eraser is already added to shapes list in mousePressed
                    currentShape = null;
                } else if (currentShape != null && (currentMode.equals(MODE_LINE) || currentMode.equals(MODE_RECTANGLE) || currentMode.equals(MODE_OVAL))) {
                    // Finalize the shape
                    int x2 = e.getX();
                    int y2 = e.getY();
                    Stroke stroke = isDotted ? DOTTED_STROKE : SOLID_STROKE;

                    if (currentMode.equals(MODE_LINE)) {
                        shapes.add(new Line(x1, y1, x2, y2, currentColor, stroke));
                    } if (currentMode.equals(MODE_RECTANGLE)) {
                        shapes.add(new Rectangle(x1, y1, x2, y2, currentColor, stroke, isSolid));
                    } if (currentMode.equals(MODE_OVAL)) {
                        shapes.add(new Oval(x1, y1, x2, y2, currentColor, stroke, isSolid));
                    }
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x2 = e.getX();
                int y2 = e.getY();

                if (currentMode.equals(MODE_PENCIL) || currentMode.equals(MODE_ERASER)) {
                    if (currentShape instanceof PencilStroke) {
                        ((PencilStroke) currentShape).addPoint(x2, y2);
                        repaint();
                    }
                } else {
                    // For shapes, create a temporary shape for preview
                    Stroke stroke = isDotted ? DOTTED_STROKE : SOLID_STROKE;
                    Color drawColor = currentColor;

                    if (currentMode.equals(MODE_LINE)) {
                        currentShape = new Line(x1, y1, x2, y2, drawColor, stroke);
                    } else if (currentMode.equals(MODE_RECTANGLE)) {
                        currentShape = new Rectangle(x1, y1, x2, y2, drawColor, stroke, isSolid);
                    } else if (currentMode.equals(MODE_OVAL)) {
                        currentShape = new Oval(x1, y1, x2, y2, drawColor, stroke, isSolid);
                    }
                    repaint(); // Repaint to show the temporary shape
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all finalized shapes
        for (Shape shape : shapes) {
            shape.draw(g);
        }

        // Draw the temporary shape (preview during drag)
        if (currentShape != null && (currentMode.equals(MODE_LINE) || currentMode.equals(MODE_RECTANGLE) || currentMode.equals(MODE_OVAL))) {
            currentShape.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

  
    public void setMode(String mode) {
        this.currentMode = mode;
        this.currentShape = null; // Clear temporary shape
    }

    public void setColor(Color color) {
        this.currentColor = color;
    }

    public void setSolid(boolean solid) {
        this.isSolid = solid;
    }

    public void setDotted(boolean dotted) {
        this.isDotted = dotted;
    }

    public void clear() {
        shapes.clear();
        currentShape = null;
        repaint();
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            repaint();
        }
    }
}
