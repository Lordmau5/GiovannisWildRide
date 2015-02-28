package com.lordmau5.giovanni.world.level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class LevelPack {

    private String name;
    private List<Level> levels = new ArrayList<>();

    public LevelPack(String name) {
        this.name = name;
    }

    public LevelPack(String name, List<Level> levels) {
        this(name);
        this.levels = levels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addLevel(Level level) {
        for (Level xLevel : levels) {
            if (xLevel.getLevelName().equalsIgnoreCase(level.getLevelName()))
                return;
        }
        levels.add(level);
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Level getFirstLevel() {
        return levels.get(0).getCopy();
    }

    public Level resetLevel(int currentLevel) {
        return levels.get(currentLevel).getCopy();
    }

    public Level getNextLevel(int currentLevel) {
        if (currentLevel + 1 >= levels.size())
            return null;

        return levels.get(currentLevel + 1).getCopy();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LevelPack))
            return false;
        LevelPack oPack = (LevelPack) obj;
        return oPack.getName().equals(getName());
    }
}
