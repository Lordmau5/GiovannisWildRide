package com.lordmau5.giovanni.button;

import com.lordmau5.giovanni.util.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class ClickableTextGrey extends ClickableText {

    public ClickableTextGrey(float x, float y, String text) {
        super(x, y, text);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0f, 0f, 0f, 0.25f));
        graphics.fillRect(x - 10, y - 10, Font.getFont().getWidth(getText()) + 20, 50);

        super.render(graphics);
    }
}
