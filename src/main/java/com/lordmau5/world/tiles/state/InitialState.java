package main.java.com.lordmau5.world.tiles.state;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lordmau5 on 27.02.2015.
 */
public class InitialState {

    // TODO: System for proper state-saving/loading
    private Map<String, Object> stateMap = new HashMap<>();

    public InitialState() {

    }

    public InitialState(String[] stateNames, Object[] objects) {
        for(int i=0; i<stateNames.length; i++) {
            if(objects[i] != null)
                stateMap.put(stateNames[i], objects[i]);
        }
    }

    public void addState(String stateName, Object object) {
        if(!stateMap.containsKey(stateName))
            stateMap.put(stateName, object);
    }

    public Object getState(String stateName) {
        if(!stateMap.containsKey(stateName))
            return null;

        return stateMap.get(stateName);
    }

}
