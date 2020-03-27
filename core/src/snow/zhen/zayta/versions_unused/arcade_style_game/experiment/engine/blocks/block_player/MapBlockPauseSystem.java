package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.PlayerTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_npc.MapBlockChangeDirectionSystem;

public class MapBlockPauseSystem extends EntitySystem {


    private static final Logger log = new Logger(MapBlockChangeDirectionSystem.class.getName(), Logger.DEBUG);

    private final Family MOVING_ENTITIES;
    private TiledMapTileLayer collisionLayer;
    private float increment;
    private String blockedKey = "block";
    public MapBlockPauseSystem(TiledMapTileLayer collisionLayer)
    {
        MOVING_ENTITIES = Family.all(
                PlayerTag.class,
                Position.class,
                RectangularBoundsComponent.class,
                VelocityComponent.class).get();

        this.collisionLayer = collisionLayer;
    }

    @Override
    public void update(float deltaTime) {
        // calculate the increment for step in #collidesBottom() and #collidesTop()
        increment = collisionLayer.getTileHeight();
        increment = snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight < increment ? snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight / 2 : increment / 2;
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(MOVING_ENTITIES);
        for(Entity entity:entities) {

            VelocityComponent movement = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(entity);
            handleBlockCollision(entity, movement);


        }
    }

    private void handleBlockCollision(Entity entity, VelocityComponent movement){
        Position position = Mappers.POSITION.get(entity);

        float x = position.getX();
        float y = position.getY();
//        //////log.debug("\nCollisionRound"
//                +"\nCollidesTop: "+collidesTop(x,y)
//                +"\nCollidesBottom: "+collidesBottom(x,y)
//                +"\nCollidesLeft: "+collidesLeft(x,y)
//                +"\nCollidesRight: "+collidesRight(x,y)
//        );
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


    private boolean collidesRight(float x, float y) {
        for(float step = 0; step <= snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight; step += increment)
            if(isCellBlocked(x + snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjWidth, y + step)) {
                return true;
            }
        return false;
    }

    private boolean collidesLeft(float x, float y) {
        for(float step = 0; step <= snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight; step += increment)
            if(isCellBlocked(x, y + step))
                return true;
        return false;
    }

    private boolean collidesTop(float x, float y) {
        for(float step = 0; step <= snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjWidth; step += increment)
            if(isCellBlocked(x + step, y + snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight))
                return true;
        return false;

    }

    private boolean collidesBottom(float x, float y) {
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






//    private boolean collidesRight(float x, float y) {
//        for(float step = 0; step <= GameConfig.maxObjHeight; step += increment)
//            if(isCellBlocked(x + GameConfig.maxObjWidth, y + step))
//                return true;
//        return false;
//    }
//
//    private boolean collidesLeft(float x, float y) {
//        for(float step = 0; step <= GameConfig.maxObjHeight; step += increment)
//            if(isCellBlocked(x, y + step))
//                return true;
//        return false;
//    }
//
//    private boolean collidesTop(float x, float y) {
//        for(float step = 0; step <= GameConfig.maxObjWidth; step += increment)
//            if(isCellBlocked(x + step, y + GameConfig.maxObjHeight))
//                return true;
//        return false;
//
//    }
//
//    private boolean collidesBottom(float x, float y) {
//        for(float step = 0; step <= GameConfig.maxObjWidth; step += increment)
//            if(isCellBlocked(x + step, y))
//                return true;
//        return false;
//    }
//
//    private boolean isCellBlocked(float x, float y) {
//        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x), (int) (y));
////        //////log.debug("cell: "+cell.getTile().getProperties());
//        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
//    }


}
