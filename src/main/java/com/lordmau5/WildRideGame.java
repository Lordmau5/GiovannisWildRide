package main.java.com.lordmau5;

import main.java.com.lordmau5.entity.Entity;
import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.world.Map;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class WildRideGame extends BasicGame {

    private List<Entity> drawnEntities = new ArrayList<>();

    private Player player;
    private Map map;

    public WildRideGame() {
        super("Giovanni's Wild Ride!");
    }

    Music music;
    Music fastMusic;
    boolean fastMode;
    boolean canWork = true;

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        map = new Map();

        player = new Player();
        drawnEntities.add(player);

        music = new Music("music.ogg");
        fastMusic = new Music("music.ogg");
        //music.loop(1.0F, 0.1F);

        player.setMap(map);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        movement(gameContainer, delta);
        player.update();
        otherInput(gameContainer, delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        map.render(gameContainer, graphics);

        int[] pos = player.getRelativePosition();
        player.getRenderer().draw(pos[0] + 2, pos[1] - 4);
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
            if(!music.playing() && !fastMusic.playing())
                return;

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
