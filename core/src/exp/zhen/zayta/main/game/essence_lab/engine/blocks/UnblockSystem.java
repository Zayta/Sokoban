package exp.zhen.zayta.main.game.essence_lab.engine.blocks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.RectangularBoundsComponent;

public class UnblockSystem extends IteratingSystem {

    private static Family family = Family.all(
            MovementLimitationComponent.class,
            RectangularBoundsComponent.class
    ).get();
    public UnblockSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementLimitationComponent movementLimitationComponent =
                Mappers.MOVEMENT_LIMITATION.get(entity);
        Entity block = movementLimitationComponent.getBlock();
        if(block!=null) {
            if(!collides(entity,block)){
                movementLimitationComponent.reset();
            }
        }
    }
    private boolean collides(Entity entity,Entity block){
        RectangularBoundsComponent entityBounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(block);
        return overlaps(entityBounds.getBounds(),blockBounds.getBounds());
    }
    public static boolean overlaps (Rectangle r1,Rectangle r2) {
        return r1.x <= r2.x + r2.width && r1.x + r1.width >= r2.x && r1.y <= r2.y + r2.height && r1.y + r1.height >= r2.y;
    }
}
