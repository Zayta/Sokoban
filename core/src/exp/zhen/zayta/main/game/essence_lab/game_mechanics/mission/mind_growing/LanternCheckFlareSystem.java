package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.GameControllingSystem;

public class LanternCheckFlareSystem extends GameControllingSystem {
    private Family family = Family.all(LanternTag.class).get();
    private final ImmutableArray<Entity> lanterns;
    public LanternCheckFlareSystem(RPG game, PooledEngine engine) {
        super(game, engine);
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

    @Override
    public void reset() {

    }
}
