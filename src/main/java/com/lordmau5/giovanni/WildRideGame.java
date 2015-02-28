package com.lordmau5.giovanni;

import com.lordmau5.giovanni.entity.Player;
import com.lordmau5.giovanni.menu.AbstractMenu;
import com.lordmau5.giovanni.menu.MainMenu;
import com.lordmau5.giovanni.util.*;
import com.lordmau5.giovanni.util.Font;
import com.lordmau5.giovanni.world.level.Level;
import com.lordmau5.giovanni.world.level.LevelPack;
import com.lordmau5.giovanni.world.tiles.*;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class WildRideGame extends BasicGame {

    public List<LevelPack> levelPacks = new ArrayList<>();
    public boolean levelsLoading = true;
    LevelpackLoader loader;
    private AbstractMenu menu;
    private Mode mode = null;
    private GameContainer gameContainer;
    private int lastMouseButton;

    public WildRideGame() {
        super("Giovanni's Wild Ride!");
    }

    private void registerTiles() {
        TileRegistry.registerTile(Floor.class);
        TileRegistry.registerTile(Spin.class);
        TileRegistry.registerTile(SpinStop.class);
        TileRegistry.registerTile(Wall.class);
        TileRegistry.registerTile(StartEndPoint.class);

        TileRegistry.registerTileVariations();
    }

    public void setMenu(AbstractMenu menu) {
        this.menu = menu;
        menu.init(gameContainer);
    }

    private void saveDemoLVLPack(int max, int lvls) {
        for (int j = 1; j <= max; j++) {
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

        this.gameContainer = gameContainer;

        loader = new LevelpackLoader();

        //levelPacks = GenericUtil.getLevelPacks();

        //Level level = new Level("Demo");

        //saveDemoLVLPack(1, 1);
        //LevelPack pack = LevelLoader.loadLevelPack("levels/LvlPack1.lvlpack");
        //player.setLevel(level);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        if (levelsLoading) {
            LevelPack pack = loader.getNextLevelpack();
            if (pack != null)
                levelPacks.add(pack);

            if (loader.getRemaining() == 0) {
                levelsLoading = false;
                setMenu(new MainMenu(false));
                mode = Mode.MENU;
            }
            return;
        }

        if (menu != null)
            menu.update(gameContainer, delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        if (levelsLoading) {
            String loading = "Loading...";
            graphics.scale(2f, 2f);
            int width = Font.getFont().getWidth(loading);
            Font.getFont().drawString(width - width / 8, 150, loading, Color.white);
            graphics.setColor(Color.cyan);
            graphics.drawRect(100, 200, 300, 20);
            if (loader.getTotal() == 0)
                return;

            width = 300 / loader.getTotal();
            graphics.fillRect(100, 200, width * (loader.getTotal() - loader.getRemaining()), 20);
            return;
        }

        if (menu != null)
            menu.render(gameContainer, graphics);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        lastMouseButton = button;

        if (mode == Mode.MENU) {
            menu.onMousePress(button, x, y, true);
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (mode == Mode.MENU) {
            menu.onMousePress(button, x, y, false);
        }

        if (mode != Mode.EDITOR)
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

        if (menu != null)
            menu.onMouseMove(newx, newy);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);

        if (menu != null)
            menu.onMouseClicked(button, x, y, clickCount);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        super.mouseDragged(oldx, oldy, newx, newy);

        if (menu != null)
            menu.onMouseDragged(newx, newy, lastMouseButton);
    }

    //-----------------------------------------------------------------------------------------
}
