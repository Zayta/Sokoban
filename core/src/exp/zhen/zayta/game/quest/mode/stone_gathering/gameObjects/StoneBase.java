package exp.zhen.zayta.game.quest.mode.stone_gathering.gameObjects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.game.quest.component.properties.visual.TextureComponent;
import exp.zhen.zayta.game.quest.entity.GameObjectMaker;
import exp.zhen.zayta.game.quest.entity.MovingEntityMaker;

public class StoneBase extends MovingEntityMaker {
    private TextureAtlas gamePlayAtlas;private PooledEngine engine;
    public StoneBase(TextureAtlas gamePlayAtlas, PooledEngine engine){
        this.gamePlayAtlas = gamePlayAtlas;
        this.engine = engine;
    }


    public Entity makeEntityInPos(float x, float y) {
        StoneTag stone = engine.createComponent(StoneTag.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.setRegion(gamePlayAtlas.findRegion(RegionNames.STONE));

        Entity entity = engine.createEntity();
        addPositionComponents(engine,entity,x,y);
        entity.add(stone);
        entity.add(texture);
        engine.addEntity(entity);

        return entity;
    }
}
