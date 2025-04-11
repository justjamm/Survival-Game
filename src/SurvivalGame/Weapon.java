package SurvivalGame;

import basicgraphics.Scene;
import basicgraphics.Sprite;

import java.util.Random;

public class Weapon extends Sprite {

    private Random RAND  = new Random();
    public int damage;
    private int lo;
    private int hi;

    public Weapon(Scene scene, int lo, int hi) {
        super(scene);
        this.lo = lo;
        this.hi = hi;
    }

    public int giveDamage() {
        damage = RAND.nextInt(lo, hi);
        return damage;
    }
}
