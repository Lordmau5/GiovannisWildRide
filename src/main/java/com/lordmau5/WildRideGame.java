package main.java.com.lordmau5;

import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.menu.AbstractMenu;
import main.java.com.lordmau5.menu.MainMenu;
import main.java.com.lordmau5.util.*;
import main.java.com.lordmau5.world.Level;
import main.java.com.lordmau5.world.tiles.*;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class WildRideGame extends BasicGame {

    private List<Level> levels = new ArrayList<>();

    private AbstractMenu menu;
    private Player player;
    private Level level;

    private Mode mode = Mode.MENU;

    public WildRideGame() {
        super("Giovanni's Wild Ride!");
    }

    Music music;
    Music fastMusic;
    boolean fastMode;
    boolean canWork = true;

    private void registerTiles() {
        TileRegistry.registerTile(Floor.class);
        TileRegistry.registerTile(Spin.class);
        TileRegistry.registerTile(SpinStop.class);
        TileRegistry.registerTile(Wall.class);
    }

    public void setMenu(AbstractMenu menu) {
        this.menu = menu;
    }

    private void saveDemoMap() {
        level = new Level();

        level.setStartPoint(4, 4);
        level.setEndPoint(1, 1);

        for(int x=0; x<32; x++)
            for(int y=0;y<24;y++) {
                level.replaceTileAt(x, y, Floor.class);
            }

        level.setWall(0, 0, 10);
        for(int y=1; y<24; y++)
            level.setWall(0, y, 11);
        for(int x=1; x<32; x++)
            level.setWall(x, 0, 13);

        level.setSpin(4, 15, Direction.DOWN);
        level.setSpin(4, 16, Direction.RIGHT);
        level.setSpin(6, 16, Direction.DOWN);
        level.setSpin(6, 17, Direction.LEFT);
        level.setSpin(5, 17, Direction.UP);

        level.replaceTileAt(5, 15, SpinStop.class);

        LevelLoader.saveLevel(level, "testMap1");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        registerTiles();

        player = new Player();

        levels = GenericUtil.getLevels();

        Level level = new Level();

        //saveDemoMap();
        //map = MapLoader.loadMap("testMap1");
        player.setLevel(level);

        setMenu(new MainMenu(false));
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        if(mode == Mode.GAME) {
            movement(gameContainer, delta);
            player.update();
            otherInput(gameContainer, delta);
        }
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
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        super.mouseMoved(oldx, oldy, newx, newy);

        menu.onMouseMove(newx, newy);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        if(mode == Mode.GAME) {
            level.render(gameContainer, graphics);

            int[] pos = player.getRelativePosition();
            player.getRenderer().draw(pos[0] + 2, pos[1] - 4);
        }
        else if(mode == Mode.MENU) {
            menu.render(gameContainer, graphics);
        }
    }

    //-----------------------------------------------------------------------------------------

    void movement(GameContainer container, int delta) {
        Input input = container.getInput();

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

        player.move(dr, delta);
    }

    void otherInput(GameContainer container, int delta) {
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
    }
}
