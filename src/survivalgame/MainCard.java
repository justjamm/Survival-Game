package survivalgame;

import basicgraphics.*;
import basicgraphics.sounds.ReusableClip;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class  MainCard {

    public static Card mc;
    private final SpriteComponent sc;
    private final Random RANDOM = new Random();
    private Dimension BOARD;
    private final int NUM_BACKGROUNDS = 4;





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

        ReusableClip music1 = new ReusableClip("title.wav");
        music1.loop();

        // BACKGROUND & GROUND
        BackgroundHandler bgh = new BackgroundHandler(sc.getScene(), sc.getScene().getBackgroundSize(), NUM_BACKGROUNDS);
        Ground g = new Ground(sc.getScene(), sc.getScene().getBackgroundSize());

        // PLAYER
        Player p = new Player(sc.getScene());
        //p.setX(g.getWidth() / 2);
        p.setX(100);
        p.setY(430);
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
        mc.addKeyListener(p.kl);


        //
        //
        // ENEMIES
        //
        //


        //
        // ZOMBIES
        //

        Zombie[] zombs = new Zombie[5];
//        ClockWorker.addTask(new Task() {
//           @Override
//           public void run() {
               for (int i = 0; i < zombs.length; i++) {
                   if (zombs[i] == null || !zombs[i].isActive()) {
                       Zombie z = new Zombie(sc.getScene(), p);
                       z.setX(RANDOM.nextInt(100, 3500));
                       z.tag = String.format("Zombie %d", i);
                       zombs[i] = z;
                       int j = i;
                       mc.addMouseListener(new MouseAdapter() {
                           @Override
                           public void mouseClicked(MouseEvent e) {
                               System.out.println("Zombie clicked");

                               if (between(zombs[j].getX(), p.getX() - 100, p.getX() + 100)) {
                                   System.out.println("Zombie hit!");
                               }
                           }

                       });
                   }
               }
//           }
//        });

        // PLAYER-ZOMBIE COLLISION
        sc.addSpriteSpriteCollisionListener(Player.class, Zombie.class, new SpriteSpriteCollisionListener<Player, Zombie>() {
            @Override
            public void collision(Player p, Zombie z) {
                if (p.isSwinging) {
                    z.takeDamage(p.giveDamage());
                    int knockback = 50 * z.direction;
                    z.setVel(-2 * z.getVelX(), -0.5 * z.getVelY());
                    z.setX(z.getX() - knockback);
                }
                else {
                    p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
                    p.takeDamage(z.giveDamage());
                }
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

        //
        // DEMON EYES
        //

        DemonEye[] eyes = new DemonEye[3];
//        ClockWorker.addTask(new Task() {
//            @Override
//            public void run() {
                for (int i = 0; i < eyes.length; i++) {
                    if (eyes[i] == null || !eyes[i].isActive()) {
                        DemonEye de = new DemonEye(sc.getScene(), p);
                        de.setX(RANDOM.nextInt(100, 3500));
                        de.setY(RANDOM.nextInt(200, 375));
                        de.tag = String.format("Demon Eye %d", i);
                        eyes[i] = de;
                    }
                }
//            }
//        });

        // PLAYER-EYE COLLISION
        sc.addSpriteSpriteCollisionListener(Player.class, DemonEye.class, new SpriteSpriteCollisionListener<Player, DemonEye>() {
            @Override
            public void collision(Player p, DemonEye de) {
                p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
//                if (!de.hitPlayer) {
                    p.takeDamage(de.giveDamage());
                    de.hitPlayer = true;
//                }
            }
        });

        //EYE-EYE COLLISION
        sc.addSpriteSpriteCollisionListener(DemonEye.class, DemonEye.class, new SpriteSpriteCollisionListener<DemonEye, DemonEye>() {
            @Override
            public void collision(DemonEye d1, DemonEye d2) {
                d1.setVel(-d1.getVelX(), d1.getVelY());
            }
        });

        //
        // INTERACTABLES
        //

        int treeX = 100;
        Tree[] trees = new Tree[15];
        for (int i=0;i<trees.length;i++) {
            Tree t = new Tree(sc.getScene());
            t.setX(RANDOM.nextInt(treeX - 50, treeX + 50));
            treeX += 200;
            t.setY(1.1*t.getHeight());
            trees[i] = t;
        }


        Platform[] platforms = new Platform[35];
        int platformsX = 100;
        for (int i = 0; i < platforms.length; i++) {
            Platform plat = new Platform(sc.getScene());
            plat.setX(platformsX);
            platformsX += 200;
            plat.setY(RANDOM.nextInt(100, 300));
            platforms[i] = plat;
        }

        // ZOMBIE-PLATFORM COLLISION
        sc.addSpriteSpriteCollisionListener(Zombie.class, Platform.class, new SpriteSpriteCollisionListener<Zombie, Platform>() {
            @Override
            public void collision (Zombie e, Platform plat) {
                e.setVel(-e.getVelX(), 0);
            }
        });
        sc.addSpriteSpriteCollisionListener(DemonEye.class, Platform.class, new SpriteSpriteCollisionListener<DemonEye, Platform>() {
            @Override
            public void collision (DemonEye e, Platform plat) {
                e.setVel(e.getVelX(), e.getVelY());
            }
        });
        // PLAYER-PLATFORM COLLISION
        sc.addSpriteSpriteCollisionListener(Player.class, Platform.class, new SpriteSpriteCollisionListener<Player, Platform>() {
            @Override
            public void collision(Player p, Platform plat) {
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

        LifeCrystal[] lcrystals = new LifeCrystal[3];
        final int crystalsX = 800;
        for (int i = 0; i < lcrystals.length; i++) {
            if (lcrystals[i] == null || !lcrystals[i].isActive()) {
                LifeCrystal lc = new LifeCrystal(sc.getScene());
                lc.setX(crystalsX * (i + 1));
                lc.setY(430);
            }
        }

        sc.addSpriteSpriteCollisionListener(Player.class, LifeCrystal.class, new SpriteSpriteCollisionListener<Player, LifeCrystal>() {
            @Override
            public void collision(Player p, LifeCrystal lc) {
                if (lc.isActive()) {
                    p.addHealth(lc.giveHealth());
                    lc.destroy();
                }
            }
        });




        ClockWorker.addTask(sc.moveSprites());


        // ESCAPE TO RETURN TO MENU
        mc.addKeyListener(new KeyWrapper(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    hideCard();
                    TitleCard.showCard();
                }
            }
        }));
    }

    public boolean between(double value, double lower, double upper) {
        return (lower <= value && value <= upper);
    }

    public void showCard() {
        mc.showCard();
        mc.requestFocus();
    }

    public void hideCard() {
        mc.setVisible(false);
    }
}
