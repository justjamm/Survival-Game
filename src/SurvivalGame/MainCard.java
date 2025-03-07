package SurvivalGame;

import basicgraphics.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MainCard {

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


        // BACKGROUND AND GROUND
        BackgroundHandler bgh = new BackgroundHandler(sc.getScene(), sc.getScene().getBackgroundSize(), NUM_BACKGROUNDS);
        Ground g = new Ground(sc.getScene(), sc.getScene().getBackgroundSize());

        // PLAYER
        Player p = new Player(sc.getScene(), sc.getScene().getBackgroundSize());
        p.setX(g.getWidth() / 2);
        sc.getScene().setFocus(p);
        sc.addMouseListener(p.ma);
        sc.addSpriteSpriteCollisionListener(Player.class, Ground.class, p.GroundCollision);
        sc.addSpriteSpriteCollisionListener(Player.class, Enemy.class, p.EnemyCollision);
        mc.addKeyListener(p.kl);

        // ENEMIES

//        Zombie[] zombs = new Zombie[3];
//        for (int i = 0; i < zombs.length; i++) {
//            zombs[i] = new Zombie(sc.getScene(), sc.getScene().getBackgroundSize(), Integer.toString(i));
//            zombs[i].setX(bruh);
//            bruh += 400;
//            zombs[i].setVel(.1 * Math.pow(-1, i), zombs[i].getVelY());
//
//            sc.addSpriteSpriteCollisionListener(Zombie.class, Ground.class, zombs[i].GroundCollision);
//
//            System.out.printf("Zombie %s spawned\n", zombs[i].tag);
//        }

        Zombie[] zombs = new Zombie[3];

        Zombie z = new Zombie(sc.getScene(), sc.getScene().getBackgroundSize());
        z.setX(1200);
        z.setVel(-0.1, z.getVelY());
        sc.addSpriteSpriteCollisionListener(Zombie.class, Ground.class, z.GroundCollision);
        zombs[0] = z;

        Zombie z1 = new Zombie(sc.getScene(), sc.getScene().getBackgroundSize());
        z1.setX(1200);
        z1.setVel(0.1, z1.getVelY());
        sc.addSpriteSpriteCollisionListener(Zombie.class, Ground.class, z1.GroundCollision);
        zombs[1] = z1;



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
