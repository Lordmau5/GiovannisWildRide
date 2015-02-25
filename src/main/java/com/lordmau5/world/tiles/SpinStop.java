package main.java.com.lordmau5.world.tiles;

import main.java.com.lordmau5.entity.Entity;
import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.ImageLoader;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class SpinStop extends WorldTile {

    private Image image;

    public SpinStop(int x, int y) {
        super(x, y);

        image = ImageLoader.loadImage("tiles/stopSpin.png");
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        return true;
    }

    @Override
    public Renderable getRenderer() {
        return image;
    }

    @Override
    public void onCollide(Entity entity) {
        if(entity instanceof Player)
            ((Player) entity).stopSpinning();
    }
}
