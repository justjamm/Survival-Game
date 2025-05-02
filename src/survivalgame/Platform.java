package survivalgame;

import basicgraphics.BasicFrame;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Platform extends Sprite {

    public Platform(Scene s) {
        super(s);

        final int width = 100;
        final int height = 15;
        BufferedImage bi = BasicFrame.createImage(width, height);
        Graphics g = bi.getGraphics();
        g.setColor(Color.green);
        g.fillRect(0, 0, width, height);
        Picture p = new Picture(bi);
        setDrawingPriority(3);
        setPicture(p);
    }
}
