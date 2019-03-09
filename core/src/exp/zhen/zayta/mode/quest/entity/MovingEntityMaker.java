package exp.zhen.zayta.mode.quest.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import exp.zhen.zayta.mode.quest.component.labels.WorldWrapTag;
import exp.zhen.zayta.mode.quest.component.properties.movement.CircularBoundsComponent;
import exp.zhen.zayta.mode.quest.component.properties.movement.DimensionComponent;
import exp.zhen.zayta.mode.quest.component.properties.movement.Position;
import exp.zhen.zayta.mode.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.config.SpeedManager;

public class MovingEntityMaker {
    public void addMovementComponents(PooledEngine engine, Entity entity, float x, float y){
        addPositionComponents(engine,entity,x,y);

        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setSpeed(SpeedManager.DEFAULT_SPEED,SpeedManager.DEFAULT_SPEED);
        entity.add(movement);

    }


    public void addPositionComponents(PooledEngine engine, Entity entity, float x, float y){
        Position position = new Position(entity);
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
