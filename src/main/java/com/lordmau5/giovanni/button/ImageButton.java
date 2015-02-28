package com.lordmau5.giovanni.button;

import com.lordmau5.giovanni.util.ImageLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class ImageButton implements IButton {

    private String id;
    private float x, y;
    private int w, h;
    private Image image;
    private Color color = Color.white;

    public ImageButton(String id, float x, float y, int w, int h, String imagePath) {
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
    public float[] getPosition() {
        return new float[]{x, y};
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
    public void render(Graphics graphics) {
        graphics.drawImage(getImage(), x, y, getColor());
    }

    public Image getImage() {
        return image;
    }
}
