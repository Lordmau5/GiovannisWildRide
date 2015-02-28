package main.java.com.lordmau5.menu.editor;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.button.IButton;
import main.java.com.lordmau5.menu.AbstractMenu;
import main.java.com.lordmau5.menu.editor.sub.LevelEditorSetLevelName;
import main.java.com.lordmau5.menu.editor.sub.LevelEditorSetLevelpackName;
import main.java.com.lordmau5.util.Font;
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

    public LevelEditorPauseMenu(LevelEditorMenu levelEditorMenu) {
        this.levelEditorMenu = levelEditorMenu;

        addCenteredGreyButton(210, "Set Level Name", 1f);
        addCenteredGreyButton(260, "Set Levelpack Name", 1f);
        // TODO: Add function for changing the level-PACK name
        addCenteredGreyButton(350, "Switch to previous Level", 1f);
        addCenteredGreyButton(420, "Switch to next Level", 1f);
        addCenteredGreyButton(570, "Save Levelpack", 1f);
        addCenteredGreyButton(650, "Exit Editing", 1f);
    }

    public void updateLevelName(String levelName) {
        if(!levelName.isEmpty())
            levelEditorMenu.getCurrentLevel().setLevelName(levelName);
    }

    public void updateLevelpackName(String levelpackName) {
        if (!levelpackName.isEmpty())
            levelEditorMenu.setLevelpackName(levelpackName);
    }

    @Override
    public void onButtonLeftclick(IButton button) {
        super.onButtonLeftclick(button);

        if(button.getIdentifier().equals("Set Level Name"))
            Main.game.setMenu(new LevelEditorSetLevelName(this, levelEditorMenu.getCurrentLevel().getLevelName()));
        if(button.getIdentifier().equals("Set Levelpack Name"))
            Main.game.setMenu(new LevelEditorSetLevelpackName(this, levelEditorMenu.getLevelpackName()));
        if(button.getIdentifier().equals("Switch to previous Level")) {
            if(levelEditorMenu.getCurrentLevelId() > 1)
                levelEditorMenu.switchLevel(levelEditorMenu.getCurrentLevelId() - 1);
        }
        if(button.getIdentifier().equals("Switch to next Level")) {
            levelEditorMenu.switchLevel(levelEditorMenu.getCurrentLevelId() + 1);
        }
        if(button.getIdentifier().equals("Save Levelpack")) {
            boolean isSaved = levelEditorMenu.saveLevelpack();
            if(!isSaved) {
                levelSaveInt = -200;
            }
            else
                levelSaveInt = 200;
        }
        if(button.getIdentifier().equals("Exit Editing"))
            Main.game.setMenu(new PreLevelEditorMenu(levelEditorMenu.getOldLevelpackName(), levelEditorMenu.getLevelPack()));
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

        if(levelSaveInt < 0) {
            graphics.scale(2 / 3f, 2 / 3f);
            String errorText = "One or more levels are corrupted!";
            tWidth = Font.getFont().getWidth(errorText);
            x = Font.getCenteredStartX(errorText, 2 / 3f);
            graphics.fillRect(x - 10, 710, tWidth + 20, 50);
            Font.getFont().drawString(x, 720, errorText, Color.red);
            graphics.scale(3 / 2f, 3 / 2f);
            levelSaveInt++;
        }
        else if(levelSaveInt > 0) {
            graphics.scale(2 / 3f, 2 / 3f);
            String errorText = "Levelpack successfully saved!";
            tWidth = Font.getFont().getWidth(errorText);
            x = Font.getCenteredStartX(errorText, 2 / 3f);
            graphics.fillRect(x - 10, 710, tWidth + 20, 50);
            Font.getFont().drawString(x, 720, errorText, Color.green);
            graphics.scale(3 / 2f, 3 / 2f);
            levelSaveInt--;
        }

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

}
