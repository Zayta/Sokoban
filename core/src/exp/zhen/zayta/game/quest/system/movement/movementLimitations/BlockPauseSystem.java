package exp.zhen.zayta.game.quest.system.movement.movementLimitations;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.Direction;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.game.quest.component.labels.NPCTag;
import exp.zhen.zayta.game.quest.component.labels.id.NighterTag;
import exp.zhen.zayta.game.quest.component.properties.movement.Position;
import exp.zhen.zayta.game.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.game.quest.entity.undead.nur.Nighter;
import exp.zhen.zayta.game.quest.system.movement.MovementSystem;

public class BlockPauseSystem extends EntitySystem {


    private static final Logger log = new Logger(BlocksSystem.class.getName(), Logger.DEBUG);

    private final Family MOVING_ENTITIES;
    private TiledMapTileLayer collisionLayer;
    private float increment;
    private String blockedKey = "block";
    public BlockPauseSystem(TiledMapTileLayer collisionLayer)
    {
        MOVING_ENTITIES = Family.all(
                NighterTag.class,
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

            VelocityComponent movement = Mappers.MOVEMENT.get(entity);
            handleBlockCollision(entity, movement);
//            if(collidedWithBlock(entity,currentDirection))
//            {
//                VelocityComponent movement = Mappers.MOVEMENT.get(entity);
//                //if there is a block, go a random direction upon interaction. should fix later based on tile direction.
//                movement.setDirection(Direction.generateDirectionExcluding(currentDirection));
//            }
//
        }
    }

    private void handleBlockCollision(Entity entity, VelocityComponent movement){
        Position position = Mappers.POSITION.get(entity);
        float x = position.getX();
        float y = position.getY();

        //todo needa find way to fix position relative to stuck cell.
        switch (movement.getDirection()){
            case none:
                break;
            case up:
                if(collidesTop(x,y)){
                    movement.setDirection(Direction.none);
                };
                break;
            case down:
                if(collidesBottom(x,y)){
                    movement.setDirection(Direction.none);

                };
                break;
            case left:
                if(collidesLeft(x,y)){
                    movement.setDirection(Direction.none);

                };
                break;
            case right:
                if(collidesRight(x,y)){
                    movement.setDirection(Direction.none);

                };
                break;
        }
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
