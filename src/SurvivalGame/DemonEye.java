package SurvivalGame;

import basicgraphics.ClockWorker;
import basicgraphics.Scene;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.Task;
import basicgraphics.images.Picture;

import java.util.Random;

public class DemonEye extends Enemy {

    public volatile boolean hitPlayer;

    public DemonEye(Scene scene, Player p) {
        super(scene);
        sprites = new Picture[][]{
                {new Picture("eye0.png"), new Picture("eye0f.png")}
        };
        damage = 18;
        maxHealth = 60;
        currentHealth = maxHealth;
        detRad = 500;
        this.speedX = 4;
        this.speedY = speedX;

        setDrawingPriority(4);
        if (this.direction == 1) setPicture(sprites[0][1]);
        else setPicture(sprites[0][0]);
        setY(400);
        setVel(speedX * direction, 0);


        // PLAYER DETECTION - different from zombie
//        ClockWorker.addTask(new Task() {
//            @Override
//            public void run() {
//                double pX = p.getX();
//                double zX = getX();
//                setVel(speedX, getVelY());
//
//                if ((pX < zX && pX >= zX - detRad) || (zX < pX && zX + detRad >= pX)) {
//                    trackingPlayer = true;
//                }
//                else {
//                    trackingPlayer = false;
//                }
//
//                if (trackingPlayer) {
//                    if (zX < pX) {
//                        setVel(Math.abs(getVelX()), getVelY());
//                    }
//                    else if (pX < zX) {
//                        setVel(-Math.abs(getVelX()), getVelY());
//                    }
//                }
//                else {
//                    setVel(0.3 * direction * speedX, getVelY());
//                }
//
//            }
//        });
    }

}
