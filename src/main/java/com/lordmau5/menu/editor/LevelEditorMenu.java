package main.java.com.lordmau5.menu.editor;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.menu.AbstractMenu;
import main.java.com.lordmau5.util.Constants;
import main.java.com.lordmau5.util.LevelLoader;
import main.java.com.lordmau5.util.TileRegistry;
import main.java.com.lordmau5.world.level.Level;
import main.java.com.lordmau5.world.level.LevelPack;
import main.java.com.lordmau5.world.tiles.Floor;
import main.java.com.lordmau5.world.tiles.StartEndPoint;
import main.java.com.lordmau5.world.tiles.WorldTile;
import org.newdawn.slick.*;

import java.util.List;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelEditorMenu extends AbstractMenu {

    private final LevelPack levelPack;
    private Level currentLevel;
    private int levelId = 1;
    private final String levelpackName;
    private List<WorldTile> tileMap;
    private int selected = 0;
    private boolean menuShowing = true;
    private boolean menuRight = true;

    private Input input;

    public LevelEditorMenu(String levelpackName) {
        this.tileMap = TileRegistry.getTileMap();

        if(LevelLoader.doesLevelPackExist("levels/" + levelpackName + ".lvlPack")) {
            System.out.println("Exists");
            this.levelPack = LevelLoader.loadLevelPack("levels/" + levelpackName + ".lvlPack");
            this.levelpackName = this.levelPack.getName();
            this.currentLevel = this.levelPack.getFirstLevel();
        }
        else {
            this.levelPack = new LevelPack(levelpackName);
            this.currentLevel = new Level(Constants.NOT_SET);
            levelPack.addLevel(currentLevel);
            this.levelpackName = levelpackName;

            for(int x=0; x<32; x++)
                for(int y=0; y<24; y++) {
                    currentLevel.replaceTileAt(x, y, Floor.class);
                }
        }
    }

    public int getCurrentLevelId() {
        return levelId;
    }

    public int getLevels() {
        return levelPack.getLevels().size();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void switchLevel(int nextId) {
        if(nextId <= levelPack.getLevels().size()) {
            currentLevel = levelPack.getLevels().get(nextId - 1);
            levelId = nextId;
            if(nextId == getLevels() - 1) {
                Level level = levelPack.getLevels().get(getLevels() - 1);
                if(level.getLevelName().equals(Constants.NOT_SET))
                    levelPack.getLevels().remove(level);
            }
            return;
        }
        else {
            Level level = new Level(Constants.NOT_SET);
            if(!levelPack.getLevels().get(getLevels() - 1).getLevelName().equals(Constants.NOT_SET) || levelId < getLevels()) {
                levelPack.addLevel(level);
                currentLevel = level;
                levelId = nextId;
            }
        }
    }

    public boolean saveLevelpack() {
        for(Level level : levelPack.getLevels()) {
            if(level.getStartPoint() == null || level.getEndPoint() == null)
                return false;
        }
        LevelLoader.saveLevelPack(levelPack, "levels/" + levelpackName + ".lvlPack");
        return true;
    }

    public String getLevelpackName() {
        return levelpackName;
    }

    @Override
    public void init(GameContainer gameContainer) {
        super.init(gameContainer);

        input = gameContainer.getInput();
    }

    private boolean isInsideTileSelection(int x, int y) {
        if(!menuShowing)
            return false;

        int startX = menuRight ? Main.width - 200 : 10;
        if(x >= startX && x <= startX + 192) {
            return true;
        }
        return false;
    }

    private void handleTileSelection(int x, int y) {
        if(!menuShowing)
            return;

        int menuX = menuRight ? Main.width - 194 : 0;

        int height = (int) Math.ceil(tileMap.size() / 5);
        if (tileMap.size() % 5 > 0)
            height += 1;
        for (int iX = 0; iX < 5; iX++) {
            for (int iY = 0; iY < height; iY++) {
                if ((iY * 5) + iX >= tileMap.size())
                    break;

                if(x >= menuX + 8 + iX * 32 + (5 * iX) && x <= menuX + 8 + (iX + 1) * 32 + (5 * iX) && y >= iY * 32 + 8 + (5 * iY) && y <= (iY + 1) * 32 + 8 + (5 * iY)) {
                    selected = iY * 5 + iX;
                    break;

                }
            }
        }
    }

    @Override
    public void onMouseDragged(int x, int y, int lastMouseButton) {
        super.onMouseDragged(x, y, lastMouseButton);

        if(lastMouseButton != 0)
            return;

        if(!isInsideTileSelection(x, y)) {
            int tileX = (int) Math.floor(x / 32);
            int tileY = (int) Math.floor(y / 32);

            if(selected == -1)
                return;

            WorldTile worldTile = tileMap.get(selected).copyTile();
            worldTile.setPosition(tileX, tileY);
            if(worldTile instanceof StartEndPoint) {
                StartEndPoint pt = (StartEndPoint) worldTile;
                if(pt.isStart()) {
                    pt = currentLevel.getStartPoint();
                    if(pt != null) {
                        int[] pos = pt.getAbsolutePosition();
                        currentLevel.replaceTileAt(pos[0], pos[1], Floor.class);
                    }
                    currentLevel.setStartPoint(tileX, tileY);
                }
                else {
                    pt = currentLevel.getEndPoint();
                    if(pt != null) {
                        int[] pos = pt.getAbsolutePosition();
                        currentLevel.replaceTileAt(pos[0], pos[1], Floor.class);
                    }
                    currentLevel.setEndPoint(tileX, tileY);
                }
            }
            else {
                currentLevel.replaceTileAt(tileX, tileY, worldTile);
            }
        }
    }

    @Override
    public void onMousePress(int buttonId, int x, int y, boolean press) {
        super.onMousePress(buttonId, x, y, press);

        if(!press)
            return;

        if(buttonId == 1) {
            if(input.isKeyDown(Input.KEY_LSHIFT))
                menuRight = !menuRight;
            else
                menuShowing = !menuShowing;
            return;
        }

        if(isInsideTileSelection(x, y))
            if(buttonId == 2)
                selected = -1;

        if(buttonId == 2)
            return;

        handleTileSelection(x, y);

        if(!isInsideTileSelection(x, y)) {
            int tileX = (int) Math.floor(x / 32);
            int tileY = (int) Math.floor(y / 32);

            if(selected == -1)
                return;

            WorldTile worldTile = tileMap.get(selected).copyTile();
            worldTile.setPosition(tileX, tileY);
            if(worldTile instanceof StartEndPoint) {
                StartEndPoint pt = (StartEndPoint) worldTile;
                if(pt.isStart()) {
                    pt = currentLevel.getStartPoint();
                    if(pt != null) {
                        int[] pos = pt.getAbsolutePosition();
                        currentLevel.replaceTileAt(pos[0], pos[1], Floor.class);
                    }
                    currentLevel.setStartPoint(tileX, tileY);
                }
                else {
                    pt = currentLevel.getEndPoint();
                    if(pt != null) {
                        int[] pos = pt.getAbsolutePosition();
                        currentLevel.replaceTileAt(pos[0], pos[1], Floor.class);
                    }
                    currentLevel.setEndPoint(tileX, tileY);
                }
            }
            else {
                currentLevel.replaceTileAt(tileX, tileY, worldTile);
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        for(WorldTile tile : currentLevel.getWorldTiles()) {
            if(tile == null || tile.getRenderer() == null)
                continue;

            int[] pos = tile.getRelativePosition();
            Renderable renderer = tile.getImage(); //tile instanceof StartEndPoint ? tile.getRenderer() : tile.getImage();
            renderer.draw(pos[0], pos[1]);
        }

        // Draw Start and End point
        {
            int[] pos;
            StartEndPoint pt = currentLevel.getStartPoint();
            if(pt != null) {
                pos = pt.getRelativePosition();
                pt.getImage().draw(pos[0], pos[1]);
            }
            pt = currentLevel.getEndPoint();
            if(pt != null) {
                pos = pt.getRelativePosition();
                pt.getImage().draw(pos[0], pos[1]);
            }

        }

        if(menuShowing) {
            int menuX = menuRight ? Main.width - 194 : 8;
            graphics.setColor(new Color(1f, 1f, 1f, 0.75f));
            graphics.fillRect(menuX, 0, 194, Main.height);

            int height = (int) Math.ceil(tileMap.size() / 5);
            if (tileMap.size() % 5 > 0)
                height += 1;
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < height; y++) {
                    if ((y * 5) + x >= tileMap.size())
                        break;

                    Color color = Color.white;
                    if ((y * 5) + x == selected) {
                        color = Color.green; //new Color(1f, 0.8f, 1f, 1f);
                    }
                    graphics.drawImage(tileMap.get((y * 5) + x).getImage(), menuX + 8 + x * 32 + (5 * x), y * 32 + 8 + (5 * y), color);
                }
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if(input.isKeyPressed(Input.KEY_ESCAPE)) {
            Main.game.setMenu(new LevelEditorPauseMenu(this));
        }
    }

}
