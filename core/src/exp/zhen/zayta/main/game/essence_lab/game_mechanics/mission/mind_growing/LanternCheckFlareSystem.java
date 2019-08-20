package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.main.game.essence_lab.Experiment;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;

public class LanternCheckFlareSystem extends EntitySystem {
    private Experiment experiment;
    private Family family = Family.all(LanternTag.class).get();
    private final ImmutableArray<Entity> lanterns;
    public LanternCheckFlareSystem(Experiment experiment, PooledEngine engine) {
        this.experiment = experiment;

        lanterns = engine.getEntitiesFor(family);
    }

    @Override
    public void update(float deltaTime) {
        boolean win = true;
        for(Entity lantern:lanterns){
            win = win && Mappers.LANTERN.get(lantern).getState()==LanternTag.State.FLARE;
        }
        if(win){
            completeMission();
        }
    }
    private void completeMission(){
//        PositionTracker.reset();
////        RPG.userData.Player.clear();
//        getEngine().removeAllEntities();
        experiment.progress();
    }

}
