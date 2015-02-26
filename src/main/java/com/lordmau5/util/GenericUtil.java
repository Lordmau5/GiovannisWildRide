package main.java.com.lordmau5.util;

import main.java.com.lordmau5.world.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class GenericUtil {

    public static List<Level> getLevels() {
        List<File> levelFiles = new ArrayList<>();
        listf("levels", levelFiles, ".level");

        List<Level> levels = new ArrayList<>();
        for(File file : levelFiles) {
            levels.add(LevelLoader.loadLevel(file.getAbsolutePath()));
        }
        return levels;
    }

    public static void listf(String directoryName, List<File> files, String ext) {
        File directory = new File(directoryName);
        if(!directory.exists())
            directory.mkdirs();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith("." + ext)) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files, ext);
            }
        }
    }

}
