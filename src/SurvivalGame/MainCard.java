package SurvivalGame;

import basicgraphics.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class  MainCard {

    public static Card mc;
    private final SpriteComponent sc;
    private final Random RANDOM = new Random();
    private Dimension BOARD;
    private final int NUM_BACKGROUNDS = 3;

    public MainCard(BasicFrame f) {
        mc = f.getCard();
        BOARD = new Dimension (500,500);//f.FRAME_SIZE;
        sc = new SpriteComponent();
        sc.setPreferredSize(BOARD);
        sc.getScene().setBackgroundSize(new Dimension(1200 * NUM_BACKGROUNDS,3928));
        BasicLayout layout = new BasicLayout();
        mc.setLayout(layout);
        mc.add("x=0,y=0,w=4,h=4", sc);

        sc.getScene().periodic_x = false;
        sc.getScene().periodic_y = false;


        // BACKGROUND & GROUND
        BackgroundHandler bgh = new BackgroundHandler(sc.getScene(), sc.getScene().getBackgroundSize(), NUM_BACKGROUNDS);
        Ground g = new Ground(sc.getScene(), sc.getScene().getBackgroundSize());

        // PLAYER
        Player p = new Player(sc.getScene(), sc.getScene().getBackgroundSize());
        p.setX(g.getWidth() / 2);
        sc.getScene().setFocus(p);
        sc.addMouseListener(p.ma);
        sc.addSpriteSpriteCollisionListener(Player.class, Ground.class, new SpriteSpriteCollisionListener<Player, Ground>() {
            @Override
            public void collision(Player p, Ground g) {
                p.touchingFloor = true;
                p.isJumping = false;
                p.setVel(p.getVelX(), 0);

                if (!p.isRunning) {
                    switch (p.direction) {
                        case 1 -> {
                            p.setDrawingPriority(5);
                            p.setPicture(p.spriteR);
                        }
                        case -1 -> {
                            p.setDrawingPriority(5);
                            p.setPicture(p.spriteL);
                        }
                    }
                }
            }
        });
        sc.addSpriteSpriteCollisionListener(Player.class, Enemy.class, new SpriteSpriteCollisionListener<Player, Enemy>() {
            @Override
            public void collision(Player p, Enemy z) {
                p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
            }
        });
        mc.addKeyListener(p.kl);

        // ENEMIES
        int initSpawn = 600;
        Zombie[] zombs = new Zombie[5];
        for (Zombie z : zombs) {
            z = new Zombie(sc.getScene(), sc.getScene().getBackgroundSize());
            z.setX(initSpawn);
            initSpawn += 400;
            //zombs[i].setVel(.1 * Math.pow(-1, i), zombs[i].getVelY());
            z.setVel(.2, z.getVelY());
            final Zombie z1 = z;
            ClockWorker.addTask(new Task() {
                @Override
                public void run() {
                    if (z1.getX() > p.getX()) {
                        z1.setVel(-Math.abs(z1.getVelX()), z1.getVelY());
                    }
                    else if (z1.getX() < p.getX()) {
                        z1.setVel(Math.abs(z1.getVelX()), z1.getVelY());
                    }

                }
            });

        }

        sc.addSpriteSpriteCollisionListener(Zombie.class, Ground.class, new SpriteSpriteCollisionListener<Zombie, Ground>() {
            @Override
            public void collision (Zombie z, Ground g) {
                z.touchingFloor = true;
                z.isJumping = false;
                z.setVel(z.getVelX(), 0);

                if (!z.isRunning) {
                    switch (z.direction) {
                        case 1 -> {
                            z.setDrawingPriority(4);
                            z.setPicture(z.sprites[0][1]);
                        }
                        case -1 -> {
                            z.setDrawingPriority(4);
                            z.setPicture(z.sprites[0][0]);
                        }
                    }
                }
            }
        });
        sc.addSpriteSpriteCollisionListener(Zombie.class, Zombie.class, new SpriteSpriteCollisionListener<Zombie, Zombie>() {
            @Override
            public void collision(Zombie z1, Zombie z2) {
                z1.setX(z1.getX() - z1.direction * z1.getWidth());
                z1.setVel(-z1.getVelX(), -z1.getVelY());

                z2.setX(z2.getX() - z2.direction * z2.getWidth());
                z2.setVel(-z2.getVelX(), -z2.getVelY());
            }
        });



        ClockWorker.addTask(sc.moveSprites());


        // When 'esc' pressed, return to TitleCard
        mc.addKeyListener(new KeyWrapper(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    hideCard();
                    TitleCard.card.showCard();
                }
            }
        }));
    }

    public void showCard() {
        mc.showCard();
        mc.requestFocus();
    }
    public void hideCard() {
        mc.setVisible(false);
    }
}
