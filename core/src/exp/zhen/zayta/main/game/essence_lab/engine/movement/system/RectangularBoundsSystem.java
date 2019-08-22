package exp.zhen.zayta.main.game.essence_lab.engine.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;


import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.Position;

public class RectangularBoundsSystem extends IteratingSystem {
    private static final Logger log = new Logger(RectangularBoundsSystem.class.getName(),Logger.DEBUG);

    private static Family FAMILY= Family.all(
            RectangularBoundsComponent.class,
            Position.class,
            DimensionComponent.class
    ).get();

    public RectangularBoundsSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        /*Sets new bounds*/
        RectangularBoundsComponent bounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
        Position position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        float newBoundx = position.getX();//+ dimension.getWidth()/2;
        float newBoundy = position.getY();//+dimension.getHeight()/2;
        bounds.setBounds(newBoundx,newBoundy);
    }



}
