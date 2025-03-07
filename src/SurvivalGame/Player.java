package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.event.*;

public class Player extends Sprite {
    // STILL SPRITES
    public final Picture spriteL = new Picture("0.png");
    public final Picture spriteR = new Picture("0f.png");

    // MOVEMENT BOOLEANS
    public boolean touchingFloor;
    public volatile boolean isJumping;
    public volatile boolean isRunning = false;
    public volatile boolean isSwinging = false;

    // MOVEMENT VARIABLES
    public volatile int direction = -1;
    private final int speedX = 2;
    private final int speedY = 7;
    public double InitY;
    private final double JUMP_DIST = spriteL.getWidth() * 3.5;

    // INTERACTION VARIABLES
    public MouseAdapter ma;
    public SpriteSpriteCollisionListener<Player, Ground> GroundCollision;
    public SpriteSpriteCollisionListener<Player, Enemy> EnemyCollision;
    public KeyListener kl;

    // SPRITE ARRAYS
    public final Picture[][] runningSprites = {
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
            {new Picture("18.png"), new Picture("18f.png")}};
    public final Picture[][] actionSprites = {
            {new Picture("1.png"), new Picture("1f.png")},
            {new Picture("2.png"), new Picture("2f.png")},
            {new Picture("3.png"), new Picture("3f.png")},
            {new Picture("4.png"), new Picture("4f.png")},};
    public final Picture[] jumpingSprites = {new Picture("5.png"), new Picture("5f.png")};


    public Player(Scene scene, Dimension d) {
        super(scene);
        freezeOrientation = true;
        touchingFloor = false;
        isJumping = false;
        isSwinging = false;
        isRunning = false;
        setDrawingPriority(5);
        setPicture(spriteR);
        setY(-getHeight());

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

        // MOVEMENT
        ClockWorker.addTask(new Task() {
            int frame = 0;
            @Override
            public void run() {
                if (iteration() % 10 == 0){
                    if (isRunning && !isJumping && touchingFloor & !isSwinging) {
                        switch (direction) {
                            case 1 -> {
                                if (frame > 12) {
                                    frame = 0;
                                }
                                setPicture(runningSprites[frame++][1]);
                                setVel(speedX, getVelY());

                            }
                            case -1 -> {
                                if (frame > 12) {
                                    frame = 0;
                                }
                                setPicture(runningSprites[frame++][0]);
                                setVel(-speedX, getVelY());
                            }
                        }
                    }
                    else if (isJumping || !touchingFloor) {

                        switch (direction) {
                            case 1 -> {
                                setPicture(jumpingSprites[1]);
                            }
                            case -1 -> {
                                setPicture(jumpingSprites[0]);
                            }
                        }
                        if (isRunning) {
                            setVel(direction * speedX, getVelY());
                        }
                    }
                    else {
                        switch (direction) {
                            case 1 -> {
                                setPicture(spriteR);
                            }
                            case -1 -> {
                                setPicture(spriteL);
                            }
                        }
                        frame = 0;
                    }
                }

            }
        });

        // JUMPING
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {
                if (isJumping) {
                    final double GOAL_Y = InitY - JUMP_DIST;

                    if (getY() > GOAL_Y) {
                        setVel(getVelX(), -speedY);
                    }
                    else {
                        setVel(getVelX(), 0);
                        isJumping = false;
                    }

                }
            }
        });


        // INTERACTION VIA MOUSE
        ma = new MouseAdapter() {
            int frame = 0;

            @Override
            public void mousePressed(MouseEvent me) {
                isSwinging = true;

                ClockWorker.addTask(new Task() {
                    public void run() {
                        if (frame < 3) {
                            if (iteration() % 40 == 0) {
                                switch (direction) {
                                    case 1 -> {
                                        setPicture(actionSprites[frame++][1]);
                                    }
                                    case -1 -> {
                                        setPicture(actionSprites[frame++][0]);
                                    }
                                }
                            }
                        }
                        else {
                            isSwinging = false;
                        }
                    }
                });
                frame = 0;
            }
        };

        // KEYBOARD INPUT
        kl = new KeyWrapper(new KeyAdapter() {

            // KEY PRESSED
            @Override
            public void keyPressed(KeyEvent ke) {
                // UP
                if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) {
                    if (touchingFloor) {
                        isJumping = true;
                        touchingFloor = false;
                        InitY = getY();
                    }
                }

                // RIGHT
                else if (ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    direction = 1;
                    isRunning = true;
                }

                // LEFT
                else if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    direction = -1;
                    isRunning = true;
                }
            }

            // KEY RELEASED
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    setVel(0, getVelY());
                    setDrawingPriority(5);
                    setPicture(spriteR);
                    isRunning = false;
                    direction = 1;
                } else if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    setVel(0, getVelY());
                    setDrawingPriority(5);
                    setPicture(spriteL);
                    isRunning = false;
                    direction = -1;
                } else if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) {
                    setVel(getVelX(), 0);
                    isJumping = false;
                }
            }
        });

        // GROUND COLLISION
        GroundCollision = new SpriteSpriteCollisionListener<Player, Ground>() {
            @Override
            public void collision(Player p, Ground g) {
                touchingFloor = true;
                isJumping = false;
                setVel(getVelX(), 0);

                if (!isRunning) {
                    switch (direction) {
                        case 1 -> {
                            setDrawingPriority(5);
                            setPicture(spriteR);
                        }
                        case -1 -> {
                            setDrawingPriority(5);
                            setPicture(spriteL);
                        }
                    }
                }
            }
        };

        EnemyCollision = new SpriteSpriteCollisionListener<Player, Enemy>() {
            @Override
            public void collision(Player p, Enemy z) {
                setVel(-2 * getVelX(), -0.5 * getVelY());
            }
        };
    }


    // BORDER CHECKING
    public void processEvent(SpriteCollisionEvent ce) {
        if (ce.eventType == CollisionEventType.WALL) {
            if (ce.xlo) {
                setVel(Math.abs(getVelX()), getVelY());
            }
            if (ce.xhi) {
                setVel(-Math.abs(getVelX()), getVelY());
            }
            if (ce.ylo) {
                setVel(getVelX(), Math.abs(getVelY()));
            }
            if (ce.yhi) {
                setVel(getVelX(), -Math.abs(getVelY()));
            }
        }
    }
}



