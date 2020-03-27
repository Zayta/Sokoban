package snow.zhen.zayta.versions_unused;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

@Deprecated
public abstract class MissionComponent implements Component, Pool.Poolable {

    private boolean complete;
    public void reset(){
        complete = false;
    }
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
