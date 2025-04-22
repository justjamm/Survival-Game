package SurvivalGame;

import basicgraphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
                            if (!p.isSwinging) {
                                p.setDrawingPriority(5);
                                p.setPicture(p.spriteR);
                            }
                        }
                        case -1 -> {
                            if (!p.isSwinging) {
                                p.setDrawingPriority(5);
                                p.setPicture(p.spriteL);
                            }
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
            Zombie finalZ = z;
            sc.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    double eX = e.getX();
                    double eY = e.getY();
                    double zX = finalZ.getCenterX();
                    double zY = finalZ.getCenterY();
                    double zW = 50;

                    System.out.println("eX: " + eX + " abs: " + e.getXOnScreen());

//                    if ((e.getX() + finalZ.getWidth() >= finalZ.getX() || e.getX() - finalZ.getWidth() <= finalZ.getX()) && (e.getY() + finalZ.getWidth() >= finalZ.getY() || e.getY() - finalZ.getHeight() <= finalZ.getY())) {
//                        finalZ.takeDamage(p.giveDamage());
//                        System.out.println("Mouse Position X: " + e.getX() + " Y: " + e.getY());
//                        System.out.println(finalZ.tag + " X: " + finalZ.getX() + " Y: " + finalZ.getY());
//                        System.out.println("width: " + finalZ.getWidth());
//                    }


                    // Convert screen coordinates to game coordinates
                    //
                    //
                    boolean xCheck = false;
                    boolean yCheck = false;
                    if (eX + zW >= zX && eX - zW <= zX) {
                        xCheck = true;
                    }
                    if (eY + zW >= zY && eY - zW <= zY) {
                        yCheck = true;
                    }
                    if (xCheck && yCheck) {
                        System.out.println("checked");
                    }
                }
            });

        }
        sc.addSpriteSpriteCollisionListener(Player.class, Zombie.class, new SpriteSpriteCollisionListener<Player, Zombie>() {
            @Override
            public void collision(Player p, Zombie z) {
                //p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
                p.takeDamage(z.giveDamage());
            }
        });

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
                z1.setVel(0, z1.getVelY());
            }
        });

        // DEMON EYES
        initSpawn = 200;
        enemyTag = 0;
        DemonEye[] eyes = new DemonEye[3];
        for (DemonEye eye : eyes) {
            eye = new DemonEye(sc.getScene(), p);
            eye.setX(initSpawn);
            eye.tag = String.format("Demon Eye %d", enemyTag++);
            initSpawn += 400;

            DemonEye finalZ = eye;
            sc.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if ((e.getX() + finalZ.getWidth() >= finalZ.getX() || e.getX() - finalZ.getWidth() <= finalZ.getX()) && (e.getY() + finalZ.getWidth() >= finalZ.getY() || e.getY() - finalZ.getHeight() <= finalZ.getY())) {
                        finalZ.takeDamage(p.giveDamage());
                        System.out.println("Mouse Position X: " + e.getX() + " Y: " + e.getY());
                    }
                }
            });
        }
        sc.addSpriteSpriteCollisionListener(Player.class, DemonEye.class, new SpriteSpriteCollisionListener<Player, DemonEye>() {
            @Override
            public void collision(Player p, DemonEye de) {
                //p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
                if (!de.hitPlayer) {
                    p.takeDamage(de.giveDamage());
                    de.hitPlayer = true;
                }
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
