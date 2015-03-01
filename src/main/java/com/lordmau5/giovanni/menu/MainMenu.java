package com.lordmau5.giovanni.menu;

import com.lordmau5.giovanni.Main;
import com.lordmau5.giovanni.button.IButton;
import com.lordmau5.giovanni.entity.Player;
import com.lordmau5.giovanni.menu.editor.PreLevelEditorMenu;
import com.lordmau5.giovanni.util.Direction;
import com.lordmau5.giovanni.util.Font;
import com.lordmau5.giovanni.world.tiles.Spin;
import org.newdawn.slick.*;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class MainMenu extends AbstractMenu {

    private float scale = 1f, spinUpAlpha = 1f, colorScale = 0f;
    private int introX = 1024, introY = 360;
    private boolean introSpinning = false;
    private Spin spinUpIntro;

    public MainMenu(boolean showingFinished) {
        Player.spinAnim.setSpeed(1f);
        spinUpIntro = new Spin(1, 1, Direction.UP);

        showMainMenu();

        if (showingFinished) {
            scale = 3f;
            introY = 100;
            introX = 490;
            colorScale = 1f;
            spinUpAlpha = 0f;
        }
    }

    private void showMainMenu() {
        buttons.clear();
        addCenteredButton(520, "Play", 1f);
        addCenteredButton(580, "Level Editor", 1f);
        addCenteredButton(640, "Options", 1f);
        addCenteredButton(700, "Exit", 1f);
    }

    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
    }

    @Override
    public void onButtonLeftclick(IButton button) {
        super.onButtonLeftclick(button);

        if (colorScale < 1f)
            return;

        if (button.getIdentifier().equals("Exit")) {
            System.exit(0);
        } else if (button.getIdentifier().equals("Play")) {
            Main.game.setMenu(new LevelpackList());
        } else if (button.getIdentifier().equals("Level Editor")) {
            Main.game.setMenu(new PreLevelEditorMenu());
        } else if (button.getIdentifier().equals("Options")) {
            // TODO: Show Options?
        }
    }

    public void render(GameContainer gameContainer, Graphics graphics) {
        Animation anim = Player.iFacing[Direction.LEFT.ordinal()];

        graphics.scale(1f, 1f);
        graphics.drawImage(spinUpIntro.getImage(), 480, 368, new Color(1f, 1f, 1f, spinUpAlpha));

        if (introSpinning) {
            anim = Player.spinAnim;
            if (scale <= 3f)
                scale += 0.015f;
            if (spinUpAlpha > 0f)
                spinUpAlpha -= 0.025f;
            if (introY > 100)
                introY -= 2;
            graphics.scale(scale, scale);
            graphics.drawAnimation(anim, introX / scale - (anim.getWidth() / 3), introY / scale);

            if (scale >= 3f) {
                String text = "Giovanni's Wild Ride!";
                graphics.scale(1 / scale * 2, 1 / scale * 2);
                if (colorScale <= 1f)
                    colorScale += 0.05f;
                Font.getFont().drawString(Font.getFont().getWidth(text) / 2 / scale, 5, text, new Color(1f, 1f, 1f, colorScale));

                graphics.scale(0.5f, 0.5f);
                for (IButton button : buttons) {
                    float[] pos = button.getPosition();
                    Color color = button.getColor();
                    color.a = colorScale;
                    Font.getFont().drawString(pos[0], pos[1], button.getText(), color);
                }
            }
        } else {
            if (introX > 490)
                introX -= 2;
            else {
                introSpinning = true;
            }
            graphics.drawAnimation(anim, introX, introY);
        }
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if (input.isKeyPressed(Input.KEY_ESCAPE) && colorScale < 1f) {
            scale = 3f;
            introY = 100;
            introX = 490;
            colorScale = 1f;
            spinUpAlpha = 0f;
        }
    }

}
