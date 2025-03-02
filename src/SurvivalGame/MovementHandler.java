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
    private int speedX = 2, speedY = 5;
    private final int PARALLAX_OFFSET = 1;
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
                if (!p.touchingFloor) {
                    double vy = g.getVelY() - 0.1;
                    g.setVel(g.getVelX(), vy);
                    b.setVel(b.getVelX(), vy);
                } else {
                    g.setVel(g.getVelX(), 0);
                    b.setVel(b.getVelX(), 0);
                }
            }
        });


        card.addKeyListener(new KeyWrapper(new KeyAdapter() {

            // MOVEMENT
            @Override
            public void keyPressed(KeyEvent ke) {
                // UP
                if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) {
                    if (p.touchingFloor) {
                        g.setVel(g.getVelX(), speedY);
                        b.setVel(b.getVelX(), speedY);

                        if (p.isLeft) {
                            p.setPicture(p.jumpingSprites[0]);
                        } else {
                            p.setPicture(p.jumpingSprites[1]);
                        }
                        p.touchingFloor = false;
                    }
                }

                // RIGHT
                else if (ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    moveRight();
                }

                // LEFT
                else if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    moveLeft();
                }

                else if (ke.getKeyCode() == KeyEvent.VK_S || ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    g.setVel(g.getVelX(), 0);
                    b.setVel(b.getVelX(), 0);
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
                    g.setVel(0, g.getVelY());
                    b.setVel(0, b.getVelY());
                    p.setPicture(p.sprites[0][1]);

                } else if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    g.setVel(0, g.getVelY());
                    b.setVel(0, b.getVelY());
                    p.setPicture(p.sprites[0][0]);

                } else if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) {
                    g.setVel(g.getVelX(), 0);
                    b.setVel(b.getVelX(), 0);

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
                for (int j = 0; j < 4 * INCR; j++) {
                    if (j % INCR == 0) {
                        if (p.isLeft) p.setPicture(p.actionSprites[j / INCR][0]);
                        else p.setPicture(p.actionSprites[j / INCR][1]);
                    }

                }
                if (p.isLeft) p.setPicture(p.sprites[0][0]);
                else p.setPicture(p.sprites[0][1]);
//                while (i < 4 * INCR) { //may break
//                    if (i % INCR == 0) {
//                        int step = i / INCR - 1;
////                        System.out.println("step: " + step + " i: " + i);
//                        if (step >= 0 && step <= 3) {
//                            if (isLeft) setPicture(actionSprites[step][0]);
//                            else if (!isLeft) setPicture(actionSprites[step][1]);
//                            i++;
//                            System.out.println("Setting picture to: " + step + " i: " + i);
//                        }
//                        else if (step > 3) {
//                            i = INCR;
//                        }
//                    }
//                    else i++;
//                }
//                i = INCR;
//                setPicture(sprites[0][0]);
            }
        };
    }

    public void moveRight() {
        g.setVel( -1 * speedX, g.getVelY());
        b.setVel( -1 * speedX + PARALLAX_OFFSET, b.getVelY());

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
        g.setVel(speedX, g.getVelY());
        b.setVel(speedX - PARALLAX_OFFSET, b.getVelY());

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

