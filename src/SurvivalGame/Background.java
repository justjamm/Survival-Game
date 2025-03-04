package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Background extends Sprite {

    public Background(Scene s, Dimension d) {
        super(s);
        freezeOrientation = true;
        setPicture(new Picture("Forest_background_0.png"));
        //setY((-d.height) + (d.height * 2/3));
        //setY(-getHeight() + (d.height * 1.15));
        //setY(-getHeight());
        setY(0 - 3300);
        System.out.println("Y: " + getY());
    }
}