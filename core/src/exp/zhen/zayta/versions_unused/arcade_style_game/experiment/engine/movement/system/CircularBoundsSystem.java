package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;


import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;

public class CircularBoundsSystem extends IteratingSystem {
    private static final Logger log = new Logger(CircularBoundsSystem.class.getName(),Logger.DEBUG);

    private static Family FAMILY= Family.all(
            CircularBoundsComponent.class,
            Position.class,
            DimensionComponent.class
    ).get();

    public CircularBoundsSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        /*Sets new bounds*/
        CircularBoundsComponent bounds = Mappers.CIRCULAR_BOUNDS.get(entity);
        Position position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        float newBoundx = position.getX()+ dimension.getWidth()/2;
        float newBoundy = position.getY()+dimension.getHeight()/2;
        bounds.setBounds(newBoundx,newBoundy);
    }



}
