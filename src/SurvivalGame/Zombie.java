package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;
import java.util.Random;

public class Zombie extends Enemy {

    public Zombie(Scene scene, Player p) {
        super(scene);

        sprites = new Picture[][]{
                {new Picture("zombie0.png"), new Picture("zombie0f.png")},
                {new Picture("zombie1.png"), new Picture("zombie1f.png")},
                {new Picture("zombie2.png"), new Picture("zombie2f.png")}};

        if (Math.random() >= 0.5) this.direction = 1;
        else this.direction = -1;

        Random rand = new Random();
        speedX = rand.nextDouble(0.3, 0.7);
        speedY = 7;
        detRad = 500; // detection radius of zombies

        damage = 14;
        maxHealth = 45;
        currentHealth = maxHealth;

        setDrawingPriority(4);
        if (this.direction == 1) setPicture(sprites[0][1]);
        else setPicture(sprites[0][0]);
        setY(-getHeight());

        setVel(speedX * direction, getVelY());

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

        // DIRECTION SPRITE HANDLING
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


        // PLAYER DETECTION
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {
                double pX = p.getX();
                double zX = getX();

                if ((pX < zX && pX >= zX - detRad) || (zX < pX && zX + detRad >= pX)) {
                    trackingPlayer = true;
                }
                else {
                    trackingPlayer = false;

                }

                if (trackingPlayer) {
                    if (zX < pX) {
                        setVel(Math.abs(speedX), getVelY());
                    }
                    else if (pX < zX) {
                        setVel(-Math.abs(speedX), getVelY());
                    }
                }
                else {
                    if (zX < pX && direction == 1) {
                        setVel(-1 * 0.3 * getVelX(), getVelY());

                    }
                    else if (pX < zX && direction == -1) {
                        setVel(-1 * 0.3 * getVelX(), getVelY());
                    }
                }
            }
        });
    }

    @Override
    public void processEvent(SpriteCollisionEvent ce) {
        if (ce.eventType == CollisionEventType.WALL) {
            if (ce.xlo || ce.xhi) {
                setVel(-getVelX(), getVelY());
            }
            if (ce.ylo || ce.yhi) {
                setVel(getVelX(), -getVelY());
            }
        }
    }
}
