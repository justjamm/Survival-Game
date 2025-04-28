package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;
import org.w3c.dom.ls.LSOutput;

import java.awt.event.MouseEvent;
import java.util.Random;

public class DemonEye extends Enemy {

    public Picture[] sprites;
    public volatile boolean hitPlayer;
    public int heading;
    public int abs_heading = heading;
    public volatile boolean trackingPlayer;
    public int damage;
    final Random RANDOM = new Random();
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

        speedX = 1;
        speedY = 0;
        heading = 0;
        setDrawingPriority(4);
        setPicture(sprites[0]);



        // PLAYER TRACKING - not ROTATION BASED
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {


//                if (tag.equals("Demon Eye 1")) {
//                    System.out.println(tag + " heading: " + heading);
//                }


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

                // THIS IS BUGGED - FIX LTR SKTR
                if (hitPlayer && ((pX + detRad < X) || (X < pX - detRad))) {
                    if  ((p.direction == 1 && (abs_heading < 90 && abs_heading >= 0 || abs_heading >= 270 && abs_heading < 360)) ||
                        ((p.direction == -1 && (abs_heading >= 90 && abs_heading < 270)))) {
                        hitPlayer = false;
                    }
                }


                if (trackingPlayer) {
                    if (X < pX) {
                        if (abs_heading != 180) {
                            //rotate (180 - abs_heading);
                            rotate(180);
                        }
                        setVel(speedX, speedY);
                    }
                    else if (pX < X) {
                        if (abs_heading != 0 || abs_heading != 360) {
                            //rotate (0 - abs_heading);
                            rotate(0);
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
            public void run() {
//                if (heading >= 360) {
//                    abs_heading = heading % 360;
//                    setPicture(sprites[abs_heading / 30]);
//                }
//                else {
//                    abs_heading = heading;
//                    setPicture(sprites[abs_heading / 30]);
//                }

                setDrawingPriority(4);
                if (getVelX() > 0) {
                    direction = 1;
                    setPicture(sprites[0]);
                }
                if (getVelX() < 0) {
                    direction = -1;
                    setPicture(sprites[6]);
                }
            }
        });
    }

    @Override
    public int giveDamage() {
        return (RANDOM.nextInt(damage - 2, damage + 2));
    }

    public void rotate(int n) {
        //heading += n;
        heading = n;
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
    public void mousePressed(MouseEvent e) {
        System.out.println("Eye pressed");
    }
}
