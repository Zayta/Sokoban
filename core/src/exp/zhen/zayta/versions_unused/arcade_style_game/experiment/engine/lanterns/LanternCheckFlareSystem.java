package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.lanterns;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.Experiment;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;

public class LanternCheckFlareSystem extends EntitySystem {
    private static final Logger log = new Logger(LanternCheckFlareSystem.class.getName(),Logger.DEBUG);
    private Experiment experiment;
    private PooledEngine engine;
    private Family family = Family.all(LanternTag.class).get();
    public LanternCheckFlareSystem(Experiment experiment, PooledEngine engine) {
        this.experiment = experiment;
        this.engine = engine;

    }

    @Override
    public void update(float deltaTime) {
        boolean win = true;
        ImmutableArray<Entity> lanterns = engine.getEntitiesFor(family);
//        //log.debug("lanterns size is "+lanterns.size());
        for(Entity lantern:lanterns){
            win = win && Mappers.LANTERN.get(lantern).getState()==LanternTag.State.FLARE;
//            //log.debug("lantern is "+lantern+" and has state "+Mappers.LANTERN.get(lantern).getState());
        }
        if(win){
//            //log.debug("experiment won");
            completeMission();
        }
    }
    private void completeMission(){
//        PositionTracker.reset();
////        Game.userData.Nighter.clear();
//        getEngine().removeAllEntities();
        experiment.progress();
    }

}
