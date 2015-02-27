package main.java.com.lordmau5.menu;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.button.ClickableTextGrey;
import main.java.com.lordmau5.button.IButton;
import main.java.com.lordmau5.util.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class PauseMenu extends AbstractMenu {

    private GameMenu gameMenu;

    public PauseMenu(GameMenu gameMenu) {
        this.gameMenu = gameMenu;

        addButton(Font.getCenteredStartX("Exit Level", 1f), 650, "Exit Level");
    }

    @Override
    public void onMousePress(int buttonId, int x, int y, boolean press) {
        if(buttonId != 0 || !press)
            return;

        IButton button = getButton(x, y);
        if(button == null)
            return;

        if(button.getIdentifier().equals("Exit Level")) {
            Main.game.setMenu(new MainMenu(true));
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        gameMenu.render(gameContainer, graphics);
        graphics.setColor(new Color(0f, 0f, 0f, 0.25f));
        graphics.fillRect(0, 0, Main.width, Main.height);

        graphics.scale(3 / 2f, 3 / 2f);
        int tWidth = Font.getFont().getWidth("Pause");
        int x = 512 / 2 + tWidth / 5;
        graphics.fillRect(x - 10, 30, tWidth + 20, 50);
        Font.getFont().drawString(Font.getCenteredStartX("Pause", 3 / 2f), 40, "Pause");
        graphics.scale(2 / 3f, 2 / 3f);

        for(IButton button : buttons) {
            button.render(graphics);
            /*
            float[] pos = button.getPosition();
            if(button.getText() != null)
                Font.getFont().drawString(pos[0], pos[1], button.getText(), button.getColor());

            if(button instanceof ImageButton) {
                ImageButton iButton = (ImageButton) button;
                graphics.drawImage(iButton.getImage(), pos[0], pos[1], button.getColor());
            }
            */
        }
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if(input.isKeyPressed(Input.KEY_ESCAPE)) {
            Main.game.setMenu(gameMenu);
        }
    }

    public void addButton(float x, float y, String text) {
        buttons.add(new ClickableTextGrey(x, y, text));
    }
}