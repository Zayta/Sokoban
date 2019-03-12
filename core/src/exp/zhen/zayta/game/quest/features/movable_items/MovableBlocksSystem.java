package exp.zhen.zayta.game.quest.features.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.Direction;
import exp.zhen.zayta.RPG;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.game.quest.PositionTracker;
import exp.zhen.zayta.game.quest.Quest;
import exp.zhen.zayta.game.quest.component.properties.movement.CircularBoundsComponent;
import exp.zhen.zayta.game.quest.component.properties.movement.Position;
import exp.zhen.zayta.game.quest.component.properties.movement.PositionTrackerComponent;
import exp.zhen.zayta.game.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.game.quest.entity.Arrangements;
import exp.zhen.zayta.util.BiMap;

public class MovableBlocksSystem extends EntitySystem  {

    private PooledEngine engine;
    private static final Logger log = new Logger(MovableBlocksSystem.class.getName(),Logger.DEBUG);

    private BiMap<Integer,Entity> movableBlocksBiMap;
    //families are entities that can collide
    private final Family UNDEADS;

    public MovableBlocksSystem(PooledEngine engine){
        this.engine = engine;
        UNDEADS = Family.all(
                PositionTrackerComponent.class,
                Position.class,
                VelocityComponent.class,
                CircularBoundsComponent.class
        ).get();


        movableBlocksBiMap = new BiMap<Integer, Entity>();
        initMovableBlocks();
    }

    private void initMovableBlocks(){
        int numBlocks = 5;
        Vector2[] points = Arrangements.circle(numBlocks,SizeManager.WORLD_CENTER_X,SizeManager.WORLD_CENTER_Y,SizeManager.WORLD_WIDTH/3);
        for(int i =0; i<numBlocks; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            Entity block = Quest.manufacturer.makeEntityInPos(points[i].x,points[i].y,
                    MovableTag.class,RegionNames.BACKGROUND);
            movableBlocksBiMap.put(key, block);
            VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
            block.add(velocityComponent);
        }
        log.debug("blockBiMap: "+movableBlocksBiMap);
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(UNDEADS);

        for(Entity entity: entities) {
            Direction direction = Mappers.MOVEMENT.get(entity).getDirection();
            int [] keys = new int[6];

            int key = findKey(entity);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            float newPosX, newPosY;
            switch (direction){
                case none:
                    continue;
                case up:
                    keys[0]= keyAbove;keys[1]= keyAbove+1;keys[2]= keyAbove-1;
                    keys[3] = key-1;keys[4] = key+1;
                    break;
                case down:
                    keys[0]= keyBelow;keys[1]= keyBelow+1;keys[2]= keyBelow-1;
                    keys[3] = key-1;keys[4] = key+1;
                    break;
                case left:
                    keys[0]= keyAbove-1;keys[1]= key-1;keys[2]= keyBelow-1;
                    keys[3] = keyAbove;keys[4] = keyBelow;
                    break;
                case right:
                    keys[0]= keyAbove+1;keys[1]= key+1;keys[2]= keyBelow+1;
                    keys[3] = keyAbove;keys[4] = keyBelow;
                    break;
            }
            keys[5] = key;
            checkKeysForCollision(entity,keys,direction);
//            if(checkKeysForCollision(entity,keys)){
//                pushBlock();
//            }

        }
    }

    private int findKey(Entity entity){
        return Mappers.POSITION_TRACKER.get(entity).getPositionBiMap().getBiMap().getKey(entity);
    }
    
    private boolean checkKeysForCollision(Entity entity, int [] keys,Direction direction){
        for (int key: keys) {
            Entity block = movableBlocksBiMap.get(key);

            if (block != null) {
                if (collisionBetween(entity, block)) {
                    log.debug("Block collide");
                    pushBlock(block,direction);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean collisionBetween(Entity entity, Entity block)
    {
        CircularBoundsComponent playerBounds = Mappers.BOUNDS.get(entity);
        CircularBoundsComponent blockBounds = Mappers.BOUNDS.get(block);

        return Intersector.overlaps(playerBounds.getBounds(),blockBounds.getBounds());
    }
    
    private void pushBlock(Entity block, float newPosX, float newPosY){
        Mappers.POSITION.get(block).set(newPosX,newPosY);

    }

    private void pushBlock(Entity block, Direction direction){
        Mappers.MOVEMENT.get(block).setDirection(direction);
    }

    

    public void reset() {
        movableBlocksBiMap.clear();
    }

}
