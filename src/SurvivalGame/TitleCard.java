package SurvivalGame;//package SurvivalGame;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TitleCard {
    public static Card card;
    public static MainCard mainCard;
    private final String[] splashes = {"corruption_splash.png", "crimson_splash.png", "desert_splash.png", "forest_splash.png", "hallow_splash.png", "jungle_splash.png", "mushroom_splash.png", "ocean_splash.png", "snow_splash.png", "forest_splash.png", "snow_splash.png"};
    private final String[][] layout = {
            {"Title"},
            {"Button1"},
            {"Button2"}
    };
    private static SpriteComponent sc;
    private final Picture titleLogo = new Picture("title1.png");
    private final double RANDOM = Math.random() * 10;

    public TitleCard(BasicFrame f, MainCard mc) {
        card = f.getCard();
        mainCard = mc;

        // Set background randomly to 1 of 10 splash pictures
        card.setPainter(new BackgroundPainter(new Picture(splashes[(int) RANDOM])));
        card.setStringLayout(layout);

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
                System.out.println("Start pressed.");
                mc.showCard();
            }
        });
        card.add("Button1", start);

        // Exit Button
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit pressed.");
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