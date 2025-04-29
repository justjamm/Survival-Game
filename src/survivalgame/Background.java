package survivalgame;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;

public class Background extends Sprite {

    public Background(Scene s, Dimension d) {
        super(s);
        freezeOrientation = true;
        setDrawingPriority(1);
        setPicture(new Picture("Forest_background_0.png"));
        setY(-0.85 * getHeight());
    }
}