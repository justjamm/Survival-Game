package SurvivalGame;

import basicgraphics.Scene;
import basicgraphics.images.Picture;

import java.util.Random;

public class Tree extends Interactable {

    Picture left = new Picture("tree0.png");
    Picture right = new Picture("tree0f.png");

    final Random RANDOM = new Random();

    public Tree(Scene scene) {
        super(scene);

        setDrawingPriority(1);
        if (RANDOM.nextBoolean()) setPicture(left);
        else setPicture(right);

    }
}
