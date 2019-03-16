package exp.zhen.zayta.game.quest.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import exp.zhen.zayta.game.quest.movement.PositionTracker;
import exp.zhen.zayta.game.quest.movement.system.movementLimitations.world_wrap.WorldWrapTag;
import exp.zhen.zayta.game.quest.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.game.quest.movement.component.DimensionComponent;
import exp.zhen.zayta.game.quest.movement.component.Position;
import exp.zhen.zayta.game.quest.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.game.quest.movement.component.VelocityComponent;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.config.SpeedManager;

public class MovingEntityMaker {
    public void addMovementComponents(PooledEngine engine, Entity entity, float x, float y, PositionTracker.PositionBiMap posMap){
        addPositionComponents(engine,entity,x,y);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionBiMap(posMap);
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
