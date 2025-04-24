package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Enemy extends Entity {
    public volatile boolean trackingPlayer;
    public int damage;
    public int detRad;

    public MouseListener ml;

    public Enemy(Scene scene) {
        super(scene);

        this.freezeOrientation = true;
        this.touchingFloor = false;
        this.isJumping = false;
        this.isRunning = false;
        this.trackingPlayer = false;
        this.direction = -1;
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
                        destroy();
                        setVel(0, 0);
                        direction = 1;

                    }
                    this.setFinished();
                }
                else iter++;
            }
        });

    }
}
