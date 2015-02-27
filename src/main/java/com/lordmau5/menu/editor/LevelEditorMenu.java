package main.java.com.lordmau5.menu.editor;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.menu.AbstractMenu;
import main.java.com.lordmau5.util.TileRegistry;
import main.java.com.lordmau5.world.tiles.Floor;
import main.java.com.lordmau5.world.tiles.StartEndPoint;
import main.java.com.lordmau5.world.tiles.WorldTile;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelEditorMenu extends AbstractMenu {

    private final String levelpackName;
    private List<WorldTile> tilePositions = new ArrayList<>();
    private List<WorldTile> tileMap;
    private int selected = 0;
    private boolean menuShowing = true;
    private boolean menuRight = true;

    private Input input;

    public LevelEditorMenu(String levelpackName) {
        this.levelpackName = levelpackName;
        this.tileMap = TileRegistry.getTileMap();

        for(int x=0; x<32; x++)
            for(int y=0; y<24; y++) {
                tilePositions.add(new Floor(x, y));
            }
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

        int startX = menuRight ? Main.width - 170 : 10;
        if(x >= startX && x <= startX + 160) {
            return true;
        }
        return false;
    }

    private void handleTileSelection(int x, int y) {
        if(!menuShowing)
            return;

        int startX = menuRight ? Main.width - 170 : 10;
        if(x >= startX && x <= startX + 160) {
            int rowCount = (int) Math.ceil(tileMap.size() / 5);
            if(rowCount % 5 > 0)
                rowCount++;

            if(y >= 10 && y <= rowCount * 32 + 10) {
                x -= startX;
                y -= 10;
                int column = (int) Math.floor(x / 32);
                int row = (int) Math.floor(y / 32);

                selected = column + row * 5;
                if(selected >= tileMap.size())
                    selected = -1;
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
            for(WorldTile tile : tilePositions) {
                int[] pos = tile.getAbsolutePosition();
                if(pos[0] == tileX && pos[1] == tileY) {
                    tilePositions.remove(tile);
                    break;
                }
            }
            if(worldTile instanceof StartEndPoint) {
                for(WorldTile tile : tilePositions)
                    if(tile instanceof StartEndPoint) {
                        StartEndPoint pt = (StartEndPoint) tile;
                        if(pt.isStart() != ((StartEndPoint) worldTile).isStart())
                            continue;

                        int[] pos = tile.getAbsolutePosition();
                        tilePositions.remove(tile);
                        tilePositions.add(new Floor(pos[0], pos[1]));
                        break;
                    }
            }
            tilePositions.add(worldTile);
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
            for(WorldTile tile : tilePositions) {
                int[] pos = tile.getAbsolutePosition();
                if(pos[0] == tileX && pos[1] == tileY) {
                    tilePositions.remove(tile);
                    break;
                }
            }
            if(worldTile instanceof StartEndPoint) {
                for(WorldTile tile : tilePositions)
                    if(tile instanceof StartEndPoint) {
                        StartEndPoint pt = (StartEndPoint) tile;
                        if(pt.isStart() != ((StartEndPoint) worldTile).isStart())
                            continue;

                        int[] pos = tile.getAbsolutePosition();
                        tilePositions.remove(tile);
                        tilePositions.add(new Floor(pos[0], pos[1]));
                        break;
                    }
            }
            tilePositions.add(worldTile);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        for(WorldTile tile : tilePositions) {
            if(tile == null || tile.getRenderer() == null)
                continue;

            int[] pos = tile.getRelativePosition();
            Renderable renderer = tile.getImage(); //tile instanceof StartEndPoint ? tile.getRenderer() : tile.getImage();
            renderer.draw(pos[0], pos[1]);
        }

        if(menuShowing) {
            int menuX = menuRight ? Main.width - 180 : 0;
            graphics.setColor(new Color(1f, 1f, 1f, 0.75f));
            graphics.fillRect(menuX, 0, 180, Main.height);

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
                    graphics.drawImage(tileMap.get((y * 5) + x).getImage(), menuX + 10 + x * 32, y * 32 + 10, color);
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
