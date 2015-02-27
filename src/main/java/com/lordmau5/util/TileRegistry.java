package main.java.com.lordmau5.util;

import main.java.com.lordmau5.world.Tile;
import main.java.com.lordmau5.world.tiles.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class TileRegistry {

    private static Map<String, Class<? extends WorldTile>> tileMap = new HashMap<>();
    private static List<WorldTile> tileVariations = new ArrayList<>();

    public static List<WorldTile> getTileMap() {
        return tileVariations;
    }

    public static void registerTileVariations() {
        tileVariations.add(new StartEndPoint(0, 0, true));
        tileVariations.add(new StartEndPoint(0, 0, false));

        tileVariations.add(new Floor(0, 0));
        for(int i=0; i<Wall.getWallAmount(); i++)
            tileVariations.add(new Wall(0, 0, i));

        for(Direction dr : Direction.values())
            tileVariations.add(new Spin(0, 0, dr));
        tileVariations.add(new SpinStop(0, 0));
    }

    public static void registerTile(Class<? extends WorldTile> tClass) {
        String tileName = tClass.getSimpleName();
        if(!tileMap.containsKey(tileName))
            tileMap.put(tileName, tClass);
    }

    public static WorldTile createInstance(Tile position, String tileName) {
        Class<? extends WorldTile> tClass = tileNameToClass(tileName);
        if(tClass == null)
            return null;

        int[] pos = position.getAbsolutePosition();

        WorldTile tile = null;
        try {
            tile = tClass.getConstructor(int.class, int.class).newInstance(pos[0], pos[1]);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return tile;
    }

    public static Class<? extends WorldTile> tileNameToClass(String tileName) {
        if(!tileMap.containsKey(tileName))
            return null;

        return tileMap.get(tileName);
    }

    public static String classToTileName(Class<WorldTile> tClass) {
        for(Map.Entry<String, Class<? extends WorldTile>> entry : tileMap.entrySet()) {
            if(entry.getValue().getSimpleName().equals(tClass.getSimpleName()))
                return entry.getKey();
        }
        return null;
    }

}
