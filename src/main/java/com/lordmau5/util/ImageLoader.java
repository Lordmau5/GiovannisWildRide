package main.java.com.lordmau5.util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public class ImageLoader {

    public static Image loadImage(String location) {
        try {
            return new Image(location, false, Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        return null;
    }

}
