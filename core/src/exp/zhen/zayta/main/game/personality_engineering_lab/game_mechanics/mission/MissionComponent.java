package exp.zhen.zayta.main.game.personality_engineering_lab.game_mechanics.mission;

import com.badlogic.ashley.core.Component;

@Deprecated
public abstract class MissionComponent implements Component {

    private boolean complete;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    //    private final static Family PLAYABLE_CHARACTERS = Family.all(
//            PlayerTag.class
//    ).get();
//
//    private ImmutableArray<Entity> entities;
//    public MissionComponent(PooledEngine engine){
//        entities = engine.getEntitiesFor(PLAYABLE_CHARACTERS);
//    }
//
//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//    }
}
