package main.java.com.lordmau5.menu.editor;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.button.ClickableTextGrey;
import main.java.com.lordmau5.button.IButton;
import main.java.com.lordmau5.menu.AbstractMenu;
import main.java.com.lordmau5.util.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelEditorPauseMenu extends AbstractMenu {

    private final LevelEditorMenu levelEditorMenu;

    public LevelEditorPauseMenu(LevelEditorMenu levelEditorMenu) {
        this.levelEditorMenu = levelEditorMenu;

        addCenteredButton(650, "Exit Editing", 1f);
    }

    @Override
    public void onButtonLeftclick(IButton button) {
        super.onButtonLeftclick(button);

        if(button.getIdentifier().equals("Exit Editing"))
            Main.game.setMenu(new PreLevelEditorMenu());
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        levelEditorMenu.render(gameContainer, graphics);

        graphics.setColor(new Color(0f, 0f, 0f, 0.25f));
        graphics.fillRect(0, 0, Main.width, Main.height);

        graphics.scale(3 / 2f, 3 / 2f);
        int tWidth = Font.getFont().getWidth("Pause");
        float x = 512 / 2 + tWidth / 5;
        graphics.fillRect(x - 10, 30, tWidth + 20, 50);
        Font.getFont().drawString(Font.getCenteredStartX("Pause", 3 / 2f), 40, "Pause");
        graphics.scale(2 / 3f, 2 / 3f);
        graphics.scale(2 / 3f, 2 / 3f);
        tWidth = Font.getFont().getWidth(levelEditorMenu.getLevelpackName());
        float startingX = Font.getCenteredStartX(levelEditorMenu.getLevelpackName(), 2 / 3f);
        x = startingX;
        graphics.fillRect(x - 10, 180, tWidth + 20, 50);
        Font.getFont().drawString(Font.getCenteredStartX(levelEditorMenu.getLevelpackName(), 2 / 3f), 190, levelEditorMenu.getLevelpackName());
        graphics.scale(3 / 2f, 3 / 2f);

        for(IButton button : buttons)
            button.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if(input.isKeyPressed(Input.KEY_ESCAPE)) {
            Main.game.setMenu(levelEditorMenu);
        }
    }

    public void addCenteredButton(int y, String text, float scale) {
        buttons.add(new ClickableTextGrey(Font.getCenteredStartX(text, scale), y, text));
    }

}
