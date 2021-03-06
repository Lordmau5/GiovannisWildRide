package com.lordmau5.giovanni.world.tiles;

import com.lordmau5.giovanni.entity.Entity;
import com.lordmau5.giovanni.util.TileRegistry;
import com.lordmau5.giovanni.world.Tile;
import com.lordmau5.giovanni.world.tiles.state.InitialState;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class WorldTile extends Tile implements Entity {
    private InitialState initialState;

    public WorldTile(int x, int y) {
        super(x, y);
    }

    public void setInitialState(InitialState initialState) {
        this.initialState = initialState;
    }

    public InitialState getInitialState() {
        if(initialState == null)
            this.initialState = new InitialState();
        return initialState;
    }

    public Image getImage() {
        if (getRenderer() instanceof Image)
            return (Image) getRenderer();
        return null;
    }

    public WorldTile copyTile() {
        WorldTile worldTile = TileRegistry.createInstance(new Tile(x, y), getClass().getSimpleName());
        worldTile.setInitialState(worldTile.getInitialState());
        return worldTile;
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

    public void resetState() {
    }

    public String getSaveString() {
        return x + "," + y + "," + getClass().getSimpleName();
    }

    public void onCollide(Entity entity) {

    }
}
