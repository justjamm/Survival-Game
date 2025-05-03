package survivalgame;

import basicgraphics.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    public int giveDamage() {
        return damage;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("Enemy clicked!");
    }
}
