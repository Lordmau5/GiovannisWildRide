package main.java.com.lordmau5.util;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class Font {

    private static AngelCodeFont font;

    public Font() {
        try {
            font = new AngelCodeFont("font/font.fnt", ImageLoader.loadImage("font/font.png"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static AngelCodeFont getFont() { return font; }

}
