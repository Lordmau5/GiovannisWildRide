package main.java.com.lordmau5.menu;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.button.IButton;
import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.world.level.Level;
import main.java.com.lordmau5.world.level.LevelPack;
import main.java.com.lordmau5.world.tiles.Spin;
import main.java.com.lordmau5.world.tiles.WorldTile;
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

    public void resetLevel() {
        this.level = levelPack.resetLevel(currentLevelId);
        for(WorldTile tile : level.getWorldTiles())
            tile.resetState();

        player.setLevel(level);
    }

    public void nextLevel() {
        this.level = levelPack.getNextLevel(currentLevelId);
        if(this.level == null) {
            Main.game.setMenu(new LevelpackList());
        }
        else {
            currentLevelId++;
            player.setLevel(this.level);
        }
    }

    @Override
    public void onMousePress(int buttonId, int x, int y, boolean press) {
        if(buttonId != 0 || !press)
            return;

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
    }

    @Override
    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        if(level != null)
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

        if(input.isKeyDown(Input.KEY_UP)) {
            dr = Direction.UP;
        }
        else if(input.isKeyDown(Input.KEY_DOWN)) {
            dr = Direction.DOWN;
        }
        else if(input.isKeyDown(Input.KEY_LEFT)) {
            dr = Direction.LEFT;
        }
        else if(input.isKeyDown(Input.KEY_RIGHT)) {
            dr = Direction.RIGHT;
        }

        if(dr == null) {
            player.holdingMove = false;
            return;
        }

        player.holdingMove = true;

        if(player.isMoving())
            return;

        player.move(dr);
    }

    private void otherInput(Input input) {
        if(input.isKeyPressed(Input.KEY_R)) {
            resetLevel();
        }
        else if(input.isKeyPressed(Input.KEY_ESCAPE)) {
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

    @Override
    public IButton getButton(int x, int y) {
        return super.getButton(x, y);
    }

}
