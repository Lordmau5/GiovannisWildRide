package com.lordmau5.giovanni.menu.editor;

import com.lordmau5.giovanni.Main;
import com.lordmau5.giovanni.button.IButton;
import com.lordmau5.giovanni.menu.AbstractMenu;
import com.lordmau5.giovanni.menu.editor.sub.LevelEditorSetLevelName;
import com.lordmau5.giovanni.menu.editor.sub.LevelEditorSetLevelpackName;
import com.lordmau5.giovanni.menu.editor.sub.LevelEditorTestLevel;
import com.lordmau5.giovanni.util.Font;
import com.lordmau5.giovanni.util.LevelLoader;
import com.lordmau5.giovanni.world.level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelEditorPauseMenu extends AbstractMenu {

    public final LevelEditorMenu levelEditorMenu;
    private int levelSaveInt;
    private int testLevelInt;

    public LevelEditorPauseMenu(LevelEditorMenu levelEditorMenu) {
        this.levelEditorMenu = levelEditorMenu;

        addCenteredGreyButton(210, "Set Level Name", 1f);
        addCenteredGreyButton(260, "Set Levelpack Name", 1f);
        addCenteredGreyButton(320, "Switch to previous Level", 1f);
        addCenteredGreyButton(370, "Switch to next Level", 1f);
        addCenteredGreyButton(470, "Test Level", 1f);
        addCenteredGreyButton(620, "Save Levelpack", 1f);
        addCenteredGreyButton(700, "Exit Editing", 1f);
    }

    public void updateLevelName(String levelName) {
        if (!levelName.isEmpty())
            levelEditorMenu.getCurrentLevel().setLevelName(levelName);
    }

    public void updateLevelpackName(String levelpackName) {
        if (!levelpackName.isEmpty())
            levelEditorMenu.setLevelpackName(levelpackName);
    }

    @Override
    public void onButtonLeftclick(IButton button) {
        super.onButtonLeftclick(button);

        if (button.getIdentifier().equals("Set Level Name"))
            Main.game.setMenu(new LevelEditorSetLevelName(this, levelEditorMenu.getCurrentLevel().getLevelName()));
        if (button.getIdentifier().equals("Set Levelpack Name"))
            Main.game.setMenu(new LevelEditorSetLevelpackName(this, levelEditorMenu.getLevelpackName()));
        if (button.getIdentifier().equals("Switch to previous Level")) {
            if (levelEditorMenu.getCurrentLevelId() > 1)
                levelEditorMenu.switchLevel(levelEditorMenu.getCurrentLevelId() - 1);
        }
        if (button.getIdentifier().equals("Switch to next Level")) {
            levelEditorMenu.switchLevel(levelEditorMenu.getCurrentLevelId() + 1);
        }
        if (button.getIdentifier().equals("Test Level")) {
            Level level = levelEditorMenu.getCurrentLevel();
            if(level.getStartPoint() == null || level.getEndPoint() == null) {
                testLevelInt = 200;
                return;
            }
            Main.game.setMenu(new LevelEditorTestLevel(this, level));
        }
        if (button.getIdentifier().equals("Save Levelpack")) {
            boolean canSave = levelEditorMenu.canSaveLevelpack();
            if (!canSave) {
                levelSaveInt = -200;
            }
            else {
                levelSaveInt = 200;
                levelEditorMenu.saveLevelpack();
            }
        }
        if (button.getIdentifier().equals("Exit Editing"))
            if(levelEditorMenu.canSaveLevelpack() && LevelLoader.doesLevelPackExist(levelEditorMenu.getLevelpackFilename()))
                Main.game.setMenu(new PreLevelEditorMenu(levelEditorMenu.getOldLevelpackName(), levelEditorMenu.getLevelPack()));
            else
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
        String levelPackName = "Levelpack: " + levelEditorMenu.getLevelpackName();
        tWidth = Font.getFont().getWidth(levelPackName);
        x = Font.getCenteredStartX(levelPackName, 2 / 3f);
        graphics.fillRect(x - 10, 180, tWidth + 20, 50);
        Font.getFont().drawString(x, 190, levelPackName);
        graphics.scale(3 / 2f, 3 / 2f);

        graphics.scale(2 / 3f, 2 / 3f);
        String levelName = "Level (" + levelEditorMenu.getCurrentLevelId() + "/" + levelEditorMenu.getLevels() + "): " + levelEditorMenu.getCurrentLevel().getLevelName();
        tWidth = Font.getFont().getWidth(levelName);
        x = Font.getCenteredStartX(levelName, 2 / 3f);
        graphics.fillRect(x - 10, 230, tWidth + 20, 50);
        Font.getFont().drawString(x, 240, levelName);
        graphics.scale(3 / 2f, 3 / 2f);

        if (levelSaveInt < 0) {
            graphics.scale(2 / 3f, 2 / 3f);
            String errorText = "One or more levels are corrupted!";
            tWidth = Font.getFont().getWidth(errorText);
            x = Font.getCenteredStartX(errorText, 2 / 3f);
            graphics.fillRect(x - 10, 810, tWidth + 20, 50);
            Font.getFont().drawString(x, 820, errorText, Color.red);
            graphics.scale(3 / 2f, 3 / 2f);
            levelSaveInt++;
        } else if (levelSaveInt > 0) {
            graphics.scale(2 / 3f, 2 / 3f);
            String errorText = "Levelpack successfully saved!";
            tWidth = Font.getFont().getWidth(errorText);
            x = Font.getCenteredStartX(errorText, 2 / 3f);
            graphics.fillRect(x - 10, 810, tWidth + 20, 50);
            Font.getFont().drawString(x, 820, errorText, Color.green);
            graphics.scale(3 / 2f, 3 / 2f);
            levelSaveInt--;
        } else if(testLevelInt > 0) {
            graphics.scale(2 / 3f, 2 / 3f);
            String errorText = "Level is missing start or end point!";
            tWidth = Font.getFont().getWidth(errorText);
            x = Font.getCenteredStartX(errorText, 2 / 3f);
            graphics.fillRect(x - 10, 810, tWidth + 20, 50);
            Font.getFont().drawString(x, 820, errorText, Color.red);
            graphics.scale(3 / 2f, 3 / 2f);
            testLevelInt--;
        }

        for (IButton button : buttons)
            button.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            Main.game.setMenu(levelEditorMenu);
        }
    }

}
