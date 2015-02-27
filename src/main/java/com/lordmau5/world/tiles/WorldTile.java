package main.java.com.lordmau5.world.tiles;

import main.java.com.lordmau5.entity.Entity;
import main.java.com.lordmau5.util.TileRegistry;
import main.java.com.lordmau5.world.Tile;
import main.java.com.lordmau5.world.tiles.state.InitialState;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class WorldTile extends Tile implements Entity {
    private InitialState initialState = new InitialState();

    public WorldTile(int x, int y) {
        super(x, y);
    }

    public InitialState getInitialState() {
        return initialState;
    }

    public Image getImage() {
        if(getRenderer() instanceof Image)
            return (Image) getRenderer();
        return null;
    }

    public WorldTile copyTile() {
        return TileRegistry.createInstance(new Tile(x, y), getClass().getSimpleName());
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        return false;
    }

    @Override
    public Renderable getRenderer() {
        return null;
    }

    @Override
    public int[] getRelativePosition() {
        int[] pos = getPosition();
        return new int[]{pos[0] * 16, pos[1] * 16};
    }

    public void initiate(String[] variables) {

    }

    public void resetState() {}

    public String getSaveString() {
         return x + "," + y + "," + getClass().getSimpleName();
    }

    public void onCollide(Entity entity) {

    }
}
