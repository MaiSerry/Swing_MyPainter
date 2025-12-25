/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mypainter;

/**
 *
 * @author HELLo
 */
import java.awt.image.BufferedImage;

public class ImageData {

    public BufferedImage image;
    public int x, y;

    public ImageData(BufferedImage image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }
}
