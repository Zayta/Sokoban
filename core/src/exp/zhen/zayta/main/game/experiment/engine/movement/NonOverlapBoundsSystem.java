package exp.zhen.zayta.main.game.experiment.engine.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;

import exp.zhen.zayta.main.game.experiment.common.Mappers;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.RectangularBoundsComponent;

public class NonOverlapBoundsSystem extends IteratingSystem {
    private static Family family = Family.all(
            RectangularBoundsComponent.class,
            PositionTrackerComponent.class
    ).get();
    public NonOverlapBoundsSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        int key = Mappers.POSITION_TRACKER.get(entity).getPositionKeyListMap().getKey(entity);
        int keyAbove = key + PositionTracker.n;
        int keyBelow = key - PositionTracker.n;
        int [] keys = new int []{
              key,key-1,key+1,keyAbove-1,keyAbove,keyAbove+1,keyBelow-1,keyBelow,keyBelow+1
            };

        checkBounds(entity,keys);
    }

    private void checkBounds(Entity entity,int [] keys){

        for(int key: keys){
            ArrayList<Entity> neighbors = PositionTracker.globalTracker.getList(key);
            if(neighbors!=null){
                for(Entity neighbor:neighbors){
                    RectangularBoundsComponent entityBounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
                    RectangularBoundsComponent neighborBounds = Mappers.RECTANGULAR_BOUNDS.get(neighbor);

                    if(Intersector.overlaps(entityBounds.getBounds(),neighborBounds.getBounds()))
                    {
                        //clip BOunds by setting position in a place where they wont overlap
                    }
                }

            }

        }
    }
}
