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
    }

    @Override
    public int giveDamage() {
        return damage;
    }
}
