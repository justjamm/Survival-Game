package survivalgame;

import basicgraphics.BasicFrame;
import basicgraphics.ClockWorker;

public class  SurvivalGame {
    public static void main(String[] args) {
        BasicFrame bf = new BasicFrame("Survival Game");
        TitleCard tc = new TitleCard(bf);
        ClockWorker.initialize(5);
        bf.show();
        TitleCard.showCard();
    }
}