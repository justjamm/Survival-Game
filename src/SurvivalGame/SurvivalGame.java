package SurvivalGame;

import basicgraphics.BasicFrame;
import basicgraphics.ClockWorker;
import basicgraphics.SpriteComponent;

import java.awt.*;

public class  SurvivalGame {
    public static void main(String[] args) {
        BasicFrame bf = new BasicFrame("Survival Game");
        MainCard mc = new MainCard(bf);
        TitleCard tc = new TitleCard(bf, mc);
        ClockWorker.initialize(5);
        bf.show();
        tc.showCard();
    }
}
