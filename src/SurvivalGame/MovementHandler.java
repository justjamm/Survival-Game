package SurvivalGame;

import basicgraphics.Card;
import basicgraphics.ClockWorker;
import basicgraphics.KeyWrapper;
import basicgraphics.Task;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MovementHandler {
    private Player p;

    public MovementHandler(Card card, Player p) {
        this.p = p;


        card.addKeyListener(new KeyWrapper(new KeyAdapter() {

            // MOVEMENT
            @Override
            public void keyPressed(KeyEvent ke) {
                // UP
                if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) {
                    if (p.touchingFloor) {
                        p.isJumping = true;
                        p.touchingFloor = false;
                        p.InitY = p.getY();
                    }
                }

                // RIGHT
                else if (ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    p.direction = 1;
                    p.isRunning = true;
                }

                // LEFT
                else if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    p.direction = -1;
                    p.isRunning = true;
                }
            }

            // STOPPING
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    p.setVel(0, p.getVelY());
                    p.setDrawingPriority(5);
                    p.setPicture(p.spriteR);
                    p.isRunning = false;
                    p.direction = 1;
                } else if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    p.setVel(0, p.getVelY());
                    p.setDrawingPriority(5);
                    p.setPicture(p.spriteL);
                    p.isRunning = false;
                    p.direction = -1;
                } else if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) {
                    p.setVel(p.getVelX(), 0);
                    p.isJumping = false;
                }
            }
        }));


    }
}

