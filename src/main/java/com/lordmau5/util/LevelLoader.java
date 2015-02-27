package main.java.com.lordmau5.util;

import main.java.com.lordmau5.world.level.Level;
import main.java.com.lordmau5.world.level.LevelPack;
import main.java.com.lordmau5.world.tiles.WorldTile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class LevelLoader {

    public static LevelPack loadLevelPack(String path) {
        List<Level> levels = new ArrayList<>();

        String name = null;
        Level level = null;

        List<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String line;
            while((line = br.readLine()) != null) {
                lines.add(GenericUtil.decrypt(line));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String line : lines) {
            if(line.startsWith("PackName:")) {
                name = line.substring(line.indexOf(":") + 1);
            }
            else if(line.startsWith("Name:")) {
                level = new Level(line.substring(line.indexOf(":") + 1));
            }
            else if(line.startsWith("StartEnd:")) {
                line = line.substring(line.indexOf(":") + 1);
                String[] split = line.split(";");
                String[] start = split[0].split(",");
                String[] end = split[1].split(",");

                level.setStartPoint(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
                level.setEndPoint(Integer.parseInt(end[0]), Integer.parseInt(end[1]));

                levels.add(level);
            }
            else {
                String[] split = line.split(";");
                for (String xString : split) {
                    String[] oSplit = xString.split(",");

                    level.loadTileAt(oSplit);
                }
            }
        }

        return new LevelPack(name, levels);
    }

    public static void saveLevelPack(LevelPack levelPack, String path) {
        File file = new File(path.substring(0, path.lastIndexOf("/")));
        if(!file.exists())
            file.mkdirs();

        List<Level> levels = levelPack.getLevels();
        List<String> lines = new ArrayList<>();

        lines.add("PackName:" + levelPack.getName());

        for(Level level : levels) {
            List<WorldTile> tiles = level.getWorldTiles();

            String currentLine = "";
            lines.add("Name:" + level.getLevelName());

            for(WorldTile tile : tiles) {
                currentLine = currentLine + tile.getSaveString() + ";";
            }
            lines.add(currentLine);
            int[] pos = level.getStartPoint().getAbsolutePosition();
            int[] endPos = level.getEndPoint().getAbsolutePosition();
            lines.add("StartEnd:" + pos[0] + "," + pos[1] + ";" + endPos[0] + "," + endPos[1] + ";");
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path, "UTF-8");
            for(String line : lines)
                writer.println(GenericUtil.encrypt(line));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if(writer != null)
                writer.close();
        }
    }

}
