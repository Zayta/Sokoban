package exp.zhen.zayta.main.game.wake.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.assets.AssetDescriptors;
import exp.zhen.zayta.main.game.wake.assets.WakePlayRegionNames;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.entity.society.Civilization;
import exp.zhen.zayta.main.game.wake.entity.player.nur.NUR_Awake_List;

public class EntityFactory {
    private final PooledEngine engine;
    private final AssetManager assetManager;
    private final TextureAtlas wakePlayAtlas;
    private EntityPositioner nur, civilization;
    public EntityFactory(PooledEngine engine, AssetManager assetManager)
    {
        this.engine = engine;
        this.assetManager=assetManager;
        wakePlayAtlas = assetManager.get(AssetDescriptors.WAKE_PLAY);
        initHQs();
    }
    private void initHQs(){
        nur = new NUR_Awake_List(wakePlayAtlas,engine);
        civilization = new Civilization(wakePlayAtlas,engine);
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
        texture.setRegion(wakePlayAtlas.findRegion(WakePlayRegionNames.BACKGROUND));

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.WAKE_WORLD_WIDTH,SizeManager.WAKE_WORLD_HEIGHT);

        Entity entity = engine.createEntity();
        entity.add(texture);
        Position position = engine.createComponent(Position.class);
        entity.add(position);
        entity.add(dimension);

        engine.addEntity(entity);
    }

}
