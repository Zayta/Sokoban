package exp.zhen.zayta.main.game.wake.entity.game_objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.main.game.wake.visual.TextureComponent;
import exp.zhen.zayta.main.game.wake.entity.MovingEntityMaker;

public class Manufacturer extends MovingEntityMaker {
    private TextureAtlas wakePlayAtlas;private PooledEngine engine;
    public Manufacturer(TextureAtlas wakePlayAtlas, PooledEngine engine){
        this.wakePlayAtlas = wakePlayAtlas;
        this.engine = engine;
    }


    public Entity makeEntityInPos(float x, float y,java.lang.Class componentType, String regionName) {

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.setRegion(wakePlayAtlas.findRegion(regionName));

        Entity entity = engine.createEntity();
        addPositionComponents(engine,entity,x,y);
        entity.add(engine.createComponent(componentType));//adds identifier
        entity.add(texture);
        engine.addEntity(entity);

        return entity;
    }
}
