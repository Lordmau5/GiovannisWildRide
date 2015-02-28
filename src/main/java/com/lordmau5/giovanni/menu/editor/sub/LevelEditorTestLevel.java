package com.lordmau5.giovanni.menu.editor.sub;

import com.lordmau5.giovanni.Main;
import com.lordmau5.giovanni.entity.Player;
import com.lordmau5.giovanni.menu.AbstractMenu;
import com.lordmau5.giovanni.menu.INextLevelMenu;
import com.lordmau5.giovanni.menu.editor.LevelEditorPauseMenu;
import com.lordmau5.giovanni.util.Direction;
import com.lordmau5.giovanni.world.Tile;
import com.lordmau5.giovanni.world.level.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by Lordmau5 on 28.02.2015.
 */
public class LevelEditorTestLevel extends AbstractMenu implements INextLevelMenu {

    private LevelEditorPauseMenu pauseMenu;
    private Level level;
    private int[] startPos;
    private Player player;

    public LevelEditorTestLevel(LevelEditorPauseMenu pauseMenu, Level level) {
        this.pauseMenu = pauseMenu;
        this.level = level.getCopy();
        this.player = new Player(this);
        startPos = level.getStartPoint().getAbsolutePosition();
        this.player.setLevel(this.level);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        if (level != null)
            level.render(gameContainer, graphics);

        int[] pos = player.getRelativePosition();
        player.getRenderer().draw(pos[0] + 2, pos[1] - 4);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();
        movement(input);
        player.update();
        otherInput(input);
    }

    public void movement(Input input) {
        Direction dr = null;

        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
            dr = Direction.UP;
        } else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            dr = Direction.DOWN;
        } else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
            dr = Direction.LEFT;
        } else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            dr = Direction.RIGHT;
        }

        if (dr == null) {
            player.holdingMove = false;
            return;
        }

        player.holdingMove = true;

        if (player.isMoving())
            return;

        player.move(dr);
    }

    public void otherInput(Input input) {
        if (input.isKeyPressed(Input.KEY_R)) {
            player.setPosition(new Tile(startPos[0], startPos[1]));
            player.resetVars();
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            Main.game.setMenu(pauseMenu);
        }
    }

    @Override
    public void nextLevel() {
        Main.game.setMenu(pauseMenu);
    }
}
