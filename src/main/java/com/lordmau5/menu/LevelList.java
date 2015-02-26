package main.java.com.lordmau5.menu;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.button.ClickableText;
import main.java.com.lordmau5.button.IButton;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class LevelList extends AbstractMenu {

    public LevelList() {
        addCenteredButton(700, "Back");
    }

    @Override
    public void onMousePress(int buttonId, int x, int y, boolean press) {
        if(buttonId != 0 || !press)
            return;

        IButton button = getButton(x, y);
        if(button == null)
            return;

        if(button.getText().equals("Back"))
            Main.game.setMenu(new MainMenu(true));
    }

    @Override
    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.scale(1f, 1f);
        for(IButton button : buttons) {
            int[] pos = button.getPosition();
            font.drawString(pos[0], pos[1], button.getText(), button.getColor());
        }
    }

    public void addCenteredButton(int y, String text) {
        buttons.add(new ClickableText(1024 / 2 - font.getWidth(text) / 2, y, text));
    }

}
