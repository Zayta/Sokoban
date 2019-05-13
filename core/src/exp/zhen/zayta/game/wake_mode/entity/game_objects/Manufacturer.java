package exp.zhen.zayta.game.wake_mode.entity.game_objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.game.wake_mode.visual.TextureComponent;
import exp.zhen.zayta.game.wake_mode.entity.MovingEntityMaker;

public class Manufacturer extends MovingEntityMaker {
    private TextureAtlas gamePlayAtlas;private PooledEngine engine;
    public Manufacturer(TextureAtlas gamePlayAtlas, PooledEngine engine){
        this.gamePlayAtlas = gamePlayAtlas;
        this.engine = engine;
    }


    public Entity makeEntityInPos(float x, float y,java.lang.Class componentType, String regionName) {

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.setRegion(gamePlayAtlas.findRegion(regionName));

        Entity entity = engine.createEntity();
        addPositionComponents(engine,entity,x,y);
        entity.add(engine.createComponent(componentType));//adds identifier
        entity.add(texture);
        engine.addEntity(entity);

        return entity;
    }
}
