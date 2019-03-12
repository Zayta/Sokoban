package exp.zhen.zayta.game.quest.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.assets.AssetDescriptors;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.game.quest.component.properties.movement.DimensionComponent;
import exp.zhen.zayta.game.quest.component.properties.movement.Position;
import exp.zhen.zayta.game.quest.component.properties.visual.TextureComponent;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.game.quest.entity.society.Civilization;
import exp.zhen.zayta.game.quest.entity.undead.nur.NUR;

public class EntityFactory {
    private final PooledEngine engine;
    private final AssetManager assetManager;
    private final TextureAtlas gamePlayAtlas;
    private EntityPositioner nur, civilization;
    public EntityFactory(PooledEngine engine, AssetManager assetManager)
    {
        this.engine = engine;
        this.assetManager=assetManager;
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        initHQs();
    }
    private void initHQs(){
        nur = new NUR(gamePlayAtlas,engine);
        civilization = new Civilization(gamePlayAtlas,engine);
    }
    public void addPlayer(float x, float y)
    {
        nur.addEntityInPos(x,y);
    }
    public void addCivilian(float x, float y){
        civilization.addEntityInPos(x,y);
    }

    private void commonBackground(){
        TextureComponent texture=engine.createComponent(TextureComponent.class);
        texture.setRegion(gamePlayAtlas.findRegion(RegionNames.BACKGROUND));

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.WORLD_WIDTH,SizeManager.WORLD_HEIGHT);

        Entity entity = engine.createEntity();
        entity.add(texture);
        Position position = engine.createComponent(Position.class);
        entity.add(position);
        entity.add(dimension);

        engine.addEntity(entity);
    }

}
