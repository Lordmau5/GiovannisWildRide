package main.java.com.lordmau5.world.tiles;

import main.java.com.lordmau5.util.ImageLoader;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class StartEndPoint extends WorldTile {

    private boolean isStart = false;
    private Image[] images = new Image[2];
    private Image renderImage;

    public StartEndPoint(int x, int y) {
        super(x, y);

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("tiles/startEnd.png"), 32, 32);
        for(int i=0; i<2; i++)
            images[i] = sheet.getSprite(i, 0);

        renderImage = ImageLoader.loadImage("tiles/floor.png");
    }

    public StartEndPoint(int x, int y, boolean isStart) {
        this(x, y);

        this.isStart = isStart;
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("tiles/startEnd.png"), 32, 32);
        for(int i=0; i<2; i++)
            images[i] = sheet.getSprite(i, 0);

        renderImage = ImageLoader.loadImage("tiles/floor.png");
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public boolean isStart() {
        return isStart;
    }

    @Override
    public WorldTile copyTile() {
        StartEndPoint startEndPoint = (StartEndPoint) super.copyTile();
        startEndPoint.setIsStart(isStart);
        return startEndPoint;
    }

    @Override
    public Image getImage() {
        return images[isStart ? 0 : 1];
    }

    @Override
    public Renderable getRenderer() {
        return renderImage;
    }
}
