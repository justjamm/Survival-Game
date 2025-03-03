package SurvivalGame;

import basicgraphics.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainCard {

    public static Card mc;
    private final SpriteComponent sc;
    private final int RANDOM = (int)(Math.random() * 10);
    private Dimension BOARD;

    public MainCard(BasicFrame f) {
        mc = f.getCard();
        BOARD = f.FRAME_SIZE;
        sc = new SpriteComponent();
        sc.setPreferredSize(BOARD);
        BasicLayout layout = new BasicLayout();
        mc.setLayout(layout);
        mc.add("x=0,y=0,w=4,h=4", sc);

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

        sc.getScene().periodic_x = true;
        sc.getScene().periodic_y = true;

        Player p = new Player(sc.getScene(), BOARD);

        BackgroundHandler bgh = new BackgroundHandler(sc.getScene(), BOARD, 6);
        Ground g = new Ground(sc.getScene(), BOARD);
        MovementHandler mh = new MovementHandler(mc, p, g, bgh);
        sc.addMouseListener(mh.ma);


        ClockWorker.addTask(sc.moveSprites());

        sc.addSpriteSpriteCollisionListener(Player.class, Ground.class, new SpriteSpriteCollisionListener<Player, Ground>() {
            @Override
            public void collision(Player sp1, Ground sp2) {
                p.touchingFloor = true;
                p.setVel(p.getVelX(), 0);
                p.setY(p.getY() - 2 * p.getVelY());
                if (p.isLeft) {
                    p.setPicture(p.sprites[0][0]);
                }
                else p.setPicture(p.sprites[0][1]);
                System.out.println("collision");

        }});

    }
    public void showCard() {
        mc.showCard();
        mc.requestFocus();
    }
    public void hideCard() {
        mc.setVisible(false);
    }
}
