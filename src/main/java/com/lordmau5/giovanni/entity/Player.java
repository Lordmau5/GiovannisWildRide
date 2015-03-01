package com.lordmau5.giovanni.entity;

import com.lordmau5.giovanni.menu.INextLevelMenu;
import com.lordmau5.giovanni.util.Direction;
import com.lordmau5.giovanni.util.ImageLoader;
import com.lordmau5.giovanni.world.Tile;
import com.lordmau5.giovanni.world.level.Level;
import com.lordmau5.giovanni.world.tiles.WorldTile;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class Player implements Entity {

    public static Animation[] iFacing = new Animation[4];
    public static Animation spinAnim;
    public Tile tile;
    public boolean holdingMove;
    public int x, y;
    private Tile lastTile;
    private Tile walkTile;
    private Level level;
    private Direction facing = Direction.DOWN;
    public Animation anim;
    private boolean moving;
    private boolean spinning;
    private Direction spinDirection;

    private INextLevelMenu nextLevelMenu;

    public Player(INextLevelMenu nextLevelMenu) {
        this.nextLevelMenu = nextLevelMenu;

        for (Direction dr : Direction.values()) {
            iFacing[dr.ordinal()] = new Animation(new SpriteSheet(ImageLoader.loadImage("player/" + dr.name().toLowerCase() + ".png"), 28, 32), 140);
        }
        spinAnim = new Animation(new Image[]{iFacing[0].getImage(0), iFacing[1].getImage(0), iFacing[2].getImage(0), iFacing[3].getImage(0)}, 140);

        x = 1;
        y = 1;

        tile = new Tile(1, 1);

        anim = iFacing[facing.ordinal()];
        anim.setAutoUpdate(false);
    }

    public void resetVars() {
        facing = Direction.DOWN;
        anim = iFacing[facing.ordinal()];
        anim.setAutoUpdate(false);
        anim.setCurrentFrame(0);
        moving = false;
        holdingMove = false;
        spinning = false;
        walkTile = null;
    }

    public void setLevel(Level level) {
        this.level = level;
        if (level.getStartPoint() != null) {
            setPosition(level.getStartPoint());
        }
        resetVars();
    }

    public void pause() {
        anim.setAutoUpdate(false);
    }

    public void unpause() {
        anim.setAutoUpdate(isMoving());
    }

    public void setPosition(Tile tile) {
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
        if (tile == null) {
            if(x >= 0 && x < 32 && y >= 0 && y < 24)
                return true;

            return false;
        }

        return tile.canPassThrough(this);
    }

    public void move(Direction dr) {
        if (spinning)
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
        if (!canWalkTo(pos[0] + mX, pos[1] + mY)) {
            anim.setCurrentFrame(0);
            return;
        }

        if (!holdingMove)
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
        if (!isMoving())
            return;

        int[] pos = walkTile.getAbsolutePosition();
        if (y > pos[1] * 32 && facing == Direction.UP)
            y -= 2;
        if (y < pos[1] * 32 && facing == Direction.DOWN)
            y += 2;
        if (x > pos[0] * 32 && facing == Direction.LEFT)
            x -= 2;
        if (x < pos[0] * 32 && facing == Direction.RIGHT)
            x += 2;

        // CHECK
        if (x == pos[0] * 32 && y == pos[1] * 32) { // FINISH
            x = pos[0] * 32;
            y = pos[1] * 32;

            this.lastTile = this.tile.copyTile();
            this.tile.setPosition(pos[0], pos[1]);
            if (this.tile.equals(level.getEndPoint())) {
                nextLevelMenu.nextLevel();
                return;
            }
            stopMoving();
            level.doIntersections(this);
        }
    }

    private void doSpinning() {
        if (!isSpinning())
            return;

        level.doIntersections(this);

        int[] pos = walkTile.getAbsolutePosition();
        if (y > pos[1] * 32 && spinDirection == Direction.UP)
            y -= 2;
        if (y < pos[1] * 32 && spinDirection == Direction.DOWN)
            y += 2;
        if (x > pos[0] * 32 && spinDirection == Direction.LEFT)
            x -= 2;
        if (x < pos[0] * 32 && spinDirection == Direction.RIGHT)
            x += 2;

        // CHECK
        if (x == pos[0] * 32 && y == pos[1] * 32) { // FINISH
            x = pos[0] * 32;
            y = pos[1] * 32;

            this.lastTile = this.tile.copyTile();
            this.tile.setPosition(pos[0], pos[1]);
            if (this.tile.equals(level.getEndPoint())) {
                nextLevelMenu.nextLevel();
                return;
            }

            level.doIntersections(this);

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
            if (!canWalkTo(pos[0] + mX, pos[1] + mY)) {
                stopSpinning();
            } else {
                walkTile = new Tile(pos[0] + mX, pos[1] + mY);
            }
        }
    }

    public void stopMoving() {
        this.moving = false;
        anim.setAutoUpdate(false);
        if (!holdingMove)
            anim.setCurrentFrame(0);
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void startSpinning(Tile tile, Direction direction) {
        if (spinning && direction == spinDirection)
            return;

        if (this.lastTile != null && tile.equals(this.lastTile))
            return;

        this.lastTile = tile.copyTile();

        spinDirection = direction;
        if (!spinning) {
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
        if (!canWalkTo(pos[0] + mX, pos[1] + mY)) {
            stopSpinning();
        } else {
            walkTile = new Tile(pos[0] + mX, pos[1] + mY);
        }
    }

    public void stopSpinning() {
        if (!spinning)
            return;

        spinning = false;
        spinDirection = null;
        facing = Direction.values()[spinAnim.getFrame()];
        anim = iFacing[facing.ordinal()];
        anim.setCurrentFrame(0);
        anim.setAutoUpdate(false);
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        if (entity instanceof WorldTile)
            return entity.canPassThrough(this);

        return true;
    }

    @Override
    public Renderable getRenderer() {
        if (spinning)
            return spinAnim;
        return anim;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public int[] getRelativePosition() {
        return new int[]{x, y};
    }
}
