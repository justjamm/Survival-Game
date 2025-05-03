package survivalgame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ground extends Sprite {
    final int SCALE = 50;
    final int WIDTH = 1200 * 4;
    final int HEIGHT = 500;

    public Ground(Scene s, int width, int height) {
        super(s);
        BufferedImage bi = BasicFrame.createImage(WIDTH, HEIGHT);
        Graphics g = bi.getGraphics();
        g.setColor(new Color(23, 63, 50));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        setY(HEIGHT);
        setX(0);
        setDrawingPriority(2);
        Picture pi = new Picture(bi);
        setPicture(pi);
        freezeOrientation = true;
    }

}
