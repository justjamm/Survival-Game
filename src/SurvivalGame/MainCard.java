package SurvivalGame;

import basicgraphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;

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
        Player p = new Player(sc.getScene());
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
        Scanner in = new Scanner(System.in);
        System.out.print("Enter player name: ");
        //p.name = in.nextLine();
        System.out.println();

        // PLAYER-ENEMY COLLISION
        sc.addSpriteSpriteCollisionListener(Player.class, Enemy.class, new SpriteSpriteCollisionListener<Player, Enemy>() {
            @Override
            public void collision(Player p, Enemy e) {
                p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
                p.takeDamage(e.giveDamage());
            }
        });
        mc.addKeyListener(p.kl);

        // ENEMIES
        // ZOMBIES
        int initSpawn = 200;
        int enemyTag = 0;
        Zombie[] zombs = new Zombie[5];
        for (Zombie z : zombs) {

            z = new Zombie(sc.getScene(), p);
            z.setX(initSpawn);
            z.tag = String.format("Zombie %d", enemyTag++);
            initSpawn += 400;

//            final Zombie z1 = z;
//            ClockWorker.addTask(new Task() {
//                @Override
//                public void run() {
//                   int detRad = 400; // detection radius of zombies
//
////                    if (z1.getX() + detRad >= p.getX() || z1.getX() - detRad <= p.getX()) {
////                        z1.trackingPlayer = true;
////                        z1.setVel(.5 * Math.random(), z1.getVelY());
////                    }
//                    double pX = p.getX();
//                    double zX = z1.getX();
//                    z1.setVel(z1.speedX, z1.getVelY());
//
//                    if ((pX < zX && pX >= zX - detRad) || (zX < pX && zX + detRad >= pX)) {
//                        z1.trackingPlayer = true;
//                    }
//                    else {
//                        z1.trackingPlayer = false;
//                        z1.setVel(0.3 * z1.direction * z1.speedX, z1.getVelY());
//                    }
//
//                    if (z1.trackingPlayer) {
//                        if (z1.getX() < p.getX()) {
//                            z1.setVel(Math.abs(z1.getVelX()), z1.getVelY());
//                        }
//                        else if (p.getX() < z1.getX()) {
//                            z1.setVel(-Math.abs(z1.getVelX()), z1.getVelY());
//                        }
//                    }
//                }
//            });

        }

        // ZOMBIE-GROUND COLLISION
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

        // ZOMBIE-ZOMBIE COLLISION
        sc.addSpriteSpriteCollisionListener(Zombie.class, Zombie.class, new SpriteSpriteCollisionListener<Zombie, Zombie>() {
            @Override
            public void collision(Zombie z1, Zombie z2) {
//                double x1 = z1.getX(), x2 = z2.getX(), v1 = z1.getVelX(), v2 = z2.getVelX();
//
//                z1.setVel(-v1, z1.getVelY());
//                z2.setVel(-v2, z2.getVelY());
//
//                if (x1 < x2) {
//                    z2.setX(x2 + z1.getWidth());
//                }
//                else if (x2 < x1) {
//                    z1.setX(x1 + z1.getWidth());
//                }

                z1.setVel(0, z1.getVelY());
            }
        });

        // DEMON EYES
        initSpawn = 200;
        enemyTag = 0;
        DemonEye[] eyes = new DemonEye[2];
        for (DemonEye eye : eyes) {
            eye = new DemonEye(sc.getScene(), p);
            eye.setX(initSpawn);
            eye.tag = String.format("Wandering Eye %d", enemyTag++);
            initSpawn += 400;

            System.out.println("eye: " + eye.getY());
        }



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
