package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import exp.zhen.zayta.util.KeyListMap;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;

public class PositionTrackerComponent implements Component {

    private KeyListMap<Integer,Entity> positionKeyListMap;
//    private PositionTracker.PositionKeyListMap positionKeyListMap;

    public void setPositionKeyListMap(PositionTracker.PositionKeyListMap positionKeyListMap) {
        this.positionKeyListMap = positionKeyListMap.getKeyListMap();
    }

    public void setPositionKeyListMap(KeyListMap <Integer,Entity> positionKeyListMap) {
        this.positionKeyListMap = positionKeyListMap;
    }

    public KeyListMap<Integer, Entity> getPositionKeyListMap() {
        return positionKeyListMap;
    }


}
