package com.lordmau5.giovanni.menu;

import com.lordmau5.giovanni.button.ClickableText;
import com.lordmau5.giovanni.button.ClickableTextGrey;
import com.lordmau5.giovanni.button.IButton;
import com.lordmau5.giovanni.button.ImageButton;
import com.lordmau5.giovanni.util.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public abstract class AbstractMenu {

    public List<IButton> buttons = new ArrayList<>();

    public void onButtonLeftclick(IButton button) {

    }

    public void onMousePress(int buttonId, int x, int y, boolean press) {
        IButton button = getButton(x, y);
        if (button != null && buttonId == 0 && !press)
            onButtonLeftclick(button);
    }

    public void onMouseMove(int x, int y) {
        for (IButton button : buttons) {
            float[] pos = button.getPosition();
            if (x >= pos[0] && x <= pos[0] + button.getWidth()
                    && y >= pos[1] && y <= pos[1] + button.getHeight())
                button.onHover(true);
            else
                button.onHover(false);
        }
    }

    public void onMouseClicked(int buttonId, int x, int y, int clickCount) {
    }

    public void onMouseDragged(int newx, int newy, int lastMouseButton) {
    }

    public void init(GameContainer gameContainer) {
    }

    public abstract void render(GameContainer gameContainer, Graphics graphics);

    public abstract void update(GameContainer gameContainer, int delta);

    public IButton getButton(int x, int y) {
        for (IButton button : buttons) {
            float[] pos = button.getPosition();
            if (x >= pos[0] && x <= pos[0] + button.getWidth()
                    && y >= pos[1] && y <= pos[1] + button.getHeight())
                return button;
        }
        return null;
    }

    public void addButton(float x, float y, String text) {
        buttons.add(new ClickableText(x, y, text));
    }

    public void addGreyButton(float x, float y, String text) {
        buttons.add(new ClickableTextGrey(x, y, text));
    }

    public void addCenteredButton(int y, String text, float scale) {
        buttons.add(new ClickableText(Font.getCenteredStartX(text, scale), y, text));
    }

    public void addCenteredGreyButton(int y, String text, float scale) {
        buttons.add(new ClickableTextGrey(Font.getCenteredStartX(text, scale), y, text));
    }

    public void addImageButton(String id, float x, float y, int w, int h, String imagePath) {
        buttons.add(new ImageButton(id, x, y, w, h, imagePath));
    }

}
