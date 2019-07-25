package exp.zhen.zayta.main.game.personality_engineering_lab.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import exp.zhen.zayta.main.game.personality_engineering_lab.movement.PositionTracker;
import exp.zhen.zayta.util.BiMap;

public class PositionTrackerComponent implements Component {

    private BiMap<Integer,Entity> positionBiMap;
//    private PositionTracker.PositionBiMap positionBiMap;

    public void setPositionBiMap(PositionTracker.PositionBiMap positionBiMap) {
        this.positionBiMap = positionBiMap.getBiMap();
    }

    public void setPositionBiMap(BiMap <Integer,Entity> positionBiMap) {
        this.positionBiMap = positionBiMap;
    }

    public BiMap<Integer, Entity> getPositionBiMap() {
        return positionBiMap;
    }
}
