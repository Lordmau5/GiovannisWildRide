package main.java.com.lordmau5.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import main.java.com.lordmau5.world.level.LevelPack;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class GenericUtil {

    public static List<LevelPack> getLevelPacks() {
        List<File> levelpackFiles = new ArrayList<>();
        listf("levels", levelpackFiles, "lvlPack");

        List<LevelPack> levelPacks = new ArrayList<>();
        for(File file : levelpackFiles) {
            LevelPack pack = LevelLoader.loadLevelPack(file.getAbsolutePath());
            if(!levelPacks.contains(pack))
                levelPacks.add(pack);
        }
        return levelPacks;
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

    public static String encrypt(String string) {
        try {
            return Base64.encode(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String string) {
        try {
            return new String(Base64.decode(string), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
