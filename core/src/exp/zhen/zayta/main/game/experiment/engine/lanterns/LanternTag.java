package exp.zhen.zayta.main.game.experiment.engine.lanterns;

import com.badlogic.ashley.core.Component;

public class LanternTag implements Component {
    public enum State{
        DORMANT,FLARE;
    }
    private State state = State.DORMANT;

    State getState() {
        return state;
    }

    void setState(State state) {
        this.state = state;
    }
}
