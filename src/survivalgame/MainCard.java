package survivalgame;

import basicgraphics.*;
import basicgraphics.sounds.ReusableClip;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class  MainCard {

    public static Card mc;
    private final SpriteComponent sc;
    private final Random RANDOM = new Random();
    private Dimension BOARD;
    private final int NUM_BACKGROUNDS = 4;

    private static final ReusableClip music1 = new ReusableClip("Music-Overworld_Day.wav");


    private int zombCap;
    private int zombTag;
    private int eyeCap;
    private int eyeTag;
    private int heartCap;




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
        BackgroundHandler bgh = new BackgroundHandler(sc.getScene(), 1200 * NUM_BACKGROUNDS, 3928, NUM_BACKGROUNDS);
        Ground g = new Ground(sc.getScene(), 1200 * NUM_BACKGROUNDS, 3928);

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

        ArrayList<Zombie> zombs = new ArrayList<>();
        int zombsX = 500;
        zombCap = 5;
        zombTag = 0;
        for (int i = 0; i <= zombCap; i++) {
            Zombie z = new Zombie(sc.getScene(), p);
            z.setX(zombsX);
            zombsX += 500;
            z.setY(430);
            z.tag = String.format("Zombie %d", zombTag++);

            ClockWorker.addTask(new Task() {
                public void run() {
                   if ((z.getX() + 2*z.getWidth() >= p.getX() && p.getX() >= z.getX() - 2*z.getWidth()) && (z.getY() + 2*z.getHeight() >= p.getY() && p.getY() >= z.getY() - 2*z.getHeight())) {
                       if (p.isSwinging && z.isActive()) {
                           z.takeDamage(p.giveDamage());
                           int knockback = 50 * z.direction;
                           z.setVel(-2 * z.getVelX(), -0.5 * z.getVelY());
                           z.setX(z.getX() - knockback);
                       }
                   }

                   if ((z.getX() + z.getWidth() >= p.getX() && p.getX() >= z.getX() - z.getWidth()) && (z.getY() + z.getHeight() >= p.getY() && p.getY() >= z.getY() - z.getHeight())) {
                       if (z.isActive()) {
                           if (!p.isHit) {
                               if (p.direction == 1) {
                                   p.setX(p.getX() - 50);
                               }
                               else {
                                   p.setX(p.getX() + 50);
                               }
                               p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
                               p.takeDamage(z.giveDamage());
                           }
                       }
                   }


                }
            });

            zombs.add(z);
            int j = i;
            mc.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                   System.out.println("Zombie clicked");

                   if (between(zombs.get(j).getX(), p.getX() - 100, p.getX() + 100)) {
                       System.out.println("Zombie hit!");
                   }
                }

            });
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


        //
        // DEMON EYES
        //

//        DemonEye[] eyes = new DemonEye[6];
//                for (int i = 0; i < eyes.length; i++) {
//                    if (eyes[i] == null || !eyes[i].isActive()) {
//                        DemonEye de = new DemonEye(sc.getScene(), p);
//                        de.setX(RANDOM.nextInt(500, 1200 * NUM_BACKGROUNDS));
//                        de.setY(RANDOM.nextInt(200, 375));
//                        de.tag = String.format("Demon Eye %d", i);
//
//                        ClockWorker.addTask(new Task() {
//                            public void run() {
//                                if ((de.getX() + 2*de.getWidth() >= p.getX() && p.getX() >= de.getX() - 2*de.getWidth()) && (de.getY() + 2*de.getHeight() >= p.getY() && p.getY() >= de.getY() - 2*de.getHeight())) {
//                                    if (p.isSwinging && de.isActive()) {
//                                        de.takeDamage(p.giveDamage());
//                                        int knockback = 50 * de.direction;
//                                        de.setVel(-2 * de.getVelX(), -0.5 * de.getVelY());
//                                        de.setX(de.getX() - knockback);
//                                    }
//                                }
//
//                                if ((de.getX() + de.getWidth() >= p.getX() && p.getX() >= de.getX() - de.getWidth()) && (de.getY() + de.getHeight() >= p.getY() && p.getY() >= de.getY() - de.getHeight())) {
//                                    if (de.isActive()) {
//                                        if (!p.isHit) {
//                                            p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
//                                            p.takeDamage(de.giveDamage());
//                                        }
//                                    }
//                                }
//                            }
//                        });
//
//                        eyes[i] = de;
//                    }
//                }
        ArrayList<DemonEye> eyes = new ArrayList<>();
        int eyeX = 500;
        eyeCap = 5;
        eyeTag = 0;
        for (int i = 0; i <= eyeCap; i++) {
            DemonEye de = new DemonEye(sc.getScene(), p);
            de.setX(eyeX);
            eyeX += 500;
            de.setY(RANDOM.nextInt(300, 400));
            de.tag = String.format("Demon Eye %d", eyeTag++);

            ClockWorker.addTask(new Task() {
                public void run() {
                    if ((de.getX() + 2*de.getWidth() >= p.getX() && p.getX() >= de.getX() - 2*de.getWidth()) && (de.getY() + 2*de.getHeight() >= p.getY() && p.getY() >= de.getY() - 2*de.getHeight())) {
                        if (p.isSwinging && de.isActive()) {
                            de.takeDamage(p.giveDamage());
                            int knockback = 50 * de.direction;
                            //de.setVel(-2 * de.getVelX(), -0.5 * de.getVelY());
                            de.setX(de.getX() - knockback);
                        }
                    }

                    if ((de.getX() + de.getWidth() >= p.getX() && p.getX() >= de.getX() - de.getWidth()) && (de.getY() + de.getHeight() >= p.getY() && p.getY() >= de.getY() - de.getHeight())) {
                        if (de.isActive()) {
                            if (!p.isHit) {
                                if (p.direction == 1) {
                                    p.setX(p.getX() - 50);
                                }
                                else {
                                    p.setX(p.getX() + 50);
                                }
                                p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
                                p.takeDamage(de.giveDamage());
                            }
                        }
                    }
                }
            });

            eyes.add(de);
            int j = i;
            mc.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Demon Eye clicked");

                    if (between(zombs.get(j).getX(), p.getX() - 100, p.getX() + 100)) {
                        System.out.println("Demon Eye hit!");
                    }
                }

            });
        }



        //
        // INTERACTABLES
        //

        int treeX = 100;
        Tree[] trees = new Tree[15];
        for (int i=0;i<trees.length;i++) {
            if (trees[i] == null || !trees[i].isActive()) {
                Tree t = new Tree(sc.getScene());
                t.setX(RANDOM.nextInt(treeX - 50, treeX + 50));
                treeX += 200;
                t.setY(1.1*t.getHeight());
                trees[i] = t;
            }
        }


        Platform[] platforms = new Platform[35];
        int platformsX = 100;
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i] == null || !platforms[i].isActive()) {
                Platform plat = new Platform(sc.getScene());
                plat.setX(platformsX);
                platformsX += 200;
                plat.setY(RANDOM.nextInt(100, 300));
                platforms[i] = plat;


            }
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

        // LIFE CRYSTALS
        LifeCrystal[] lcrystals = new LifeCrystal[3];
        int crystalsX = 800;
                for (int i = 0; i < lcrystals.length; i++) {
                    if (lcrystals[i] == null || !lcrystals[i].isActive()) {
                        LifeCrystal lc = new LifeCrystal(sc.getScene());
//                        lc.setX(RANDOM.nextInt(500, 1200 * NUM_BACKGROUNDS));
                        lc.setX(crystalsX);
                        crystalsX += 800;
                        lc.setY(430);

                        ClockWorker.addTask(new Task() {
                            @Override
                            public void run() {
                                if ((lc.getX() + lc.getWidth() >= p.getX() && p.getX() >= lc.getX() - lc.getWidth()) && (lc.getY() + lc.getHeight() >= p.getY() && p.getY() >= lc.getY() - lc.getHeight())) {
                                    if (lc.isActive()) {
                                        p.addMaxHealth(lc.giveHealth());
                                        lc.destroy();
                                    }
                                }
                            }

                        });
                        lcrystals[i] = lc;
                    }
                }

        // 2ND WAVES OF ZOMBIES
        ClockWorker.addTask(new Task() {
            @Override
            public void run() {

                zombs.removeIf(z -> !z.isActive());

                if (zombs.isEmpty()) {
                    System.out.println("2ND WAVE OF ZOMBIES");
                    int zombsX = 500;
                    zombCap *= 2;
                    for (int i = 0; i <= zombCap; i++) {
                        Zombie z = new Zombie(sc.getScene(), p);
                        z.setX(zombsX);
                        zombsX += 500;
                        z.setY(430);
                        z.tag = String.format("Zombie %d", zombTag++);

                        ClockWorker.addTask(new Task() {
                            public void run() {
                                if ((z.getX() + 2*z.getWidth() >= p.getX() && p.getX() >= z.getX() - 2*z.getWidth()) && (z.getY() + 2*z.getHeight() >= p.getY() && p.getY() >= z.getY() - 2*z.getHeight())) {
                                    if (p.isSwinging && z.isActive()) {
                                        z.takeDamage(p.giveDamage());
                                        int knockback = 50 * z.direction;
                                        z.setVel(-2 * z.getVelX(), -0.5 * z.getVelY());
                                        z.setX(z.getX() - knockback);
                                    }
                                }

                                if ((z.getX() + z.getWidth() >= p.getX() && p.getX() >= z.getX() - z.getWidth()) && (z.getY() + z.getHeight() >= p.getY() && p.getY() >= z.getY() - z.getHeight())) {
                                    if (z.isActive()) {
                                        if (!p.isHit) {
                                            if (p.direction == 1) {
                                                p.setX(p.getX() - 50);
                                            }
                                            else {
                                                p.setX(p.getX() + 50);
                                            }
                                            p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
                                            p.takeDamage(z.giveDamage());
                                        }
                                    }
                                }


                            }
                        });

                        zombs.add(z);
                        int j = i;
                        mc.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                System.out.println("Zombie clicked");

                                if (between(zombs.get(j).getX(), p.getX() - 100, p.getX() + 100)) {
                                    System.out.println("Zombie hit!");
                                }
                            }

                        });
                    }

                }
            }
        });




        ClockWorker.addTask(new Task() {
            @Override
            public void run() {

            eyes.removeIf(de -> !de.isActive());

            if (eyes.isEmpty()) {
                System.out.println("2ND WAVE OF EYES");
                int eyeX = 500;
                eyeCap *= 2;
                for (int i = 0; i <= eyeCap; i++) {
                    DemonEye de = new DemonEye(sc.getScene(), p);
                    de.setX(eyeX);
                    eyeX += 500;
                    de.setY(RANDOM.nextInt(300, 400));
                    de.tag = String.format("Demon Eye %d", eyeTag++);

                    ClockWorker.addTask(new Task() {
                        public void run() {
                            if ((de.getX() + 2*de.getWidth() >= p.getX() && p.getX() >= de.getX() - 2*de.getWidth()) && (de.getY() + 2*de.getHeight() >= p.getY() && p.getY() >= de.getY() - 2*de.getHeight())) {
                                if (p.isSwinging && de.isActive()) {
                                    de.takeDamage(p.giveDamage());
                                    int knockback = 50 * de.direction;
                                    //de.setVel(-2 * de.getVelX(), -0.5 * de.getVelY());
                                    de.setX(de.getX() - knockback);
                                }
                            }

                            if ((de.getX() + de.getWidth() >= p.getX() && p.getX() >= de.getX() - de.getWidth()) && (de.getY() + de.getHeight() >= p.getY() && p.getY() >= de.getY() - de.getHeight())) {
                                if (de.isActive()) {
                                    if (!p.isHit) {
                                        if (p.direction == 1) {
                                            p.setX(p.getX() - 50);
                                        }
                                        else {
                                            p.setX(p.getX() + 50);
                                        }
                                        p.setVel(-2 * p.getVelX(), -0.5 * p.getVelY());
                                        p.takeDamage(de.giveDamage());
                                    }
                                }
                            }
                        }
                    });

                    eyes.add(de);
                    int j = i;
                    mc.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            System.out.println("Demon Eye clicked");

                            if (between(zombs.get(j).getX(), p.getX() - 100, p.getX() + 100)) {
                                System.out.println("Demon Eye hit!");
                            }
                        }

                    });
                }
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

    public static void showCard() {
        mc.showCard();
        mc.requestFocus();

        music1.loop();
    }

    public static void hideCard() {
        mc.setVisible(false);
        music1.stop();
    }

}
