package exp.zhen.zayta.game.wake_mode.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.game.wake_mode.movement.Direction;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.game.wake_mode.component.labels.NPCTag;
import exp.zhen.zayta.game.wake_mode.movement.component.Position;
import exp.zhen.zayta.game.wake_mode.movement.component.VelocityComponent;

public class MapBlocksSystem extends EntitySystem {


    private static final Logger log = new Logger(MapBlocksSystem.class.getName(), Logger.DEBUG);

    private final Family MOVING_ENTITIES;
    private TiledMapTileLayer collisionLayer;
    private float increment;
    private String blockedKey = "block";
    public MapBlocksSystem(TiledMapTileLayer collisionLayer)
    {
        MOVING_ENTITIES = Family.all(
                NPCTag.class,
                Position.class,
                VelocityComponent.class).get();
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void update(float deltaTime) {
        // calculate the increment for step in #collidesBottom() and #collidesTop()
        increment = collisionLayer.getTileHeight();
        increment = SizeManager.maxObjHeight < increment ? SizeManager.maxObjHeight / 2 : increment / 2;
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(MOVING_ENTITIES);
        for(Entity entity:entities) {

            Direction currentDirection = Mappers.MOVEMENT.get(entity).getDirection();
            if(collidedWithBlock(entity,currentDirection))
            {
                VelocityComponent movement = Mappers.MOVEMENT.get(entity);
                //if there is a block, go a random direction upon interaction. should fix later based on tile direction.
                movement.setDirection(Direction.generateDirectionExcluding(currentDirection));
            }
        }
    }

    private boolean collidedWithBlock(Entity entity, Direction currentDirection){
        float x = Mappers.POSITION.get(entity).getX();
        float y = Mappers.POSITION.get(entity).getY();

        boolean collidedWithBlock = false;

        switch (currentDirection){
            case none:
                break;
            case up:
                collidedWithBlock = collidesTop(x,y);
                break;
            case down:
                collidedWithBlock = collidesBottom(x,y);
                break;
            case left:
                collidedWithBlock = collidesLeft(x,y);
                break;
            case right:
                collidedWithBlock = collidesRight(x,y);
                break;
        }

        return collidedWithBlock;
    }


    public boolean collidesRight(float x, float y) {
        for(float step = 0; step <= SizeManager.maxObjHeight; step += increment)
            if(isCellBlocked(x + SizeManager.maxObjWidth, y + step))
                return true;
        return false;
    }

    public boolean collidesLeft(float x, float y) {
        for(float step = 0; step <= SizeManager.maxObjHeight; step += increment)
            if(isCellBlocked(x, y + step))
                return true;
        return false;
    }

    public boolean collidesTop(float x, float y) {
        for(float step = 0; step <= SizeManager.maxObjWidth; step += increment)
            if(isCellBlocked(x + step, y + SizeManager.maxObjHeight))
                return true;
        return false;

    }

    public boolean collidesBottom(float x, float y) {
        for(float step = 0; step <= SizeManager.maxObjWidth; step += increment)
            if(isCellBlocked(x + step, y))
                return true;
        return false;
    }

    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x), (int) (y));

//        log.debug("cell: "+cell.getTile().getProperties());
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }


}
