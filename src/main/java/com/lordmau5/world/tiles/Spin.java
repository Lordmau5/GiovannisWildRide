package main.java.com.lordmau5.world.tiles;

import main.java.com.lordmau5.entity.Entity;
import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.util.ImageLoader;
import main.java.com.lordmau5.world.Tile;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class Spin extends WorldTile {

    private Direction direction = Direction.DOWN;
    private Image[] images = new Image[Direction.values().length];

    public Spin(int x, int y) {
        super(x, y);

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("tiles/spin.png"), 32, 32);
        for(int i=0; i<Direction.values().length; i++)
            images[i] = sheet.getSprite(i, 0);
    }

    public Spin(int x, int y, Direction direction) {
        this(x, y);

        this.direction = direction;
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
    }

    @Override
    public void onCollide(Entity entity) {
        if(entity instanceof Player) {
            Player player = (Player) entity;

            int[] pos = getAbsolutePosition();
            player.startSpinning(new Tile(pos[0], pos[1]), direction);
        }
    }
}
