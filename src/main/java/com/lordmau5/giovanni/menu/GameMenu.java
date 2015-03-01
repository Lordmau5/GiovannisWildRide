package com.lordmau5.giovanni.menu;

import com.lordmau5.giovanni.Main;
import com.lordmau5.giovanni.entity.Player;
import com.lordmau5.giovanni.util.Direction;
import com.lordmau5.giovanni.util.Font;
import com.lordmau5.giovanni.world.level.Level;
import com.lordmau5.giovanni.world.level.LevelPack;
import com.lordmau5.giovanni.world.tiles.WorldTile;
import org.newdawn.slick.*;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class GameMenu extends AbstractMenu implements INextLevelMenu {

    private Player player;
    private LevelPack levelPack;
    private Level level;
    private int currentLevelId;

    private int intro;
    private int introTime;
    private float introAlpha;

    private int outro;
    private float outroSpinSpeed;
    private float outroAlpha;

    public GameMenu(LevelPack levelPack) {
        player = new Player(this);
        this.levelPack = levelPack;

        this.level = levelPack.getFirstLevel();
        currentLevelId = 0;
        player.setLevel(this.level);

        startIntro();
    }

    private void startIntro() {
        intro = 4;
        introTime = 60;
        introAlpha = 0f;
    }

    private void startOutro() {
        outro = 2;
        outroSpinSpeed = 0.5f;
        outroAlpha = 0f;
    }

    public Player getPlayer() {
        return player;
    }

    public void resetLevel() {
        this.level = levelPack.resetLevel(currentLevelId);
        for (WorldTile tile : level.getWorldTiles())
            tile.resetState();

        player.setLevel(level);
    }

    private void properNextLevel() {
        this.level = levelPack.getNextLevel(currentLevelId);
        if (this.level == null) {
            Main.game.setMenu(new LevelpackList());
        } else {
            currentLevelId++;
            player.setLevel(this.level);
            player.y = -28;
            player.anim = Player.spinAnim;
            startIntro();
        }
    }

    public void nextLevel() {
        startOutro();
    }

    @Override
    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        if (level != null)
            level.render(gameContainer, graphics);

        int[] pos = player.getRelativePosition();
        player.getRenderer().draw(pos[0] + 2, pos[1] - 4);

        if(intro > 0) {
            if(intro == 4) {
                graphics.setColor(new Color(0f, 0f, 0f, 1f));
                graphics.fillRect(0, 0, Main.width, Main.height);

                if(introAlpha < 1f)
                    introAlpha += 0.025f;

                Color color = new Color(1f, 1f, 1f, introAlpha);

                graphics.scale(3 / 2f, 3 / 2f);
                Font.getFont().drawString(Font.getCenteredStartX(levelPack.getName(), 3 / 2f), 25, levelPack.getName(), color);
                graphics.scale(2 / 3f, 2 / 3f);

                introTime--;
                if(introTime <= 0) {
                    introTime = 60;
                    intro = 3;
                    introAlpha = 0f;
                }
            }
            else if(intro == 3) {
                graphics.setColor(new Color(0f, 0f, 0f, 1f));
                graphics.fillRect(0, 0, Main.width, Main.height);

                graphics.scale(3 / 2f, 3 / 2f);
                Font.getFont().drawString(Font.getCenteredStartX(levelPack.getName(), 3 / 2f), 25, levelPack.getName());
                graphics.scale(2 / 3f, 2 / 3f);

                if(introAlpha < 1f)
                    introAlpha += 0.025f;

                Color color = new Color(1f, 1f, 1f, introAlpha);

                String levelString = "Level " + (currentLevelId + 1) + "/" + levelPack.getLevels().size();
                Font.getFont().drawString(Font.getCenteredStartX(levelString, 1f), 150, levelString, color);

                introTime--;
                if(introTime <= 0) {
                    introTime = 100;
                    intro = 2;
                    introAlpha = 0f;
                }
            }
            else if(intro == 2) {
                graphics.setColor(new Color(0f, 0f, 0f, 1f));
                graphics.fillRect(0, 0, Main.width, Main.height);

                graphics.scale(3 / 2f, 3 / 2f);
                Font.getFont().drawString(Font.getCenteredStartX(levelPack.getName(), 3 / 2f), 25, levelPack.getName());
                graphics.scale(2 / 3f, 2 / 3f);

                String levelString = "Level " + (currentLevelId + 1) + "/" + levelPack.getLevels().size();
                Font.getFont().drawString(Font.getCenteredStartX(levelString, 1f), 150, levelString);

                if(introAlpha < 1f)
                    introAlpha += 0.025f;

                Color color = new Color(1f, 1f, 1f, introAlpha);

                levelString = level.getLevelName();
                Font.getFont().drawString(Font.getCenteredStartX(levelString, 1f), 200, levelString, color);

                introTime--;
                if(introTime <= 0) {
                    intro = 1;
                    introAlpha = 1f;
                }
            } else if(intro == 1) {
                if(introAlpha > 0f)
                    introAlpha -= 0.025f;

                graphics.setColor(new Color(0f, 0f, 0f, introAlpha));
                graphics.fillRect(0, 0, Main.width, Main.height);

                Color color = new Color(1f, 1f, 1f, introAlpha);

                graphics.scale(3 / 2f, 3 / 2f);
                Font.getFont().drawString(Font.getCenteredStartX(levelPack.getName(), 3 / 2f), 25, levelPack.getName(), color);
                graphics.scale(2 / 3f, 2 / 3f);

                String levelString = "Level " + (currentLevelId + 1) + "/" + levelPack.getLevels().size();
                Font.getFont().drawString(Font.getCenteredStartX(levelString, 1f), 150, levelString, color);


                levelString = level.getLevelName();
                Font.getFont().drawString(Font.getCenteredStartX(levelString, 1f), 200, levelString, color);

                if(introAlpha <= 0f) {
                    if(currentLevelId == 0)
                        intro = 0;
                    else {
                        intro = 5;
                        outroSpinSpeed = 2f;
                        Player.spinAnim.setSpeed(2f);
                    }
                }
            } else if(intro == 5) {
                if(player.y < level.getStartPoint().getAbsolutePosition()[1] * 32)
                    player.y += 2;
                else {
                    if(outroSpinSpeed > 0.5f) {
                        outroSpinSpeed -= 0.005f;
                        Player.spinAnim.setSpeed(outroSpinSpeed);
                    }
                    else {
                        intro = 0;
                        Player.spinAnim.setSpeed(1f);
                        player.anim = Player.iFacing[Direction.DOWN.ordinal()];
                    }
                }
            }
        }

        if(outro > 0) {
            if(outro == 2) {
                if(outroSpinSpeed < 2f)
                    outroSpinSpeed += 0.005f;

                Player.spinAnim.setSpeed(outroSpinSpeed);
                player.anim = Player.spinAnim;

                if(outroSpinSpeed >= 2f) {
                    if(player.y > -28)
                        player.y -= 2;
                    else
                        outro = 1;
                }
            } else if(outro == 1) {
                if(outroAlpha < 1f)
                    outroAlpha += 0.025f;

                graphics.setColor(new Color(0f, 0f, 0f, outroAlpha));
                graphics.fillRect(0, 0, Main.width, Main.height);

                if(outroAlpha >= 1f) {
                    outro = 0;
                    properNextLevel();
                }
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if(intro > 0) {
            if(input.isKeyPressed(Input.KEY_ESCAPE)) {
                if(intro == 5) {
                    intro = 0;
                    player.y = level.getStartPoint().getAbsolutePosition()[1] * 32;
                    Player.spinAnim.setSpeed(1f);
                    player.anim = Player.iFacing[Direction.DOWN.ordinal()];
                    return;
                }
                intro--;
                introTime = 60;
                if(intro == 2)
                    introTime = 100;
                introAlpha = 0f;
            }
            return;
        }

        if(outro > 0) {
            if(input.isKeyPressed(Input.KEY_ESCAPE)) {
                outro--;
                if(outro == 0) {
                    properNextLevel();
                }
            }
            return;
        }

        movement(input);
        player.update();
        otherInput(input);
    }

    public void movement(Input input) {
        Direction dr = null;

        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
            dr = Direction.UP;
        } else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            dr = Direction.DOWN;
        } else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
            dr = Direction.LEFT;
        } else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            dr = Direction.RIGHT;
        }

        if (dr == null) {
            player.holdingMove = false;
            return;
        }

        player.holdingMove = true;

        if (player.isMoving())
            return;

        player.move(dr);
    }

    public void otherInput(Input input) {
        if (input.isKeyPressed(Input.KEY_R)) {
            resetLevel();
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            getPlayer().pause();
            Main.game.setMenu(new PauseMenu(this));
        }
    }

}
