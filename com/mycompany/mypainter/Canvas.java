package com.mycompany.mypainter;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Canvas extends JPanel {

    // Drawing modes
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

    private List<ImageData> images = new ArrayList<>();
    private ImageData selectedImage = null;

    private int x1, y1; // Start point for shapes
    private boolean dragImage = false;
    private int offsetX, offsetY;

    // Strokes
    private static final BasicStroke SOLID_STROKE = new BasicStroke(2);
    private static final BasicStroke DOTTED_STROKE = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    private static final BasicStroke PENCIL_STROKE = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final BasicStroke ERASER_STROKE = new BasicStroke(18, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    public Canvas() {
        setBackground(Color.BLACK);

        // Mouse Listener for pressing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mx = e.getX();
                int my = e.getY();

                // Check if mouse is on any image (topmost first)
                selectedImage = null;
                for (int i = images.size() - 1; i >= 0; i--) {
                    ImageData data = images.get(i);
                    int imgWidth = data.image.getWidth() / 2;
                    int imgHeight = data.image.getHeight() / 2;

                    if (mx >= data.x && mx <= data.x + imgWidth
                            && my >= data.y && my <= data.y + imgHeight) {
                        selectedImage = data;
                        dragImage = true;
                        offsetX = mx - data.x;
                        offsetY = my - data.y;
                        return; // skip shapes while dragging image
                    }
                }

                // draw shapes
                x1 = mx;
                y1 = my;
                dragImage = false;

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
                dragImage = false;

                if (currentMode.equals(MODE_PENCIL) || currentMode.equals(MODE_ERASER)) {
                    currentShape = null;
                } else if (currentShape != null
                        && (currentMode.equals(MODE_LINE) || currentMode.equals(MODE_RECTANGLE) || currentMode.equals(MODE_OVAL))) {
                    int x2 = e.getX();
                    int y2 = e.getY();
                    Stroke stroke = isDotted ? DOTTED_STROKE : SOLID_STROKE;

                    if (currentMode.equals(MODE_LINE)) {
                        shapes.add(new Line(x1, y1, x2, y2, currentColor, stroke));
                    }
                    if (currentMode.equals(MODE_RECTANGLE)) {
                        shapes.add(new Rectangle(x1, y1, x2, y2, currentColor, stroke, isSolid));
                    }
                    if (currentMode.equals(MODE_OVAL)) {
                        shapes.add(new Oval(x1, y1, x2, y2, currentColor, stroke, isSolid));
                    }
                    repaint();
                }
            }
        });

        // Mouse Motion Listener for dragging
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int mx = e.getX();
                int my = e.getY();

                // Drag selected image
                if (dragImage && selectedImage != null) {
                    selectedImage.x = mx - offsetX;
                    selectedImage.y = my - offsetY;
                    repaint();
                    return;
                }

                // Pencil / Eraser drawing
                if (currentMode.equals(MODE_PENCIL) || currentMode.equals(MODE_ERASER)) {
                    if (currentShape instanceof PencilStroke) {
                        ((PencilStroke) currentShape).addPoint(mx, my);
                        repaint();
                    }
                } else {
                    // Shapes preview
                    Stroke stroke = isDotted ? DOTTED_STROKE : SOLID_STROKE;
                    Color drawColor = currentColor;

                    if (currentMode.equals(MODE_LINE)) {
                        currentShape = new Line(x1, y1, mx, my, drawColor, stroke);
                    } else if (currentMode.equals(MODE_RECTANGLE)) {
                        currentShape = new Rectangle(x1, y1, mx, my, drawColor, stroke, isSolid);
                    } else if (currentMode.equals(MODE_OVAL)) {
                        currentShape = new Oval(x1, y1, mx, my, drawColor, stroke, isSolid);
                    }
                    repaint();
                }
            }
        });
    }

    // Load image method
    public void loadImage(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            if (img != null) {
                ImageData data = new ImageData(img, 0, 0); // initial position
                images.add(data);
                selectedImage = data;
                repaint();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all images
        for (ImageData data : images) {
            if (data.image != null) {
                int imgWidth = data.image.getWidth() / 2; // resize
                int imgHeight = data.image.getHeight() / 2;
                g.drawImage(data.image, data.x, data.y, imgWidth, imgHeight, null);
            }
        }

        // Draw finalized shapes
        for (Shape shape : shapes) {
            shape.draw(g);
        }

        // Draw temporary shape preview
        if (currentShape != null
                && (currentMode.equals(MODE_LINE) || currentMode.equals(MODE_RECTANGLE) || currentMode.equals(MODE_OVAL))) {
            currentShape.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    // Setters for paint modes
    public void setMode(String mode) {
        this.currentMode = mode;
        this.currentShape = null;
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

    // Functions
    public void clear() {
        shapes.clear();
        images.clear();
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
