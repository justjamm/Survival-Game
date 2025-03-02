package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Player extends Sprite {
    public Picture sprite;
    public boolean isLeft;
    public boolean touchingFloor;
    public final Picture[][] sprites = {
            {new Picture("0.png"), new Picture("0f.png")},
            {new Picture("6.png"), new Picture("6f.png")},
            {new Picture("7.png"), new Picture("7f.png")},
            {new Picture("8.png"), new Picture("8f.png")},
            {new Picture("9.png"), new Picture("9f.png")},
            {new Picture("10.png"), new Picture("10f.png")},
            {new Picture("11.png"), new Picture("11f.png")},
            {new Picture("12.png"), new Picture("12f.png")},
            {new Picture("13.png"), new Picture("13f.png")},
            {new Picture("14.png"), new Picture("14f.png")},
            {new Picture("15.png"), new Picture("15f.png")},
            {new Picture("16.png"), new Picture("16f.png")},
            {new Picture("17.png"), new Picture("17f.png")},
            {new Picture("18.png"), new Picture("18f.png")},
            {new Picture("1.png"), new Picture("1f.png")},
            {new Picture("2.png"), new Picture("2f.png")},
            {new Picture("3.png"), new Picture("3f.png")},
            {new Picture("4.png"), new Picture("4f.png")},
            {new Picture("5.png"), new Picture("5f.png")}};
    public final Picture[][] actionSprites = {
            {new Picture("1.png"), new Picture("1f.png")},
            {new Picture("2.png"), new Picture("2f.png")},
            {new Picture("3.png"), new Picture("3f.png")},
            {new Picture("4.png"), new Picture("4f.png")},};
    public final Picture[] jumpingSprites = {new Picture("5.png"), new Picture("5f.png")};


    public Player(Scene scene, Dimension d) {
        super(scene);
        this.freezeOrientation = true;
        sprite = sprites[0][0];
        isLeft = true;
        touchingFloor = false;
        setPicture(sprite);
        setVel(0, 0);
        setDrawingPriority(1);
        setX(d.width / 2 );
        setY(d.height / 2);


    }
}



