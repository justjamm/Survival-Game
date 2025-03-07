package SurvivalGame;//package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class TitleCard {
    public static Card card;
    public static MainCard mainCard;
    private final Picture[] splashes = {new Picture("corruption_splash.png"), new Picture("crimson_splash.png"), new Picture("desert_splash.png"), new Picture("forest_splash.png"), new Picture("hallow_splash.png"), new Picture("jungle_splash.png"), new Picture("mushroom_splash.png"), new Picture("ocean_splash.png"), new Picture("snow_splash.png")};
    private final String[][] layout = {
            {"Title"},
            {"Button1"},
            {"Button2"}
    };
    private static SpriteComponent sc;
    private final Picture titleLogo = new Picture("title1.png");
    private final Random RANDOM = new Random();

    public TitleCard(BasicFrame f, MainCard mc) {
        card = f.getCard();
        mainCard = mc;

        // Set background randomly to 1 of 10 splash pictures
        card.setPainter(new BackgroundPainter(splashes[RANDOM.nextInt(splashes.length)]));
        card.setStringLayout(layout);

        // non functional, implement later
        SpriteComponent sc = new SpriteComponent();
        Background bg = new Background(sc.getScene(), sc.getScene().getBackgroundSize());
        bg.setDrawingPriority(1);
        bg.setPicture(splashes[RANDOM.nextInt(splashes.length)]);
        bg.setX(0);
        bg.setY(0);

        // Terraria Logo
        Picture title = new Picture("title1.png");
        JLabel TitlePicture = new JLabel(title.getIcon());
        card.add("Title", TitlePicture);

        // Start Button
        JButton start = new JButton("Start");
        start.setOpaque(false);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.showCard();
            }
        });
        card.add("Button1", start);

        // Exit Button
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
        card.add("Button2", exit);

    }
    public static void showCard() {
        card.showCard();
        card.requestFocus();
    }
    public static void hideCard() {
        card.setVisible(false);
    }
}