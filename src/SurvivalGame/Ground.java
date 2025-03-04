package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Ground extends Sprite {
    final int SCALE = 50;
    final int WIDTH = 35 * SCALE;
    final int HEIGHT = 9 * SCALE;

    public Ground(Scene s, Dimension d) {
        super(s);
        BufferedImage bi = BasicFrame.createImage(WIDTH, HEIGHT);
        Graphics g = bi.getGraphics();
        g.setColor(new Color(23, 63, 50));
        System.out.println(d.width);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        setY(HEIGHT);
        setX(0);
        System.out.println(getY());
        setDrawingPriority(2);
        Picture pi = new Picture(bi);
        setPicture(pi);
        freezeOrientation = true;
        System.out.println("Ground height: " + getY());
    }

}
