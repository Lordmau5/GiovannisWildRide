package com.lordmau5.giovanni.util;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class Font {

    private static AngelCodeFont font;
    private static String specialChars = "?/&!)(][;|:,~._-";

    public Font() {
        try {
            font = new AngelCodeFont("res/font/font.fnt", ImageLoader.loadImage("font/font.png"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static AngelCodeFont getFont() {
        return font;
    }

    public static float getCenteredStartX(String text, float scale) {
        float width = Font.getFont().getWidth(text);
        return 1024 / 2 / scale - width / 2;
    }

    public static boolean isSpecialChar(char c) {
        for (char xC : specialChars.toCharArray())
            if (xC == c)
                return true;
        return false;
    }

}
