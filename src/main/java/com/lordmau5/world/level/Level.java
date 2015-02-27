package main.java.com.lordmau5.world.level;

import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.util.TileRegistry;
import main.java.com.lordmau5.world.Tile;
import main.java.com.lordmau5.world.tiles.Floor;
import main.java.com.lordmau5.world.tiles.Spin;
import main.java.com.lordmau5.world.tiles.Wall;
import main.java.com.lordmau5.world.tiles.WorldTile;
import org.newdawn.slick.Color;
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

    public Level(String levelName) {
        this.levelName = levelName;

        for(int x=0; x<32; x++)
            for(int y=0; y<24; y++) {
                worldTiles.add(new Floor(x, y));
            }
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Level))
            return false;
        Level oLevel = (Level) obj;
        return oLevel.getLevelName().equals(getLevelName());
    }

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

    public Level getCopy() {
        Level level = new Level(getLevelName());
        level.setWorldTiles(getWorldTiles());
        int[] pos = getStartPoint().getAbsolutePosition();
        level.setStartPoint(pos[0], pos[1]);
        pos = getEndPoint().getAbsolutePosition();
        level.setEndPoint(pos[0], pos[1]);
        return level;
    }

    public WorldTile getWorldTileAt(int x, int y) {
        Tile tmpTile = new Tile(x, y);
        for(WorldTile tile : worldTiles) {
            if (tmpTile.equals(tile))
                return tile;
        }
        return null;
    }

    public void setWorldTiles(List<WorldTile> worldTiles) {
        this.worldTiles = worldTiles;
    }
    public List<WorldTile> getWorldTiles() {
        return worldTiles.subList(0, worldTiles.size());
    }

    public void loadTileAt(String[] split) {
        Tile location = new Tile(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        WorldTile newTile = TileRegistry.createInstance(location, split[2]);
        newTile.initiate(split);
        int[] pos = location.getAbsolutePosition();
        replaceTileAt(pos[0], pos[1], newTile);
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
        int[] endPos = getEndPoint().getAbsolutePosition();
        for(WorldTile tile : worldTiles) {
            if(tile.getRenderer() != null) {
                int[] pos = tile.getRelativePosition();
                int[] abs = tile.getAbsolutePosition();
                tile.getImage().draw(pos[0], pos[1], (abs[0] == endPos[0] && abs[1] == endPos[1]) ? Color.red : Color.white);
            }
        }
    }

}
