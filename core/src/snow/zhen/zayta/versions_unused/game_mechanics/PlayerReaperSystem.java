package snow.zhen.zayta.versions_unused.game_mechanics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.Experiment;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.PlayerTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;

public class PlayerReaperSystem extends IteratingSystem {
    private static final Logger log = new Logger(PlayerReaperSystem.class.getName(),Logger.DEBUG);
    private final static Family PLAYABLE_CHARACTERS = Family.all(
            HealthComponent.class, PlayerTag.class
    ).get();
    private PooledEngine engine; private snow.zhen.zayta.versions_unused.arcade_style_game.experiment.Experiment experiment;
//    private ImmutableArray<Entity> entities;

    public PlayerReaperSystem(Experiment experiment, PooledEngine engine) {
        super(PLAYABLE_CHARACTERS);
        this.engine = engine;
        this.experiment = experiment;
    }



    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //////log.debug("\nReaper system Players.size is "+entities.size());
        if(getEntities().size()<=0)
            setGameOver();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.HEALTH.get(entity).getHp()<=0){

            PositionTrackerComponent positionTracker = Mappers.POSITION_TRACKER.get(entity);
            if(positionTracker!=null){
                positionTracker.getPositionKeyListMap().remove(entity);
            }
            engine.removeEntity(entity);
        }
    }

    private void setGameOver(){
        experiment.fail();
    }
}
