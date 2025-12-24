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
abstract class Shape  {
    protected int x1, y1, x2, y2;
    protected Color color;
    protected Stroke stroke;

    public Shape() {
    }

    public Shape(int x1, int y1, int x2, int y2, Color color, Stroke stroke) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.stroke = stroke;
    }
    
 public Graphics2D setupGraphics(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(stroke);
        return g2d;
    }
 


 public abstract void draw(Graphics g);
}