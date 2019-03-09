package exp.zhen.zayta.mode.quest.system.movement.movementLimitations;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;

import exp.zhen.zayta.Direction;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.mode.quest.component.labels.BlockTag;
import exp.zhen.zayta.mode.quest.component.labels.PlayerTag;
import exp.zhen.zayta.mode.quest.component.properties.movement.CircularBoundsComponent;
import exp.zhen.zayta.mode.quest.component.properties.movement.Position;
import exp.zhen.zayta.mode.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.mode.quest.system.collision.CollisionListener;

public class BlockPauseSystem extends EntitySystem  implements CollisionListener {
    //todo dont need blocks if using tiled map
    private final Family BLOCKS,PLAYER;
    
    public BlockPauseSystem(){
        super();

        PLAYER = Family.all(
                PlayerTag.class,
                CircularBoundsComponent.class,
                VelocityComponent.class,
                Position.class
        ).get();

        BLOCKS = Family.all(
                BlockTag.class,
                CircularBoundsComponent.class).get();
        
    }
    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER);//size of  players array is always 1 since we only have one player instantiated
        ImmutableArray<Entity> blocks = getEngine().getEntitiesFor(BLOCKS);
        for(Entity playerEntity: players) {
            for (Entity blockEntity : blocks){
                if(checkCollision(playerEntity,blockEntity)){
                    collideEvent(playerEntity,blockEntity);
//                    if(stones.size()==0)
//                    {
//                        RPG.userData.setLevelPassed(true);
//                    }
                }
            }
        }


    }
    private boolean checkCollision(Entity player, Entity obstacle)
    {
        CircularBoundsComponent playerBounds = Mappers.BOUNDS.get(player);
        CircularBoundsComponent obstacleBounds = Mappers.BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    @Override
    public void collideEvent(Entity nighter, Entity block) {
        VelocityComponent velocityComponent =Mappers.MOVEMENT.get(nighter);
        velocityComponent.setDirection(Direction.none);

//        Position nighterPos = Mappers.POSITION.get(nighter);
//        Position blockPos = Mappers.POSITION.get(block);
//        switch (direction)
//        {
//            case up:
//                nighterPos.setY(blockPos.getY()-2*SizeManager.maxBoundsRadius);
//                break;
//            case down:
//                nighterPos.setY(blockPos.getY()+2*SizeManager.maxBoundsRadius);
//                break;
//            case left:
//                nighterPos.setX(blockPos.getX()+2*SizeManager.maxBoundsRadius);
//                break;
//            case right:
//                nighterPos.setX(blockPos.getX()-2*SizeManager.maxBoundsRadius);
//                break;
//        }


    }
}
