package main.java.com.lordmau5.world;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class Tile {

    private int x;
    private int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getAbsolutePosition() {
        return new int[]{this.x, this.y};
    }

    public int[] getPosition() {
        return new int[]{this.x * 2, this.y * 2};
    }

    /*@Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Tile))
            return false;
        Tile oTile = (Tile) obj;
        return oTile.x == this.x && oTile.y == this.y;
    }*/

    public boolean positionEquals(Tile oTile) {
        return oTile.x == this.x && oTile.y == this.y;
    }
}
