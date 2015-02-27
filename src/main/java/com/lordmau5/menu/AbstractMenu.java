package main.java.com.lordmau5.menu;

import main.java.com.lordmau5.button.IButton;
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
        if(button != null && buttonId == 0 && !press)
            onButtonLeftclick(button);
    }

    public void onMouseMove(int x, int y) {
        for(IButton button : buttons) {
            float[] pos = button.getPosition();
            if(x >= pos[0] && x <= pos[0] + button.getWidth()
                    && y >= pos[1] && y <= pos[1] + button.getHeight())
                button.onHover(true);
            else
                button.onHover(false);
        }
    }

    public void init(GameContainer gameContainer) {}

    public abstract void render(GameContainer gameContainer, Graphics graphics);

    public abstract void update(GameContainer gameContainer, int delta);

    public IButton getButton(int x, int y) {
        for(IButton button : buttons) {
            float[] pos = button.getPosition();
            if(x >= pos[0] && x <= pos[0] + button.getWidth()
                    && y >= pos[1] && y <= pos[1] + button.getHeight())
                return button;
        }
        return null;
    }

}
