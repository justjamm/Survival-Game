package SurvivalGame;

import basicgraphics.Card;
import basicgraphics.ClockWorker;
import basicgraphics.KeyWrapper;
import basicgraphics.Task;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MovementHandler {
    private Player p;
    private Ground g;
    private BackgroundHandler b;
    public MouseAdapter ma;
    private int speedX = 2, speedY = -7;
    private boolean superSpeed = false;
    private int INCR = 1;
    private int i = INCR;


    public MovementHandler(Card card, Player p, Ground g, BackgroundHandler b) {
        this.p = p;
        this.g = g;
        this.b = b;

        // GRAVITY
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {
                if (p.touchingFloor) {
                    p.setVel(p.getVelX(), p.getVelY());
                }
                else if (!p.touchingFloor) {
                    double vy = p.getVelY() + 0.02;
                    p.setVel(p.getVelX(), vy );
                    //System.out.printf("vy: %.4f\n", vy);
                }
                System.out.printf("Y-Velocity: %.4f\n", p.getVelY());
            }
        });

        card.addKeyListener(new KeyWrapper(new KeyAdapter() {

            // MOVEMENT
            @Override
            public void keyPressed(KeyEvent ke) {
                // UP
                if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) {
                    moveUp();
                }

                // RIGHT
                else if (ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    moveRight();
                }

                // LEFT
                else if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    moveLeft();
                }

                else if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
                    if (superSpeed) {
                        System.out.println("SuperSpeed Deactivated");
                        superSpeed = false;
                        speedX /= 3;
                        speedY /= 3;
                        INCR = 6 / speedX + 1;
                    }
                    else {
                        System.out.println("SuperSpeed Activated");
                        superSpeed = true;
                        speedX *= 3;
                        speedY *= 3;
                        INCR = 6 / speedX + 1;
                    }
                }
            }

            // STOPPING
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    p.setVel(0, p.getVelY());
                    p.setPicture(p.sprites[0][1]);

                } else if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    p.setVel(0, p.getVelY());
                    p.setPicture(p.sprites[0][0]);

                } else if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) {
                    p.setVel(p.getVelX(), 0);
                    p.touchingFloor = false;
                }
                i = INCR;
            }

        }));

        ma = new MouseAdapter() {
            private final int INCR = 2;
            private int i = INCR;

            @Override
            public void mousePressed(MouseEvent me) {
                System.out.println("mouse clicked");
            }
        };
    }
    public void moveUp() {
        if (p.touchingFloor) {
            p.setVel(p.getVelX(), speedY);

            if (p.isLeft) {
                p.setPicture(p.jumpingSprites[0]);
            } else {
                p.setPicture(p.jumpingSprites[1]);
            }
        }
    }

    public void moveRight() {
        p.setVel(speedX, p.getVelY());

        p.isLeft = false;
        if (i % INCR == 0) {
            int step = i / INCR;
            if (step >= 0 && step <= 12) {
                p.setPicture(p.sprites[step][1]);
                i++;
            }
            else if (step > 12) {
                i = 1;
                p.setPicture(p.sprites[i][1]);
            }
        }
        else i++;
    }

    public void moveLeft() {
        p.setVel(-speedX, p.getVelY());

        p.isLeft = true;
        if (i % INCR == 0) {
            int step = i / INCR;
            if (step >= 0 && step <= 12) {
                p.setPicture(p.sprites[step][0]);
                i++;
            } else if (step > 12) {
                i = 1;
                p.setPicture(p.sprites[i][0]);
            }
        }
        else i++;
    }

}

