package survivalgame;

import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.images.Picture;

public class LifeCrystal extends Interactable {

    private final int health = 30;

    public LifeCrystal(Scene scene) {
        super(scene);

        freezeOrientation = true;
        setDrawingPriority(2);
        setPicture(new Picture("heart.png"));

    }

    public int giveHealth() {
        return this.health;
    }


}
