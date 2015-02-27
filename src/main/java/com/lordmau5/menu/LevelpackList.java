package main.java.com.lordmau5.menu;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.button.IButton;
import main.java.com.lordmau5.button.ImageButton;
import main.java.com.lordmau5.util.Font;
import main.java.com.lordmau5.world.level.LevelPack;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.List;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class LevelpackList extends AbstractMenu {

    private List<LevelPack> levelPacks;
    private int modifier = 0;
    private int selected = -1;
    private float movingX = 0;
    private boolean movingLeft = true;

    private int mouseOverX, mouseOverY;

    public LevelpackList() {
        levelPacks = Main.game.levelPacks;
        if(levelPacks.size() > 0)
            selected = 0;

        addButton(240, 700, "Play");
        addButton(700, 700, "Back");

        addImageButton("Up", 860, 650 - 96, 32, 32, "arrowUp.png");
        addImageButton("Down", 860, 650 - 32, 32, 32, "arrowDown.png");
    }

    @Override
    public void onButtonLeftclick(IButton button) {
        super.onButtonLeftclick(button);

        if(button.getIdentifier().equals("Back"))
            Main.game.setMenu(new MainMenu(true));
        if(button.getIdentifier().equals("Play")) {
            if(selected != -1)
                Main.game.setMenu(new GameMenu(levelPacks.get(selected)));
        }
        if(button.getIdentifier().equals("Up")) {
            if(modifier == 0)
                return;
            modifier--;
        }
        if(button.getIdentifier().equals("Down")) {
            if(modifier == levelPacks.size() - 9)
                return;
            modifier++;
        }
    }

    @Override
    public void onMousePress(int buttonId, int x, int y, boolean press) {
        super.onMousePress(buttonId, x, y, press);

        if(x >= 200 && y >= 100 && x <= 840 && y <= 650) { // Clicking levels
            int levelPackId = getMouseOverLevelPackId();
            if(levelPackId == -1)
                return;
            System.out.println("LevelPack ID: " + levelPackId);
            selected = levelPackId;
            movingX = 0;
            return;
        }
    }

    @Override
    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
        mouseOverX = x;
        mouseOverY = y;
    }

    private int getMouseOverLevelPackId() {
        for (int i=modifier; i<modifier + 9; i++) {
            if (mouseOverX >= 210 && mouseOverX <= 840) {
                if (mouseOverY >= 110 + ((i - modifier) * 60) && mouseOverY <= 160 + ((i - modifier) * 60)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean mouseOverLevel(int num) {
        if(mouseOverX >= 210 && mouseOverX <= 840) {
            if(mouseOverY >= 110 + ((num - modifier) * 60) && mouseOverY <= 160 + ((num - modifier) * 60)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        Rectangle oldClip = graphics.getClip();

        graphics.scale(3 / 2f, 3 / 2f);
        Font.getFont().drawString(Font.getCenteredStartX("Levelpacks", 3 / 2f), 20, "Levelpacks");
        graphics.scale(2 / 3f, 2 / 3f);

        graphics.setColor(new Color(1f, 1f, 1f, 0.5f));
        graphics.drawRect(180, 100, 670, 550);

        for(int i=modifier; i<Math.min(levelPacks.size(), modifier + 9); i++) {
            Color color = mouseOverLevel(i) ? Color.cyan : (i == selected ? Color.green : Color.white);
            color.a = 0.5f;
            graphics.setColor(color);
            graphics.fillRect(190, 110 + ((i - modifier) * 60), 650, 50);

            color.a = 1f;
            graphics.setColor(color);
            String name = levelPacks.get(i).getName();
            graphics.setClip(190, 110 + ((i - modifier) * 60), 650, 50);
            float xPos = Font.getCenteredStartX(name, 1f);
            int maxX = 0;
            if(name.length() > 21) {
                maxX = Font.getFont().getWidth(name.substring(13)) + 10;
                xPos = 232;

                float mod = 0.5f * ((maxX / 22) / 10);
                if(selected == i) {
                    if (movingLeft) {
                        movingX += mod;
                        if (movingX + xPos >= maxX)
                            movingLeft = false;
                    } else {
                        movingX -= mod;
                        if (movingX <= 0)
                            movingLeft = true;
                    }
                    xPos -= movingX;
                }
            }
            Font.getFont().drawString(xPos, 120 + (i - modifier) * 60, name);
            graphics.setClip(oldClip);
        }

        for(IButton button : buttons) {
            float[] pos = button.getPosition();
            if(button.getText() != null)
                Font.getFont().drawString(pos[0], pos[1], button.getText(), button.getColor());

            if(button instanceof ImageButton) {
                ImageButton iButton = (ImageButton) button;
                graphics.drawImage(iButton.getImage(), pos[0], pos[1], button.getColor());
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {

    }

}
