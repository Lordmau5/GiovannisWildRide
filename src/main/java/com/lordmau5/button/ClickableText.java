package main.java.com.lordmau5.button;

import org.newdawn.slick.Color;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class ClickableText implements IButton {

    private final int x, y;
    private final String text;
    private Color color = Color.white;

    public ClickableText(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
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
        return text;
    }

    @Override
    public void onClick(int button, boolean press) {

    }

    @Override
    public void onHover(boolean isHover) {
        color = isHover ? Color.cyan : Color.white;
    }
}
