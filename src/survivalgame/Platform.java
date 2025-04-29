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
        BufferedImage bi = BasicFrame.createImage(100, 200);
        Graphics g = bi.getGraphics();
        g.setColor(Color.green);
        g.fillRect(0, 0, 100, 30);
        Picture p = new Picture(bi);
        setDrawingPriority(3);
        setPicture(p);
    }
}
