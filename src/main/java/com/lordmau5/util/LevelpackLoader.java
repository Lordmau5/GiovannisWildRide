package main.java.com.lordmau5.util;

import main.java.com.lordmau5.world.level.LevelPack;

import java.io.File;
import java.util.List;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class LevelpackLoader {

    private int total = 0, remaining = 0;
    private List<File> levelpackFiles;

    public LevelpackLoader() {
        levelpackFiles = GenericUtil.getFiles("levels", "lvlPack");
        total = levelpackFiles.size();
        remaining = total;
    }

    public int getTotal() {
        return total;
    }

    public int getRemaining() {
        return remaining;
    }

    public LevelPack getNextLevelpack() {
        if(levelpackFiles.size() == 0)
            return null;

        LevelPack pack = LevelLoader.loadLevelPack(levelpackFiles.get(0).getAbsolutePath());
        levelpackFiles.remove(0);
        remaining--;
        return pack;
    }

}
