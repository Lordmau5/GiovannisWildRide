package com.lordmau5.giovanni.world.tiles;

import com.lordmau5.giovanni.entity.Entity;
import com.lordmau5.giovanni.entity.Player;
import com.lordmau5.giovanni.util.Direction;
import com.lordmau5.giovanni.util.ImageLoader;
import com.lordmau5.giovanni.world.Tile;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class Spin extends com.lordmau5.giovanni.world.tiles.WorldTile {

    private Direction direction = Direction.DOWN;
    private Image[] images = new Image[Direction.values().length];

    public Spin(int x, int y) {
        super(x, y);

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("tiles/spin.png"), 32, 32);
        for (int i = 0; i < Direction.values().length; i++)
            images[i] = sheet.getSprite(i, 0);
    }

    public Spin(int x, int y, Direction direction) {
        this(x, y);

        this.direction = direction;

        getInitialState().setState("Direction", direction);
    }

    @Override
    public com.lordmau5.giovanni.world.tiles.WorldTile copyTile() {
        Spin spin = (Spin) super.copyTile();
        spin.setDirection(direction);
        return spin;
    }

    @Override
    public void resetState() {
        super.resetState();

        this.direction = (Direction) getInitialState().getState("Direction");
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction dr) {
        this.direction = dr;
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        return true;
    }

    @Override
    public Renderable getRenderer() {
        return images[direction.ordinal()];
    }

    @Override
    public Image getImage() {
        return images[direction.ordinal()];
    }

    @Override
    public String getSaveString() {
        return super.getSaveString() + "," + getDirection().ordinal();
    }

    @Override
    public void initiate(String[] variables) {
        setDirection(Direction.values()[Integer.parseInt(variables[3])]);
        getInitialState().setState("Direction", direction);
    }

    @Override
    public void onCollide(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;

            int[] pos = getAbsolutePosition();
            player.startSpinning(new Tile(pos[0], pos[1]), direction);
        }
    }
}
