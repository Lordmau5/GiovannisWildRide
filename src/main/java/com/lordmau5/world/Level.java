package main.java.com.lordmau5.world;

import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.util.TileRegistry;
import main.java.com.lordmau5.world.tiles.Spin;
import main.java.com.lordmau5.world.tiles.Wall;
import main.java.com.lordmau5.world.tiles.WorldTile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class Level {

    private String levelName;
    private List<WorldTile> worldTiles = new ArrayList<>();
    private Tile startPoint;
    private Tile endPoint;

    public void setLevelName(String levelName) { this.levelName = levelName; }
    public String getLevelName() { return levelName; }

    public Tile getStartPoint() { return startPoint; }
    public Tile getEndPoint() { return endPoint; }

    public void setStartPoint(int x, int y) {
        startPoint = new Tile(x, y);
    }

    public void setEndPoint(int x, int y) {
        endPoint = new Tile(x, y);
    }

    public WorldTile getWorldTileAt(int x, int y) {
        Tile tmpTile = new Tile(x, y);
        for(WorldTile tile : worldTiles) {
            if (tmpTile.equals(tile))
                return tile;
        }
        return null;
    }

    public List<WorldTile> getWorldTiles() {
        return worldTiles;
    }

    public void loadTileAt(String[] split) {
        Tile location = new Tile(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        WorldTile newTile = TileRegistry.createInstance(location, split[2]);
        newTile.initiate(split);
        replaceTileAt(location.x, location.y, newTile);
    }

    public void replaceTileAt(int x, int y, WorldTile newTile) {
        Tile location = new Tile(x, y);
        if(!worldTiles.contains(location)) {
            worldTiles.add(newTile);
            return;
        }

        for(WorldTile tile : worldTiles)
            if(tile.equals(location)) {
                replaceTile(tile, newTile);
                return;
            }
    }

    public void replaceTileAt(int x, int y, Class<? extends WorldTile> tClass) {
        Tile location = new Tile(x, y);
        WorldTile newTile = TileRegistry.createInstance(location, tClass.getSimpleName());
        replaceTileAt(x, y, newTile);
    }

    public void setWall(int x, int y, int type) {
        Tile location = new Tile(x, y);
        Wall newTile = (Wall) TileRegistry.createInstance(location, Wall.class.getSimpleName());
        newTile.setType(type);
        replaceTileAt(x, y, newTile);
    }

    public void setSpin(int x, int y, Direction dr) {
        Tile location = new Tile(x, y);
        Spin newTile = (Spin) TileRegistry.createInstance(location, Spin.class.getSimpleName());
        newTile.setDirection(dr);
        replaceTileAt(x, y, newTile);
    }


    private void replaceTile(WorldTile tile, WorldTile newTile) {
        worldTiles.remove(tile);
        worldTiles.add(newTile);
    }

    public void doIntersections(Player player) {
        for(WorldTile tile : worldTiles)
            if(tile.equals(player.tile))
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
