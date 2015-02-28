package com.lordmau5.giovanni.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;

/**
 * Created by Lordmau5 on 24.02.2015.
 */
public interface Entity {

    public boolean canPassThrough(Entity entity);

    public Renderable getRenderer();

    public Image getImage();

    public int[] getRelativePosition();

}
