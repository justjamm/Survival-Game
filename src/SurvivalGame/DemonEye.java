package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.util.Random;

public class DemonEye extends Enemy {

    public Picture[] sprites;
    public volatile boolean hitPlayer;
    public int heading;
    public volatile boolean trackingPlayer;
    public int damage;
    public int detRad;

    public DemonEye(Scene scene, Player p) {
        super(scene);
//        sprites = new Picture[][]{
//                {new Picture("eye0.png"), new Picture("eye0f.png")
//        }};

        sprites = new Picture[]{
                new Picture("eye0.png"),
                new Picture("eye1.png"),
                new Picture("eye2.png"),
                new Picture("eye3.png"),
                new Picture("eye4.png"),
                new Picture("eye5.png"),
                new Picture("eye6.png"),
                new Picture("eye7.png"),
                new Picture("eye8.png"),
                new Picture("eye9.png"),
                new Picture("eye10.png"),
                new Picture("eye11.png")
        };

        this.freezeOrientation = true;
        this.touchingFloor = false;
        this.isJumping = false;
        this.isRunning = false;
        this.trackingPlayer = false;
        this.direction = -1;

        damage = 18;
        maxHealth = 60;
        currentHealth = maxHealth;
        detRad = 800;

        speedX = 2;
        speedY = 0;
        heading = 0;
        setDrawingPriority(4);
        setPicture(sprites[0]);
        setY(400);



        // PLAYER TRACKING - ROTATION BASED
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {


                //System.out.println(tag + " heaidng: " + heading);


                double pX = p.getX();
                double pY = p.getY();
                double X = getX();
                double Y = getY();

                // only x coord checking, implement y later
                if (((pX < X && pX >= X - detRad) || (X < pX && X + detRad >= pX)) && !hitPlayer) {
                    trackingPlayer = true;
                }
                else {
                    trackingPlayer = false;
                }

                if (trackingPlayer) {
                    if (X < pX) {
                        if (heading != 180) {
                            rotate (180 - heading);
                        }
                        setVel(speedX, speedY);
                    }
                    else if (pX < X) {
                        if (heading != 0 || heading != 360) {
                            rotate (0 - heading);
                        }
                        setVel(-speedX, speedY);
                    }
                }

                if (X + 50 >= pX && pX >= X - 50) {
                    trackingPlayer = false;
                    hitPlayer = true;
                    //System.out.println("false");
                    if (0 <= heading && heading < 180) setVel(speedX, speedY);
                    else if (180 <= heading && heading < 360) setVel(-speedX, speedY);
                }
            }
        });

        // DIRECTION SPRITE HANDLING
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {;

                if (heading == 0) {
                    setPicture(sprites[0]);
                }
                else if (heading == 30) {
                    setPicture(sprites[1]);
                }
                else if (heading == 60) {
                    setPicture(sprites[2]);
                }
                else if (heading == 90) {
                    setPicture(sprites[3]);
                }
                else if (heading == 120) {
                    setPicture(sprites[4]);
                }
                else if (heading == 150) {
                    setPicture(sprites[5]);
                }
                else if (heading == 180) {
                    setPicture(sprites[6]);
                }
                else if (heading == 210) {
                    setPicture(sprites[7]);
                }
                else if (heading == 240) {
                    setPicture(sprites[8]);
                }
                else if (heading == 270) {
                    setPicture(sprites[9]);
                }
                else if (heading == 300) {
                    setPicture(sprites[10]);
                }
                else if (heading == 340) {
                    setPicture(sprites[11]);
                }
                else if (heading == 360) {
                    setPicture(sprites[0]);
                }
                else if (0 < heading && heading < 30) {
                    setPicture(sprites[0]);
                }
                else if (30 < heading && heading < 60) {
                    setPicture(sprites[1]);
                }
                else if (40 < heading && heading < 90) {
                    setPicture(sprites[2]);
                }
                else if (90 < heading && heading < 120) {
                    setPicture(sprites[3]);
                }
                else if (120 < heading && heading < 150) {
                    setPicture(sprites[4]);
                }
                else if (150 < heading && heading < 180) {
                    setPicture(sprites[5]);
                }
                else if (180 < heading && heading < 240) {
                    setPicture(sprites[6]);
                }
                else if (240 < heading && heading < 270) {
                    setPicture(sprites[7]);
                }
                else if (270 < heading && heading < 290) {
                    setPicture(sprites[8]);
                }
                else if (290 < heading && heading < 310) {
                    setPicture(sprites[9]);
                }
                else if (310 < heading && heading < 360) {
                    setPicture(sprites[10]);
                }
            }
        });
    }

    public void rotate(int n) {
//        ClockWorker.addTask(new Task() {
//            @Override
//            public void run() {
//                heading += 0.5;
//                if (heading == n) this.setFinished();
//            }
//        });

        heading += n;
    }

    @Override
    public void processEvent(SpriteCollisionEvent ce) {
        if (ce.eventType == CollisionEventType.WALL) {
            if (ce.xlo) {
                setVel(-getVelX(), getVelY());
                heading = 0;
                System.out.println(tag + " hit low wall");
            }
            if (ce.xhi) {
                setVel(-getVelX(), getVelY());
                heading = 180;
                System.out.println(tag + " hit high wall");
            }
            if (ce.ylo) {
                setVel(getVelX(), -getVelY());
            }
            if (ce.yhi) {
                setVel(getVelX(), -getVelY());
            }
        }
    }

    @Override
    public int giveDamage() {
        return damage;
    }

}
