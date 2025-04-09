package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.util.Random;

public class DemonEye extends Enemy {

    public volatile boolean hitPlayer;

    public DemonEye(Scene scene, Player p) {
        super(scene);
        sprites = new Picture[][]{
                {new Picture("eye0.png"), new Picture("eye0f.png")}
        };
        freezeOrientation = false;
        damage = 18;
        maxHealth = 60;
        currentHealth = maxHealth;
        detRad = 800;

        speedX = 1;
        speedY = 0;

        setDrawingPriority(4);
        if (this.direction == 1) setPicture(sprites[0][1]);
        else setPicture(sprites[0][0]);
        setY(400);
        setVel(speedX * direction, speedY * direction);

        // PLAYER TRACKING - ROTATION BASED
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {
                double pX = p.getX();
                double pY = p.getY();
                double X = getX();
                double Y = getY();

                // only x coord checking, implement y later
                if ((pX < X && pX >= X - detRad) || (X < pX && X + detRad >= pX)) {
                    trackingPlayer = true;
                }
                else {
                    trackingPlayer = false;
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
