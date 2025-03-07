package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;

public class Zombie extends Sprite {

    // ANIMATION SPRITES
    private Picture[][] sprites = {
        {new Picture("zombie0.png"), new Picture("zombie0f.png")},
        {new Picture("zombie1.png"), new Picture("zombie1f.png")},
        {new Picture("zombie2.png"), new Picture("zombie2f.png")}};

    // MOVEMENT VARIABLES
    public volatile int direction = -1;
    private final int speedX = 2;
    private final int speedY = 7;
    public boolean touchingFloor;
    public volatile boolean isJumping;
    public volatile boolean isRunning = false;

    // INTERACTION VARIABLES
    public SpriteSpriteCollisionListener gcl;
    public SpriteSpriteCollisionListener pcl;

    public Zombie(Scene scene, Dimension d) {
        super(scene);
        freezeOrientation = true;
        touchingFloor = false;
        isJumping = false;
        isRunning = false;

        setDrawingPriority(4);
        setPicture(sprites[0][0]);
        setY(-getHeight());

        // GROUND COLLISION
        gcl = new SpriteSpriteCollisionListener<Zombie, Ground>() {
            @Override
            public void collision(Zombie z, Ground g) {
                touchingFloor = true;
                isJumping = false;
                setVel(getVelX(), 0);

                if (!isRunning) {
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
            }
        };

        pcl = new SpriteSpriteCollisionListener<Player, Zombie>() {
            @Override
            public void collision(Player p, Zombie z) {
                System.out.println("Player Zombie Collision!");
            }
        };

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

    }
}
