package main.java.com.lordmau5;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class Main {

    public static void main(String[] args) {
        initGLStuff();
    }

    public static void initGLStuff() {
        AppGameContainer game;
        try {
            game = new AppGameContainer(new WildRideGame());
            game.setDisplayMode(800, 600, false);
            game.setShowFPS(false);
            game.setVSync(true);
            game.setUpdateOnlyWhenVisible(true);
            game.setAlwaysRender(true);
            game.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
