package com.lordmau5.giovanni.menu;

import com.lordmau5.giovanni.Main;
import com.lordmau5.giovanni.entity.Player;
import com.lordmau5.giovanni.util.Direction;
import com.lordmau5.giovanni.world.level.Level;
import com.lordmau5.giovanni.world.level.LevelPack;
import com.lordmau5.giovanni.world.tiles.WorldTile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class GameMenu extends AbstractMenu {

    private Player player;
    private LevelPack levelPack;
    private Level level;
    private int currentLevelId;

    public GameMenu(LevelPack levelPack) {
        player = new Player(this);
        this.levelPack = levelPack;

        this.level = levelPack.getFirstLevel();
        currentLevelId = 0;
        player.setLevel(this.level);
    }

    public Player getPlayer() {
        return player;
    }

    public void resetLevel() {
        this.level = levelPack.resetLevel(currentLevelId);
        for (WorldTile tile : level.getWorldTiles())
            tile.resetState();

        player.setLevel(level);
    }

    public void nextLevel() {
        this.level = levelPack.getNextLevel(currentLevelId);
        if (this.level == null) {
            Main.game.setMenu(new LevelpackList());
        } else {
            currentLevelId++;
            player.setLevel(this.level);
        }
    }

    @Override
    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
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

    private void movement(Input input) {
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

    private void otherInput(Input input) {
        if (input.isKeyPressed(Input.KEY_R)) {
            resetLevel();
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            getPlayer().pause();
            Main.game.setMenu(new PauseMenu(this));
        }
    }

    /*void otherInput(GameContainer container, int delta) {
        Input input = container.getInput();

        if(input.isKeyPressed(Input.KEY_P)) {
            if(fastMode) {
                if(!canWork)
                    return;

                float pos = fastMusic.getPosition();
                music.loop(1.0F, 0.1F);
                music.setPosition(pos);
                fastMode = false;
            }
            else {
                if(!canWork)
                    return;

                canWork = false;
                float pos = music.getPosition();
                fastMusic.loop(1.0F, 0.1F);
                fastMusic.setPosition(pos);
                new Timer().schedule(new TimerTask() {
                    int times = 50;

                    @Override
                    public void run() {
                        float pos = fastMusic.getPosition();
                        fastMusic.loop(1.0F + ((50 - times) * 0.01F), 0.1F);
                        fastMusic.setPosition(pos);

                        times--;
                        if(times == 0) {
                            this.cancel();
                            canWork = true;
                            fastMode = true;
                        }
                    }
                }, 0, 20);
            }
        }
        else if(input.isKeyPressed(Input.KEY_M)) {
            if(fastMode)
                if(fastMusic.playing())
                    fastMusic.pause();
                else
                    fastMusic.pause();
            else
            if(music.playing())
                music.pause();
            else
                music.pause();

        }
    }*/

}
