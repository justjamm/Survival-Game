package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.util.Random;

public class Enemy extends Entity {
    public volatile boolean trackingPlayer;
    public Picture[][] sprites;
    public int damage;
    public int detRad;

    public Enemy(Scene scene) {
        super(scene);

        this.freezeOrientation = true;
        this.touchingFloor = false;
        this.isJumping = false;
        this.isRunning = false;
        this.trackingPlayer = false;
        this.direction = -1;

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

    @Override
    public int giveDamage() {
        return damage;
    }
}
