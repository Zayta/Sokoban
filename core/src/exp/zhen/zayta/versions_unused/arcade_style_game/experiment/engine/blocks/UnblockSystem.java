package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;

public class UnblockSystem extends IteratingSystem {

    private static Family family = Family.all(
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent.class,
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class
    ).get();
    public UnblockSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementLimitationComponent movementLimitationComponent =
                exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(entity);
        Entity block = movementLimitationComponent.getBlock();
        if(block!=null) {
            if(!collides(entity,block)){
                movementLimitationComponent.reset();
            }
        }
    }
    private boolean collides(Entity entity,Entity block){
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent entityBounds = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(entity);
        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(block);
        return overlaps(entityBounds.getBounds(),blockBounds.getBounds());
    }
    public static boolean overlaps (Rectangle r1,Rectangle r2) {
        return r1.x <= r2.x + r2.width && r1.x + r1.width >= r2.x && r1.y <= r2.y + r2.height && r1.y + r1.height >= r2.y;
    }
}
