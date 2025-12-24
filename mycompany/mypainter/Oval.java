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

class Oval extends Shape {
    private boolean isSolid;

    public Oval(int x1, int y1, int x2, int y2, Color color, Stroke stroke, boolean isSolid) {
        super(x1, y1, x2, y2, color, stroke);
        this.isSolid = isSolid;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = setupGraphics(g);
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x1 - x2);
        int height = Math.abs(y1 - y2);

        if (isSolid) {
            g2d.fillOval(x, y, width, height);
        } else {
            g2d.drawOval(x, y, width, height);
        }
    }
    
    
    
    
}