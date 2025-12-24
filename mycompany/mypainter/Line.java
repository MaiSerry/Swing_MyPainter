/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mypainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author HELLo
 */

class Line extends Shape {
    public Line(int x1, int y1, int x2, int y2, Color color, Stroke stroke) {
        super(x1, y1, x2, y2, color, stroke);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = setupGraphics(g);
        g2d.drawLine(x1, y1, x2, y2);
    }
}