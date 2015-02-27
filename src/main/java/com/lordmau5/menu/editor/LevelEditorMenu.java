package main.java.com.lordmau5.menu.editor;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.menu.AbstractMenu;
import main.java.com.lordmau5.world.Tile;
import main.java.com.lordmau5.world.tiles.Floor;
import main.java.com.lordmau5.world.tiles.WorldTile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelEditorMenu extends AbstractMenu {

    private final String levelpackName;
    private Map<Tile, WorldTile> positionSet = new HashMap<>();

    public LevelEditorMenu(String levelpackName) {
        this.levelpackName = levelpackName;

        for(int x=0; x<32; x++)
            for(int y=0; y<24; y++) {
                positionSet.put(new Tile(x, y), new Floor(x, y));
            }
    }

    public String getLevelpackName() {
        return levelpackName;
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        for(Map.Entry<Tile, WorldTile> entry : positionSet.entrySet()) {
            int[] pos = entry.getValue().getRelativePosition();
            entry.getValue().getImage().draw(pos[0], pos[1]);
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
