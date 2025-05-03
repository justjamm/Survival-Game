package survivalgame;

import basicgraphics.*;
import basicgraphics.images.Picture;

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

        damage = 20;
        maxHealth = 60;
        currentHealth = maxHealth;
        detRad = 800;

        speedX = RANDOM.nextDouble(0.5, 1.0);
        speedY = 0;
        heading = 0;
        setDrawingPriority(4);
        setPicture(sprites[0]);

        setVel(speedX, speedY);

        // DIRECTION SPRITE HANDLING
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {
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
            }
            if (ce.xhi) {
                setVel(-getVelX(), getVelY());
                heading = 180;
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
