package com.lordmau5.giovanni.menu.editor.sub;

import com.lordmau5.giovanni.Main;
import com.lordmau5.giovanni.menu.AbstractMenu;
import com.lordmau5.giovanni.menu.editor.LevelEditorPauseMenu;
import com.lordmau5.giovanni.util.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelEditorSetLevelpackName extends AbstractMenu {

    private String levelpackName;
    private LevelEditorPauseMenu pauseMenu;

    public LevelEditorSetLevelpackName(LevelEditorPauseMenu pauseMenu, String levelpackName) {
        this.pauseMenu = pauseMenu;
        this.levelpackName = levelpackName;
    }

    @Override
    public void init(GameContainer gameContainer) {
        super.init(gameContainer);

        final Input input = gameContainer.getInput();

        input.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(int i, char c) {
                if (i == Input.KEY_BACK && !levelpackName.isEmpty()) {
                    levelpackName = levelpackName.substring(0, levelpackName.length() - 1);
                    if (input.isKeyDown(Input.KEY_LCONTROL))
                        levelpackName = "";
                    return;
                }

                if (levelpackName.length() == 32)
                    return;

                if (Character.isLetterOrDigit(c) || Character.isSpaceChar(c) || Font.isSpecialChar(c))
                    levelpackName = new StringBuilder(levelpackName).append(c).toString();
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
        pauseMenu.levelEditorMenu.render(gameContainer, graphics);

        graphics.setColor(new Color(0f, 0f, 0f, 0.25f));
        graphics.fillRect(0, 0, Main.width, Main.height);

        String string = "Set Levelpack Name:";
        int tWidth = Font.getFont().getWidth(string);
        float x = Font.getCenteredStartX(string, 1f);
        graphics.fillRect(x - 10, 150, tWidth + 20, 50);
        Font.getFont().drawString(x, 160, string);

        graphics.scale(2 / 3f, 2 / 3f);
        string = "(Press ESC to finish)";
        tWidth = Font.getFont().getWidth(string);
        x = Font.getCenteredStartX(string, 2 / 3f);
        graphics.fillRect(x - 10, 375, tWidth + 20, 50);
        Font.getFont().drawString(x, 385, string);
        graphics.scale(3 / 2f, 3 / 2f);

        Rectangle oldClip = graphics.getClip();

        graphics.fillRect(200, 200, 620, 50);
        graphics.setColor(Color.cyan);
        graphics.drawRect(200, 200, 620, 50);
        graphics.setClip(210, 210, 600, 50);
        x = 210;
        float width = Font.getFont().getWidth(levelpackName);
        if (width > 600)
            x -= (width - 600);
        Font.getFont().drawString(x, 210, levelpackName);

        graphics.setClip(oldClip);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            pauseMenu.updateLevelpackName(levelpackName);
            Main.game.setMenu(pauseMenu);
        }
    }
}
