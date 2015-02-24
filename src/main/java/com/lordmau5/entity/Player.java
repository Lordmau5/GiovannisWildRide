package main.java.com.lordmau5.entity;

import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.util.ImageLoader;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class Player implements Entity {

    private float x, y;

    private Direction facing = Direction.DOWN;

    private Animation[] iFacing = new Animation[4];
    private Animation anim;

    private int movement = 2;
    private boolean moving;

    public Player() {
        for (Direction dr : Direction.values()) {
            iFacing[dr.ordinal()] = new Animation(new SpriteSheet(ImageLoader.loadImage("player/walking/" + dr.name().toLowerCase() + ".png"), 14, 16), 200);
        }

        x = 0;
        y = 0;

        anim = iFacing[facing.ordinal()];
        anim.setAutoUpdate(false);
    }

    public void move(Direction dr, int delta) {
        facing = dr;
        anim = iFacing[facing.ordinal()];
        anim.setAutoUpdate(true);

        if (dr == Direction.UP) {
            y = Math.max(y - movement, 0);
        } else if (dr == Direction.DOWN) {
            y = Math.min(y + movement, 600 - 17);
        } else if (dr == Direction.LEFT) {
            x = Math.max(x - movement, 0);
        } else if (dr == Direction.RIGHT) {
            x = Math.min(x + movement, 800 - 15);
        }
        this.moving = true;
    }

    public void stopMoving() {
        this.moving = false;
        anim.setAutoUpdate(false);
        anim.setCurrentFrame(0);
    }

    public Direction getFacing() {
        return facing;
    }

    @Override
    public Renderable getRenderer() {
        return anim;
    }

    @Override
    public float[] getPosition() {
        return new float[]{x, y};
    }
}
