package SurvivalGame;

import basicgraphics.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Clock;

public class MainCard {

    public static Card mc;
    private final SpriteComponent sc;
    private final int RANDOM = (int)(Math.random() * 10);
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
        sc.addSpriteSpriteCollisionListener(Player.class, Ground.class, p.gcl);
        mc.addKeyListener(p.kl);

        // ENEMIES
        Zombie z = new Zombie(sc.getScene(), sc.getScene().getBackgroundSize());
        z.setX(1200);
        z.setY(400);
        sc.addSpriteSpriteCollisionListener(Zombie.class, Ground.class, z.gcl);
        sc.addSpriteSpriteCollisionListener(Player.class, Zombie.class, z.pcl);
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
