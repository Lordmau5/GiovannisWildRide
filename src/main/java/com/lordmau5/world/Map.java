package main.java.com.lordmau5.world;

import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.world.tiles.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class Map {

    private List<WorldTile> worldTiles = new ArrayList<>();

    public Map() {
        for(int x=0; x<16; x++)
            for(int y=0; y<16; y++) {
                worldTiles.add(new Floor(x, y));
            }

        replaceTileAt(new Tile(4, 5), new Wall(4, 5, 14));
        replaceTileAt(new Tile(8, 10), new Spin(8, 10, Direction.DOWN));
        //replaceTileAt(new Tile(8, 12), new Spin(8, 12, Direction.RIGHT));
        replaceTileAt(new Tile(10, 12), new Spin(10, 12, Direction.UP));
        replaceTileAt(new Tile(10, 10), new Spin(10, 10, Direction.LEFT));
        replaceTileAt(new Tile(8, 14), new SpinStop(8, 14));
        replaceTileAt(new Tile(8, 15), new Wall(8, 15, 14));
    }

    public WorldTile getWorldTileAt(int x, int y) {
        Tile tmpTile = new Tile(x, y);
        for(WorldTile tile : worldTiles)
            if(tile.positionEquals(tmpTile))
                return tile;

        return null;
    }

    private void replaceTileAt(Tile location, WorldTile newTile) {
        for(WorldTile tile : worldTiles)
            if(tile.positionEquals(location)) {
                replaceTile(tile, newTile);
                return;
            }
    }

    private void replaceTile(WorldTile tile, WorldTile newTile) {
        worldTiles.remove(tile);
        worldTiles.add(newTile);
    }

    public void doIntersections(Player player) {
        for(WorldTile tile : worldTiles)
            if(tile.positionEquals(player.tile))
                tile.onCollide(player);
    }

    public void render(GameContainer gameContainer, Graphics graphics) {
        for(WorldTile tile : worldTiles) {
            if(tile.getRenderer() != null) {
                int[] pos = tile.getRelativePosition();
                tile.getRenderer().draw(pos[0], pos[1]);
            }
        }
    }

}
