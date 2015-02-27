package main.java.com.lordmau5.entity;

import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.util.ImageLoader;
import main.java.com.lordmau5.world.level.Level;
import main.java.com.lordmau5.world.Tile;
import main.java.com.lordmau5.world.tiles.WorldTile;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class Player implements Entity {

    private int x, y;
    public Tile tile;
    private Tile lastSpinTile;
    private Tile walkTile;

    private Level level;

    private Direction facing = Direction.DOWN;

    public static Animation[] iFacing = new Animation[4];
    public static  Animation spinAnim;
    private Animation anim;

    private int movement = 2;
    private boolean moving;
    public boolean holdingMove;
    private boolean spinning;
    private Direction spinDirection;

    public Player() {
        for (Direction dr : Direction.values()) {
            iFacing[dr.ordinal()] = new Animation(new SpriteSheet(ImageLoader.loadImage("player/" + dr.name().toLowerCase() + ".png"), 28, 32), 140);
        }
        spinAnim = new Animation(new Image[]{iFacing[0].getImage(0),iFacing[1].getImage(0),iFacing[2].getImage(0),iFacing[3].getImage(0)}, 140);

        x = 1;
        y = 1;

        tile = new Tile(1, 1);

        anim = iFacing[facing.ordinal()];
        anim.setAutoUpdate(false);
    }

    public void setLevel(Level level) {
        this.level = level;
        if(level.getStartPoint() != null)
            setPosition(level.getStartPoint());
    }

    private void setPosition(Tile tile) {
        this.tile = tile;
        int[] pos = tile.getAbsolutePosition();
        this.x = pos[0] * 32;
        this.y = pos[1] * 32;
    }

    public boolean isMoving() {
        return moving;
    }

    private boolean canWalkTo(int x, int y) {
        WorldTile tile = level.getWorldTileAt(x, y);
        if(tile == null)
            return false;

        return tile.canPassThrough(this);
    }

    public void move(Direction dr, int delta) {
        if(spinning)
            return;

        facing = dr;
        anim = iFacing[facing.ordinal()];

        int mX = 0, mY = 0;

        if (dr == Direction.UP) {
            mY = -1;
        } else if (dr == Direction.DOWN) {
            mY = 1;
        } else if (dr == Direction.LEFT) {
            mX = -1;
        } else if (dr == Direction.RIGHT) {
            mX = 1;
        }

        int[] pos = tile.getAbsolutePosition();
        if(!canWalkTo(pos[0] + mX, pos[1] + mY)) {
            anim.setCurrentFrame(0);
            return;
        }

        if(!holdingMove)
            anim.setCurrentFrame(0);
        anim.setAutoUpdate(false);

        anim.setAutoUpdate(true);

        walkTile = new Tile(pos[0] + mX, pos[1] + mY);

        moving = true;
    }

    public void update() {
        doSpinning();
        doMovement();
    }

    private void doMovement() {
        if(!isMoving())
            return;

        int[] pos = walkTile.getAbsolutePosition();
        if(y > pos[1] * 32 && facing == Direction.UP)
            y -= 2;
        if(y < pos[1] * 32 && facing == Direction.DOWN)
            y += 2;
        if(x > pos[0] * 32 && facing == Direction.LEFT)
            x -= 2;
        if(x < pos[0] * 32 && facing == Direction.RIGHT)
            x += 2;

        // CHECK
        if(x == pos[0] * 32 && y == pos[1] * 32) { // FINISH
            x = pos[0] * 32;
            y = pos[1] * 32;

            this.tile.setPosition(pos[0], pos[1]);
            stopMoving();
            level.doIntersections(this);
        }
    }

    private void doSpinning() {
        if(!isSpinning())
            return;

        level.doIntersections(this);

        int[] pos = walkTile.getAbsolutePosition();
        if(y > pos[1] * 32 && spinDirection == Direction.UP)
            y -= 2;
        if(y < pos[1] * 32 && spinDirection == Direction.DOWN)
            y += 2;
        if(x > pos[0] * 32 && spinDirection == Direction.LEFT)
            x -= 2;
        if(x < pos[0] * 32 && spinDirection == Direction.RIGHT)
            x += 2;

        // CHECK
        if(x == pos[0] * 32 && y == pos[1] * 32) { // FINISH
            x = pos[0] * 32;
            y = pos[1] * 32;

            this.tile.setPosition(pos[0], pos[1]);

            // SET NEW TILE
            int mX = 0, mY = 0;

            if (spinDirection == Direction.UP) {
                mY = -1;
            } else if (spinDirection == Direction.DOWN) {
                mY = 1;
            } else if (spinDirection == Direction.LEFT) {
                mX = -1;
            } else if (spinDirection == Direction.RIGHT) {
                mX = 1;
            }

            pos = tile.getAbsolutePosition();
            if(!canWalkTo(pos[0] + mX, pos[1] + mY)) {
                stopSpinning();
            }
            else {
                walkTile = new Tile(pos[0] + mX, pos[1] + mY);
            }
        }
    }

    public void stopMoving() {
        this.moving = false;
        anim.setAutoUpdate(false);
        if(!holdingMove)
            anim.setCurrentFrame(0);
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void startSpinning(Tile tile, Direction direction) {
        if(spinning && direction == spinDirection)
            return;

        if(this.lastSpinTile != null && tile.equals(this.lastSpinTile))
            return;

        this.lastSpinTile = tile.copyTile();

        spinDirection = direction;
        if(!spinning) {
            spinAnim.setCurrentFrame(facing.ordinal());
        }
        spinning = true;

        int mX = 0, mY = 0;

        if (spinDirection == Direction.UP) {
            mY = -1;
        } else if (spinDirection == Direction.DOWN) {
            mY = 1;
        } else if (spinDirection == Direction.LEFT) {
            mX = -1;
        } else if (spinDirection == Direction.RIGHT) {
            mX = 1;
        }

        int[] pos = tile.getAbsolutePosition();
        if(!canWalkTo(pos[0] + mX, pos[1] + mY)) {
            stopSpinning();
        }
        else {
            walkTile = new Tile(pos[0] + mX, pos[1] + mY);
        }
    }

    public void stopSpinning() {
        if(!spinning)
            return;

        spinning = false;
        spinDirection = null;
        facing = Direction.values()[spinAnim.getFrame()];
        anim = iFacing[facing.ordinal()];
        anim.setCurrentFrame(0);
        anim.setAutoUpdate(false);
    }

    public Direction getFacing() {
        return facing;
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        if(entity instanceof WorldTile)
            return entity.canPassThrough(this);

        return true;
    }

    @Override
    public Renderable getRenderer() {
        if(spinning)
            return spinAnim;
        return anim;
    }

    @Override
    public int[] getRelativePosition() {
        return new int[]{x, y};
    }
}
