package SurvivalGame;

import basicgraphics.ClockWorker;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.Task;
import basicgraphics.images.Picture;

import java.awt.*;

public class Zombie extends Sprite {
    private Picture[][] sprites = {
        {new Picture("zombie0.png"), new Picture("zombie0f.png")},
        {new Picture("zombie1.png"), new Picture("zombie1f.png")},
        {new Picture("zombie2.png"), new Picture("zombie2f.png")}};

    public Zombie(Scene scene, Dimension d) {
        super(scene);
        freezeOrientation = true;

        setDrawingPriority(4);
        setPicture(sprites[0][0]);

        setY(-getHeight());

        ClockWorker.addTask(new Task() {
            @Override
            public void run() {

            }
        });

    }
}
