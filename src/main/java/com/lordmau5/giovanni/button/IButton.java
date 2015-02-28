package com.lordmau5.giovanni.button;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public interface IButton {

    public float[] getPosition();

    public int getWidth();

    public int getHeight();

    public Color getColor();

    public String getText();

    public String getIdentifier();

    public void onHover(boolean isHover);

    public void render(Graphics graphics);

}
