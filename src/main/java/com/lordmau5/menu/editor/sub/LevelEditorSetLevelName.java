package main.java.com.lordmau5.menu.editor.sub;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.menu.AbstractMenu;
import main.java.com.lordmau5.menu.editor.LevelEditorPauseMenu;
import main.java.com.lordmau5.util.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelEditorSetLevelName extends AbstractMenu {

    private String levelName;
    private LevelEditorPauseMenu pauseMenu;

    public LevelEditorSetLevelName(LevelEditorPauseMenu pauseMenu, String levelName) {
        this.pauseMenu = pauseMenu;
        this.levelName = levelName;
    }

    @Override
    public void init(GameContainer gameContainer) {
        super.init(gameContainer);

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

                if (/*Character.isAlphabetic(c) || */ Character.isDefined(c))
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
        pauseMenu.levelEditorMenu.render(gameContainer, graphics);

        graphics.setColor(new Color(0f, 0f, 0f, 0.25f));
        graphics.fillRect(0, 0, Main.width, Main.height);

        Rectangle oldClip = graphics.getClip();

        graphics.fillRect(200, 200, 620, 50);
        graphics.setColor(Color.cyan);
        graphics.drawRect(200, 200, 620, 50);
        graphics.setClip(210, 210, 600, 50);
        int x = 210;
        float width = Font.getFont().getWidth(levelName);
        if(width > 600)
            x -= (width - 600);
        main.java.com.lordmau5.util.Font.getFont().drawString(x, 210, levelName);

        graphics.setClip(oldClip);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if(input.isKeyPressed(Input.KEY_ESCAPE)) {
            pauseMenu.updateLevelName(levelName);
            Main.game.setMenu(pauseMenu);
        }
    }
}
