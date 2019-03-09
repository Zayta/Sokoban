package exp.zhen.zayta.mode.quest.system.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;


import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.mode.quest.component.properties.movement.CircularBoundsComponent;
import exp.zhen.zayta.mode.quest.component.properties.movement.DimensionComponent;
import exp.zhen.zayta.mode.quest.component.properties.movement.Position;

public class BoundsSystem extends IteratingSystem {
    private static final Logger log = new Logger(BoundsSystem.class.getName(),Logger.DEBUG);

    private static Family FAMILY= Family.all(
            CircularBoundsComponent.class,
            Position.class,
            DimensionComponent.class
    ).get();

    public BoundsSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        /*Sets new bounds*/
        CircularBoundsComponent bounds = Mappers.BOUNDS.get(entity);
        Position position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        float newBoundx = position.getX() + dimension.getWidth()/2;
        float newBoundy = position.getY()+dimension.getHeight()/2;
        bounds.setBounds(newBoundx,newBoundy);
    }



}
