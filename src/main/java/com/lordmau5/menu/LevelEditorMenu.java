package main.java.com.lordmau5.menu;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.button.ClickableText;
import main.java.com.lordmau5.button.IButton;
import main.java.com.lordmau5.util.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelEditorMenu extends AbstractMenu {
    //TODO: Actually CREATE / START ON it?!

    private String levelName = "";

    public LevelEditorMenu() {
        addButton(Font.getCenteredStartX("Back", 1f), 650, "Back");
    }

    @Override
    public void onMousePress(int buttonId, int x, int y, boolean press) {
        if(buttonId != 0 || !press)
            return;

        IButton button = getButton(x, y);
        if(button == null)
            return;

        if(button.getIdentifier().equals("Back"))
            Main.game.setMenu(new MainMenu(true));
    }

    @Override
    public void init(GameContainer gameContainer) {
        final Input input = gameContainer.getInput();

        input.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(int i, char c) {
                if(i == Input.KEY_BACK && !levelName.isEmpty()) {
                    levelName = levelName.substring(0, levelName.length() - 1);
                    if(input.isKeyPressed(Input.KEY_LCONTROL))
                        levelName = "";
                    return;
                }

                if(/*Character.isAlphabetic(c) || */ Character.isDefined(c))
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
        for(IButton button : buttons)
            button.render(graphics);

        Rectangle oldClip = graphics.getClip();

        graphics.setColor(Color.cyan);
        graphics.drawRect(200, 400, 600, 50);
        graphics.setClip(210, 410, 580, 50);
        int x = 210;
        float width = Font.getFont().getWidth(levelName);
        if(width > 580)
            x -= (width - 580);
        Font.getFont().drawString(x, 410, levelName);

        graphics.setClip(oldClip);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {

    }

    public void addButton(float x, float y, String text) {
        buttons.add(new ClickableText(x, y, text));
    }
}
