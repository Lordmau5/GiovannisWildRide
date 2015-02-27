package main.java.com.lordmau5.button;

import org.newdawn.slick.Color;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public interface IButton {

    public int[] getPosition();

    public int getWidth();

    public int getHeight();

    public Color getColor();

    public String getText();

    public String getIdentifier();

    public void onHover(boolean isHover);

    public void onClick(int button, boolean press);

}
