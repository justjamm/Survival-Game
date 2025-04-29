package survivalgame;

import basicgraphics.*;

import java.awt.event.MouseEvent;

public class Entity extends Sprite {

    public String tag;

    public volatile boolean touchingFloor;
    public volatile boolean isJumping;
    public volatile boolean isRunning;

    public volatile int direction; // Left: -1, Right: 1
    public double speedX;
    public double speedY;

    int maxHealth;
    int currentHealth;

    public final int damageCooldown = 40;

    public Entity(Scene scene) {
        super(scene);
    }

    public int giveDamage() {
        return 0;
    }

    public void takeDamage(int damage) {

        ClockWorker.addTask(new Task(damageCooldown) {
            @Override
            public void run() {
                if (iteration() == damageCooldown) {
                    currentHealth -= damage;
                    System.out.println(tag + " health: " + currentHealth + " / " + maxHealth);
                    if (currentHealth <= 0) {
                        destroy();
                    }
                    this.setFinished();
                }
            }
        });

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("Entity clicked");
    }
}
