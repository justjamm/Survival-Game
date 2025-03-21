package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Ground extends Sprite {
    public Ground(Scene s, Dimension d) {
        super(s);
        final int width = d.width * 10;
        final int height = d.height * 3;


        BufferedImage bi = BasicFrame.createImage(width, height);
        Graphics g = bi.getGraphics();
        g.setColor(new Color(23, 63, 50));
        System.out.println(d.width);
        g.fillRect(0, 0, width, height);
        setY(d.height * 0.95);
        setX(-1 * (width / 2));
        System.out.println(getY());
        setDrawingPriority(2);
        Picture pi = new Picture(bi);
        setPicture(pi);
        freezeOrientation = true;
    }

}
