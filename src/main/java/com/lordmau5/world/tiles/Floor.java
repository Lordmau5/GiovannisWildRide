package main.java.com.lordmau5.world.tiles;

import main.java.com.lordmau5.entity.Entity;
import main.java.com.lordmau5.util.ImageLoader;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class Floor extends WorldTile {

    private Image image;

    public Floor(int x, int y) {
        super(x, y);

        image = ImageLoader.loadImage("tiles/floor.png");
    }

    @Override
    public Renderable getRenderer() {
        return image;
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        return true;
    }
}
