package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.lanterns;

import com.badlogic.ashley.core.Component;

public class LanternTag implements Component {
    public enum State{
        DORMANT,FLARE;
    }
    private State state = State.DORMANT;

    State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
