package main.java.com.lordmau5;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class Main {

    public static WildRideGame game;
    public static int width = 1024, height = 768;

    public static void main(String[] args) {
        initGLStuff();
    }

    public static void initGLStuff() {
        AppGameContainer appGame;
        try {
            game = new WildRideGame();
            appGame = new AppGameContainer(game);
            appGame.setDisplayMode(width, height, false);
            appGame.setShowFPS(false);
            appGame.setVSync(true);
            appGame.setUpdateOnlyWhenVisible(true);
            appGame.setAlwaysRender(true);
            appGame.setVerbose(false);
            appGame.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
