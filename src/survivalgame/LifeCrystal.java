package survivalgame;

import basicgraphics.Scene;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;

public class LifeCrystal extends Interactable {

    private final int healthBoost = 20;
    final ReusableClip useAudio = new ReusableClip("star.wav");

    public LifeCrystal(Scene scene) {
        super(scene);

        freezeOrientation = true;
        setDrawingPriority(2);
        setPicture(new Picture("heart.png"));

    }

    public int giveHealth() {
        useAudio.playNow();
        return healthBoost;
    }


}
