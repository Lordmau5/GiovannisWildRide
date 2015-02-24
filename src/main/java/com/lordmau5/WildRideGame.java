package main.java.com.lordmau5;

import main.java.com.lordmau5.entity.Entity;
import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.Direction;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class WildRideGame extends BasicGame {

    private List<Entity> drawnEntities = new ArrayList<>();

    private Player player;

    public WildRideGame() {
        super("Giovanni's Wild Ride!");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        player = new Player();
        drawnEntities.add(player);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        movement(gameContainer, delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        for(Entity ent : drawnEntities) {
            if(ent.getRenderer() != null) {
                float[] pos = ent.getPosition();
                ent.getRenderer().draw(pos[0], pos[1]);
            }
        }
    }

    //-----------------------------------------------------------------------------------------

    void movement(GameContainer container, int delta) {
        Input input = container.getInput();

        if(input.isKeyDown(Input.KEY_UP)) {
            player.move(Direction.UP, delta);
        }
        else if(input.isKeyDown(Input.KEY_DOWN)) {
            player.move(Direction.DOWN, delta);
        }
        else if(input.isKeyDown(Input.KEY_LEFT)) {
            player.move(Direction.LEFT, delta);
        }
        else if(input.isKeyDown(Input.KEY_RIGHT)) {
            player.move(Direction.RIGHT, delta);
        }
        else
            player.stopMoving();
    }
}
