package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing;

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
