package main.java.com.lordmau5;

import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.menu.AbstractMenu;
import main.java.com.lordmau5.menu.MainMenu;
import main.java.com.lordmau5.util.*;
import main.java.com.lordmau5.util.Font;
import main.java.com.lordmau5.world.level.Level;
import main.java.com.lordmau5.world.level.LevelPack;
import main.java.com.lordmau5.world.tiles.*;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class WildRideGame extends BasicGame {

    public List<LevelPack> levelPacks = new ArrayList<>();

    private AbstractMenu menu;

    private Mode mode = null;

    public WildRideGame() {
        super("Giovanni's Wild Ride!");
    }

    Music music;
    Music fastMusic;
    boolean fastMode;
    boolean canWork = true;
    public boolean levelsLoading = true;
    LevelpackLoader loader;

    private void registerTiles() {
        TileRegistry.registerTile(Floor.class);
        TileRegistry.registerTile(Spin.class);
        TileRegistry.registerTile(SpinStop.class);
        TileRegistry.registerTile(Wall.class);
    }

    public void setMenu(AbstractMenu menu) {
        this.menu = menu;
    }

    private void saveDemoLVLPack(int max, int lvls) {
        for(int j=1; j<=max; j++) {
            List<Level> levels = new ArrayList<>();
            for (int i = 0; i < lvls; i++) {
                Level level = new Level("Level" + i);

                level.setStartPoint(4, 4);
                level.setEndPoint(1, 1);

                for (int x = 0; x < 32; x++)
                    for (int y = 0; y < 24; y++) {
                        level.replaceTileAt(x, y, Floor.class);
                    }

                level.setWall(0, 0, 10);
                for (int y = 1; y < 24; y++)
                    level.setWall(0, y, 11);
                for (int x = 1; x < 32; x++)
                    level.setWall(x, 0, 13);

                level.setSpin(4, 15, Direction.DOWN);
                level.setSpin(4, 16, Direction.RIGHT);
                level.setSpin(6, 16, Direction.DOWN);
                level.setSpin(6, 17, Direction.LEFT);
                level.setSpin(5, 17, Direction.UP);

                level.replaceTileAt(5, 15, SpinStop.class);
                levels.add(level);
            }
            LevelLoader.saveLevelPack(new LevelPack("LevelPack " + j, levels), "levels/LvlPack_" + j + ".lvlPack");
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        registerTiles();
        new Player(null);
        new Font();

        loader = new LevelpackLoader();

        //levelPacks = GenericUtil.getLevelPacks();

        //Level level = new Level("Demo");

        //saveDemoLVLPack(1, 1);
        //LevelPack pack = LevelLoader.loadLevelPack("levels/LvlPack1.lvlpack");
        //player.setLevel(level);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        if(levelsLoading) {
            LevelPack pack = loader.getNextLevelpack();
            levelPacks.add(pack);
            if(loader.getRemaining() == 0) {
                levelsLoading = false;
                setMenu(new MainMenu(false));
                mode = Mode.MENU;
            }
            return;
        }

        if(menu != null)
            menu.update(gameContainer, delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        if(levelsLoading) {
            String loading = "Loading...";
            graphics.scale(2f, 2f);
            int width = Font.getFont().getWidth(loading);
            Font.getFont().drawString(width - width / 8, 150, loading, Color.white);
            graphics.setColor(Color.cyan);
            graphics.drawRect(100, 200, 300, 20);
            width = 300 / loader.getTotal();
            graphics.fillRect(100, 200, width * (loader.getTotal() - loader.getRemaining()), 20);
            return;
        }

        if(menu != null)
            menu.render(gameContainer, graphics);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if(mode == Mode.MENU) {
            menu.onMousePress(button, x, y, true);
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if(mode == Mode.MENU) {
            menu.onMousePress(button, x, y, false);
        }

        if(mode != Mode.EDITOR)
            return;
        /*
        x = (int) Math.floor(x / 32);
        y = (int) Math.floor(y / 32);

        WorldTile tile = level.getWorldTileAt(x, y);
        if(tile != null) {
            if(tile instanceof Spin) {
                Spin spin = (Spin) tile;
                int type = spin.getDirection().ordinal() + 1;
                if(type >= Direction.values().length)
                    type = 0;


                spin.setDirection(Direction.values()[type]);
            }
        }
        */
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        super.mouseMoved(oldx, oldy, newx, newy);

        if(menu != null)
            menu.onMouseMove(newx, newy);
    }

    //-----------------------------------------------------------------------------------------
}
