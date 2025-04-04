package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;

public class Zombie extends Enemy {

    // ANIMATION SPRITES
    public Picture[][] sprites = {
        {new Picture("zombie0.png"), new Picture("zombie0f.png")},
        {new Picture("zombie1.png"), new Picture("zombie1f.png")},
        {new Picture("zombie2.png"), new Picture("zombie2f.png")}};

    // MOVEMENT VARIABLES
    public volatile int direction = -1;
    //private final int speedX = 2;
    //private final int speedY = 7;
    public boolean touchingFloor;
    public volatile boolean isJumping;
    public volatile boolean isRunning = false;

    public Zombie(Scene scene, Dimension d) {
        super(scene);
        this.freezeOrientation = true;
        this.touchingFloor = false;
        this.isJumping = false;
        this.isRunning = false;

        setDrawingPriority(4);
        setPicture(sprites[0][0]);
        setY(-getHeight());

        setVel(0, getVelY());


        // GRAVITY
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {
                if (touchingFloor) {
                    setVel(getVelX(), getVelY());
                }
                else if (!touchingFloor && getVelY() <= 50) {  // CHECK FOR TERMINAL VELOCITY
                    double vy = getVelY() + 0.1;
                    setVel(getVelX(), vy );
                }
            }
        });

        ClockWorker.addTask(new Task() {
            @Override
            public void run() {
                if (getVelX() > 0) direction = 1;
                else if (getVelX() < 0) direction = -1;
                switch (direction) {
                    case 1 -> {
                        setDrawingPriority(4);
                        setPicture(sprites[0][1]);
                    }
                    case -1 -> {
                        setDrawingPriority(4);
                        setPicture(sprites[0][0]);
                    }
                }
            }
        });
    }
}
