package com.lordmau5.giovanni.menu.editor;

import com.lordmau5.giovanni.Main;
import com.lordmau5.giovanni.button.IButton;
import com.lordmau5.giovanni.menu.AbstractMenu;
import com.lordmau5.giovanni.menu.MainMenu;
import com.lordmau5.giovanni.util.Font;
import com.lordmau5.giovanni.world.level.LevelPack;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class PreLevelEditorMenu extends AbstractMenu {

    private String levelName = "";
    private int nameNotEmpty = 0;

    public PreLevelEditorMenu() {
        addButton(820 - Font.getFont().getWidth("Back"), 650, "Back");
        addButton(200, 650, "Start Editing");
    }

    public PreLevelEditorMenu(String oldLevelpackName, LevelPack modifiedLevelPack) {
        this();

        if (modifiedLevelPack != null) {
            if (!Main.game.levelPacks.isEmpty())
                for (LevelPack pack : Main.game.levelPacks) {
                    if (pack.getName().equals(oldLevelpackName)) {
                        Main.game.levelPacks.remove(pack);
                        break;
                    }
                }
            Main.game.levelPacks.add(modifiedLevelPack);
        }
    }

    @Override
    public void onButtonLeftclick(IButton button) {
        super.onButtonLeftclick(button);

        if (button.getIdentifier().equals("Back"))
            Main.game.setMenu(new MainMenu(true));
        if (button.getIdentifier().equals("Start Editing")) {
            if (levelName.isEmpty()) {
                nameNotEmpty = 200;
                return;
            }
            Main.game.setMenu(new LevelEditorMenu(levelName));
        }
    }

    @Override
    public void init(GameContainer gameContainer) {
        final Input input = gameContainer.getInput();

        input.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(int i, char c) {
                if (i == Input.KEY_BACK && !levelName.isEmpty()) {
                    levelName = levelName.substring(0, levelName.length() - 1);
                    if (input.isKeyDown(Input.KEY_LCONTROL))
                        levelName = "";
                    return;
                }

                if (levelName.length() == 32)
                    return;

                if (Character.isLetterOrDigit(c) || Character.isSpaceChar(c) || Font.isSpecialChar(c))
                    levelName = new StringBuilder(levelName).append(c).toString();
            }

            @Override
            public void keyReleased(int i, char c) {

            }

            @Override
            public void setInput(Input input) {

            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {

            }

            @Override
            public void inputStarted() {

            }
        });
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.scale(3 / 2f, 3 / 2f);
        Font.getFont().drawString(Font.getCenteredStartX("Level Editor", 3 / 2f), 20, "Level Editor");
        graphics.scale(2 / 3f, 2 / 3f);

        for (IButton button : buttons)
            button.render(graphics);

        Font.getFont().drawString(Font.getCenteredStartX("Levelpack Filename:", 1f), 320, "Levelpack Filename:");

        graphics.scale(2 / 3f, 2 / 3f);
        Font.getFont().drawString(Font.getCenteredStartX("(Without the .lvlPack ending!)", 2 / 3f), 370 * (3 / 2f), "(Without the .lvlPack ending!)");
        graphics.scale(3 / 2f, 3 / 2f);

        graphics.scale(2 / 3f, 2 / 3f);
        Font.getFont().drawString(Font.getCenteredStartX("(Just type using your keyboard)", 2 / 3f), 460 * (3 / 2f), "(Just type using your keyboard)");
        graphics.scale(3 / 2f, 3 / 2f);

        if (nameNotEmpty > 0) {
            Font.getFont().drawString(Font.getCenteredStartX("You have to enter a name!", 1f), 520, "You have to enter a name!", Color.red);
            nameNotEmpty--;
        }

        Rectangle oldClip = graphics.getClip();

        graphics.setColor(Color.cyan);
        graphics.drawRect(200, 400, 620, 50);
        graphics.setClip(210, 410, 600, 50);
        int x = 210;
        float width = Font.getFont().getWidth(levelName);
        if (width > 600)
            x -= (width - 600);
        Font.getFont().drawString(x, 410, levelName);

        graphics.setClip(oldClip);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {

    }
}
