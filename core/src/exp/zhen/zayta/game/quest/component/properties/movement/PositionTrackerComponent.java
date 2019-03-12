package exp.zhen.zayta.game.quest.component.properties.movement;

import com.badlogic.ashley.core.Component;

import exp.zhen.zayta.game.quest.PositionTracker;

public class PositionTrackerComponent implements Component {

    private PositionTracker.PositionBiMap positionBiMap;

    public void setPositionBiMap(PositionTracker.PositionBiMap positionBiMap) {
        this.positionBiMap = positionBiMap;
    }

    public PositionTracker.PositionBiMap getPositionBiMap() {
        return positionBiMap;
    }
}
