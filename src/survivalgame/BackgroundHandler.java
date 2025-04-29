package survivalgame;

import basicgraphics.Scene;
import basicgraphics.images.Picture;

import java.awt.*;
import java.util.Random;

public class BackgroundHandler {
    private final Picture[] forest = {new Picture("Forest_background_0.png"), new Picture("Forest_background_1.png"), new Picture("Forest_background_2.png"), new Picture("Forest_background_3.png"), new Picture("Forest_background_4.png"), new Picture("Forest_background_5.png")};
    private final Background[] bgs;
    private int index = new Random().nextInt(6);

    public BackgroundHandler(Scene scene, Dimension d, int num) {
        bgs = new Background[num];


        // GROUND IMAGES
        for (int i = 0; i < bgs.length; i++) {
            bgs[i] = new Background(scene, d);
            bgs[i].setDrawingPriority(1);
            bgs[i].setPicture(forest[index]);
        }



        int x = 0;
        for (int i = 0; i < bgs.length; i++) {
            bgs[i].setX(x);

            if (x >= 0) {
                x += bgs[i].getWidth();
            }
            //x *= -1;
        }



    }

    public void setVel(double velx, double vely) {
        for (Background b : bgs) {
            b.setVel(velx, vely);
        }

    }

    public double getVelX() {
        return bgs[0].getVelX();
    }

    public double getVelY() {
        return bgs[0].getVelY();
    }

    public void setY(double y) {
        for (Background b : bgs) {
            b.setY(y);
        }

    }

    public void setX(double x) {
        for (Background b : bgs) {
            b.setX(x);
        }
    }

    public double getX() {
        return forest[0].getX();
    }

    public double getY() {
        return forest[0].getY();
    }

}
