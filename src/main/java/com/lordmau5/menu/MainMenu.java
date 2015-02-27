package main.java.com.lordmau5.menu;

import main.java.com.lordmau5.Main;
import main.java.com.lordmau5.button.ClickableText;
import main.java.com.lordmau5.button.IButton;
import main.java.com.lordmau5.entity.Player;
import main.java.com.lordmau5.util.Direction;
import main.java.com.lordmau5.util.Font;
import main.java.com.lordmau5.world.tiles.Spin;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class MainMenu extends AbstractMenu {

    private float scale = 1f, spinUpAlpha = 1f, colorScale = 0f;
    private int introX = 1024, introY = 360;
    private boolean introSpinning = false;
    private Spin spinUpIntro;

    public MainMenu(boolean showingFinished) {
        spinUpIntro = new Spin(1, 1, Direction.UP);

        showMainMenu();

        if(showingFinished) {
            scale = 3f;
            introY = 100;
            introX = 490;
            colorScale = 1f;
            spinUpAlpha = 0f;
        }
    }

    private void showMainMenu() {
        buttons.clear();
        addCenteredButton(520, "Play");
        addCenteredButton(580, "Level Editor");
        addCenteredButton(640, "Options");
        addCenteredButton(700, "Exit");
    }

    public void addCenteredButton(int y, String text) {
        buttons.add(new ClickableText(1024 / 2 - Font.getFont().getWidth(text) / 2, y, text, Font.getFont()));
    }

    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
    }

    public void onMousePress(int buttonId, int x, int y, boolean press) {
        if(buttonId != 0 || !press)
            return;

        IButton button = getButton(x, y);
        if(button == null)
            return;

        if (button.getText().equals("Exit")) {
            System.exit(0);
        } else if (button.getText().equals("Play")) {
            Main.game.setMenu(new LevelpackList());
        } else if (button.getText().equals("Level Editor")) {
            // TODO: Show Level Editor
        } else if (button.getText().equals("Options")) {
            // TODO: Show Options?
        }
    }

    public void render(GameContainer gameContainer, Graphics graphics) {
        Animation anim = Player.iFacing[Direction.LEFT.ordinal()];

        graphics.scale(1f, 1f);
        graphics.drawImage(spinUpIntro.getImage(), 480, 368, new Color(1f, 1f, 1f, spinUpAlpha));

        if(introSpinning) {
            anim = Player.spinAnim;
            if(scale <= 3f)
                scale += 0.015f;
            if(spinUpAlpha > 0f)
                spinUpAlpha -= 0.025f;
            if(introY > 100)
                introY -= 2;
            graphics.scale(scale, scale);
            graphics.drawAnimation(anim, introX / scale - (anim.getWidth() / 3), introY / scale);

            if(scale >= 3f) {
                String text = "Giovanni's Wild Ride!";
                graphics.scale(1 / scale * 2, 1 / scale * 2);
                if(colorScale <= 1f)
                    colorScale += 0.05f;
                Font.getFont().drawString(Font.getFont().getWidth(text) / 2 / scale, 5, text, new Color(1f, 1f, 1f, colorScale));

                graphics.scale(0.5f, 0.5f);
                for(IButton button : buttons) {
                    int[] pos = button.getPosition();
                    Color color = button.getColor();
                    color.a = colorScale;
                    Font.getFont().drawString(pos[0], pos[1], button.getText(), color);
                }
            }
        }
        else {
            if(introX > 490)
                introX -= 2;
            else {
                introSpinning = true;
            }
            graphics.drawAnimation(anim, introX, introY);
        }
    }

}
