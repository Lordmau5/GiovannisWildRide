package main.java.com.lordmau5.button;

import main.java.com.lordmau5.util.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class ClickableText implements IButton {

    protected final float x, y;
    private final String text;
    private Color color = Color.white;

    public ClickableText(float x, float y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    @Override
    public int getWidth() {
        return Font.getFont().getWidth(text);
    }

    @Override
    public int getHeight() {
        return Font.getFont().getHeight(text);
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
        return text;
    }

    @Override
    public String getIdentifier() {
        return text;
    }

    @Override
    public void onHover(boolean isHover) {
        color = isHover ? Color.cyan : Color.white;
    }

    @Override
    public void render(Graphics graphics) {
        if(getText() != null && !getText().isEmpty())
            Font.getFont().drawString(x, y, getText(), getColor());
    }
}
