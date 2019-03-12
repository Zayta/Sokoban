package exp.zhen.zayta.game.quest.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import exp.zhen.zayta.game.quest.PositionTracker;
import exp.zhen.zayta.game.quest.component.labels.WorldWrapTag;
import exp.zhen.zayta.game.quest.component.properties.movement.CircularBoundsComponent;
import exp.zhen.zayta.game.quest.component.properties.movement.DimensionComponent;
import exp.zhen.zayta.game.quest.component.properties.movement.Position;
import exp.zhen.zayta.game.quest.component.properties.movement.PositionTrackerComponent;
import exp.zhen.zayta.game.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.config.SpeedManager;

public class MovingEntityMaker {
    public void addMovementComponents(PooledEngine engine, Entity entity, float x, float y, PositionTracker.PositionBiMap biMap){
        addPositionComponents(engine,entity,x,y);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionBiMap(biMap);
        entity.add(positionTrackerComponent);

        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setSpeed(SpeedManager.DEFAULT_SPEED,SpeedManager.DEFAULT_SPEED);
        entity.add(movement);

    }


    public void addPositionComponents(PooledEngine engine, Entity entity, float x, float y){
        Position position = engine.createComponent(Position.class);
        position.set(x,y);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,SizeManager.maxBoundsRadius);

        WorldWrapTag worldWrap = engine.createComponent(WorldWrapTag.class);

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);
    }


}
