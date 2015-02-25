package main.java.com.lordmau5.world.tiles;

import main.java.com.lordmau5.util.ImageLoader;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 25.02.2015.
 */
public class Wall extends WorldTile {

    private int type;
    private List<Image> tiles = new ArrayList<>();

    public Wall(int x, int y, int type) {
        super(x, y);

        this.type = type;

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("tiles/wall.png"), 32, 32);
        for(int iY=0; iY<3; iY++) // Rows
            for(int iX=0; iX<5; iX++) // Images in Row
                tiles.add(sheet.getSprite(iX, iY));
    }

    @Override
    public Renderable getRenderer() {
        if(type < 0 || type >= tiles.size())
            type = 0;
        return tiles.get(type);
    }
}
