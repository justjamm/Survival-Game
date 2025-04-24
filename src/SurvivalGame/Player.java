package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Player extends Entity {
    public final Picture spriteL = new Picture("0.png");
    public final Picture spriteR = new Picture("0f.png");
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
    public final Picture[] jumpingSprites = {
            new Picture("5.png"), new Picture("5f.png")};

    public volatile boolean isSwinging;
    public double InitY;
    private final double JUMP_DIST = spriteL.getWidth() * 3.5;
    public String name;
    public MouseAdapter ma;
    public KeyListener kl;

    private final Random RAND = new Random();
    public int damage;


    public Player(Scene scene) {
        super(scene);

        tag = "Player";

        freezeOrientation = true;
        touchingFloor = false;
        isJumping = false;
        isSwinging = false;
        isRunning = false;

        direction = -1;
        speedX = 2;
        speedY = 7;

        maxHealth = 300;
        currentHealth = maxHealth;

        name = "Default";

        setDrawingPriority(5);
        setPicture(spriteR);
        setY(-getHeight());

        // GRAVITY
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {

                //System.out.println(getY());

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
                    if (isRunning && !isJumping && touchingFloor) {
                        switch (direction) {
                            case 1 -> {
                                if (frame > 12) {
                                    frame = 0;
                                }
                                if (!isSwinging) {
                                    setPicture(runningSprites[frame++][1]);
                                }
                                setVel(speedX, getVelY());

                            }
                            case -1 -> {
                                if (frame > 12) {
                                    frame = 0;
                                }
                                if (!isSwinging) {
                                    setPicture(runningSprites[frame++][0]);
                                }
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

            @Override
            public void mouseClicked(MouseEvent me) {
                isSwinging = true;

                ClockWorker.addTask(new Task() {
                    int frame = 0;
                    final int INCR = 5;

                    @Override
                    public void run() {
                        if (iteration() > 4 * INCR) {
                            isSwinging = false;
                            this.setFinished();
                        }
                        else {
                            if (iteration() % INCR == 0) {
                                switch (direction) {
                                    case 1 -> {
                                        if (frame > 3) {
                                            frame = 0;
                                        }
                                        else {
                                            if (touchingFloor) setPicture(actionSprites[frame++][1]);
                                        }

                                    }
                                    case -1 -> {
                                        if (frame > 3) {
                                            frame = 0;
                                        }
                                        else {
                                            if (touchingFloor) setPicture(actionSprites[frame++][0]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
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
    }

    @Override
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
                BasicDialog.getOK("You fell out the world! Press OK to restart.");
                BasicFrame.getFrame().dispose();
                System.exit(0);
            }
        }
    }

    @Override
    public int giveDamage() {

        damage = RAND.nextInt(5, 10);
        if (damage == 10) {
            System.out.println("Critical Hit!");
            System.out.println("Hit for " + damage);
        }
        else {
            System.out.println("Hit for " + damage);

        }
        return damage;
    }

    @Override
    public void takeDamage(int damage) {
        iter = 0;

        ClockWorker.addTask(new Task() {
            @Override
            public void run() {
                if (iter == damageCooldown) {
                    currentHealth -= damage;
                    System.out.println(tag + " health: " + currentHealth + " / " + maxHealth);
                    if (currentHealth <= 0) {
                        BasicDialog.getOK("You died! Press OK to restart.");
                        BasicFrame.getFrame().dispose();
                        System.exit(0);
                    }
                    this.setFinished();
                }
                else iter++;
            }
        });
    }

    public double getXPos() {
        return getX();

    }
}



