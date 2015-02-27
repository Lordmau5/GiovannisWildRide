package main.java.com.lordmau5.world.tiles.state;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class InitialState {

    private Map<String, Object> stateMap = new HashMap<>();

    public void setState(String stateName, Object object) {
        if(!stateMap.containsKey(stateName))
            stateMap.put(stateName, object);
    }

    public Object getState(String stateName) {
        if(!stateMap.containsKey(stateName))
            return null;

        return stateMap.get(stateName);
    }

}
