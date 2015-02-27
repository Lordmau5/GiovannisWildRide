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

    public abstract void onMousePress(int buttonId, int x, int y, boolean press);

    public void onMouseMove(int x, int y) {
        for(IButton button : buttons) {
            int[] pos = button.getPosition();
            if(x >= pos[0] && x <= pos[0] + button.getWidth()
                    && y >= pos[1] && y <= pos[1] + button.getHeight())
                button.onHover(true);
            else
                button.onHover(false);
        }
    }

    public abstract void render(GameContainer gameContainer, Graphics graphics);

    public IButton getButton(int x, int y) {
        for(IButton button : buttons) {
            int[] pos = button.getPosition();
            if(x >= pos[0] && x <= pos[0] + button.getWidth()
                    && y >= pos[1] && y <= pos[1] + button.getHeight())
                return button;
        }
        return null;
    }

}
