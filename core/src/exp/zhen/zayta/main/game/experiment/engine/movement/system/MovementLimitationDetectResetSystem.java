package exp.zhen.zayta.main.game.experiment.engine.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.experiment.common.Mappers;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.RectangularBoundsComponent;

public class MovementLimitationDetectResetSystem extends IteratingSystem {
    //todo added this system to debug joystick. not necessary for keyboard.
    private static final Logger log = new Logger(MovementLimitationDetectResetSystem.class.getName(),Logger.DEBUG);
    private  static Family family = Family.all(MovementLimitationComponent.class,
            RectangularBoundsComponent.class).get();
    public MovementLimitationDetectResetSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RectangularBoundsComponent entityBounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
        Entity block = Mappers.MOVEMENT_LIMITATION.get(entity).getBlock();
        if(block!=null){
            RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(block);
            if(blockBounds==null){
                log.debug("Rectangular bounds is null");
            }
            else{
                if(entityBounds.getBottom()>blockBounds.getTop()||
                        entityBounds.getTop()<blockBounds.getBottom()
                        ||entityBounds.getLeft()>blockBounds.getRight()
                        ||entityBounds.getRight()<blockBounds.getLeft()){
                    log.debug("resetting block");
                    Mappers.MOVEMENT_LIMITATION.get(entity).reset();
                }
            }
        }

    }
}
