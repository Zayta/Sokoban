package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_npc;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.NPCTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;

public class MapBlockChangeDirectionSystem extends EntitySystem {


    private static final Logger log = new Logger(MapBlockChangeDirectionSystem.class.getName(), Logger.DEBUG);

    private final Family MOVING_ENTITIES;
    private TiledMapTileLayer collisionLayer;
    private float increment;
    private String blockedKey = "block";
    public MapBlockChangeDirectionSystem(TiledMapTileLayer collisionLayer)
    {
        MOVING_ENTITIES = Family.all(
                NPCTag.class,
                Position.class,
                exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent.class).get();
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void update(float deltaTime) {
        // calculate the increment for step in #collidesBottom() and #collidesTop()
        increment = collisionLayer.getTileHeight();
        increment = exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight < increment ? exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight / 2 : increment / 2;
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(MOVING_ENTITIES);
        for(Entity entity:entities) {

            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction currentDirection = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(entity).getDirection();
            if(collidedWithBlock(entity,currentDirection))
            {
                VelocityComponent movement = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(entity);
                //if there is a block, go a random direction upon interaction. should fix later based on tile direction.
                movement.setDirection(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.generateDirectionExcluding(currentDirection));
            }
        }
    }

    private boolean collidedWithBlock(Entity entity, Direction currentDirection){
        float x = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(entity).getX();
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
        for(float step = 0; step <= exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight; step += increment)
            if(isCellBlocked(x + exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjWidth, y + step))
                return true;
        return false;
    }

    public boolean collidesLeft(float x, float y) {
        for(float step = 0; step <= exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight; step += increment)
            if(isCellBlocked(x, y + step))
                return true;
        return false;
    }

    public boolean collidesTop(float x, float y) {
        for(float step = 0; step <= exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjWidth; step += increment)
            if(isCellBlocked(x + step, y + exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight))
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

//        //////log.debug("cell: "+cell.getTile().getProperties());
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }


}
