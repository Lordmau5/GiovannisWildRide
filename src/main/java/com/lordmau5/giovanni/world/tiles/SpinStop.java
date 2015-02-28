package com.lordmau5.giovanni.world.tiles;

import com.lordmau5.giovanni.world.tiles.WorldTile;
import com.lordmau5.giovanni.entity.Entity;
import com.lordmau5.giovanni.entity.Player;
import com.lordmau5.giovanni.util.ImageLoader;
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
        if (entity instanceof Player)
            ((Player) entity).stopSpinning();
    }
}
