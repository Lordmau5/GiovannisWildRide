package main.java.com.lordmau5.button;

import main.java.com.lordmau5.util.ImageLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class ImageButton implements IButton {

    private String id;
    private int x, y, w, h;
    private Image image;
    private Color color = Color.white;

    public ImageButton(String id, int x, int y, int w, int h, String imagePath) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.image = ImageLoader.loadImage(imagePath).getScaledCopy(w, h);
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public int[] getPosition() {
        return new int[]{x, y};
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public void onHover(boolean isHover) {
        color = isHover ? Color.cyan : Color.white;
    }

    @Override
    public void onClick(int button, boolean press) {

    }

    public Image getImage() {
        return image;
    }
}
