package main.java.com.lordmau5.util;

import main.java.com.lordmau5.world.Level;
import main.java.com.lordmau5.world.tiles.WorldTile;

import java.io.*;
import java.util.List;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class LevelLoader {

    public static Level loadLevel(String path) {
        //List<WorldTile> tiles = new ArrayList<>();
        Level level = new Level();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("StartEnd:")) {
                    line = line.substring(line.indexOf(":") + 1);
                    String[] split = line.split(";");
                    String[] start = split[0].split(",");
                    String[] end = split[1].split(",");

                    level.setStartPoint(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
                    level.setEndPoint(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
                }
                else {
                    String[] split = line.split(";");
                    for (String xString : split) {
                        String[] oSplit = xString.split(",");

                        level.loadTileAt(oSplit);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return level;
    }

    public static void saveLevel(Level level, String path) {
        List<WorldTile> tiles = level.getWorldTiles();

        File file = new File(path.substring(0, path.lastIndexOf("/")));
        if(!file.exists())
            file.mkdirs();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path, "UTF-8");

            int lineFull = 0;
            for(WorldTile tile : tiles) {
                if(lineFull == 20) {
                    writer.println(tile.getSaveString() + ";");
                    lineFull = 0;
                }
                else {
                    writer.print(tile.getSaveString() + ";");
                    lineFull++;
                }
            }
            int[] pos = level.getStartPoint().getAbsolutePosition();
            int[] endPos = level.getEndPoint().getAbsolutePosition();
            writer.println();
            writer.print("StartEnd:" + pos[0] + "," + pos[1] + ";" + endPos[0] + "," + endPos[1] + ";");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if(writer != null)
                writer.close();
        }
    }

}
