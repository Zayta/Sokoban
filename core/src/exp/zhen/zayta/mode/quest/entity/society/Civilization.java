package exp.zhen.zayta.mode.quest.entity.society;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.Direction;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.mode.quest.component.labels.id.MortalTag;
import exp.zhen.zayta.mode.quest.component.labels.NPCTag;
import exp.zhen.zayta.mode.quest.component.properties.visual.AnimationComponent;
import exp.zhen.zayta.mode.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.mode.quest.component.properties.visual.TextureComponent;
import exp.zhen.zayta.config.SpeedManager;
import exp.zhen.zayta.mode.quest.entity.MovingEntityMaker;
import exp.zhen.zayta.mode.quest.entity.GameObjectMaker;
import exp.zhen.zayta.util.BiMap;

public class Civilization extends MovingEntityMaker implements GameObjectMaker {
    private TextureAtlas gamePlayAtlas;private PooledEngine engine;
    private BiMap<Integer,Entity> civilianPos;
    public Civilization(TextureAtlas gamePlayAtlas,PooledEngine engine){
        this.gamePlayAtlas = gamePlayAtlas;
        this.engine = engine;
        civilianPos = new BiMap<Integer, Entity>();
    }

    @Override
    public void addEntityInPos(float x, float y) {
        NPCTag npcTag = engine.createComponent(NPCTag.class);

        MortalTag mortalTag = engine.createComponent(MortalTag.class);

        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setSpeed(SpeedManager.DEFAULT_SPEED,SpeedManager.DEFAULT_SPEED);
        movement.setDirection(Direction.generateRandomDirection());

        TextureComponent texture = engine.createComponent(TextureComponent.class);
//        texture.setRegion(gamePlayAtlas.findRegion(RegionNames.PLAYER));

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.init(gamePlayAtlas.findRegion(RegionNames.CIVILIAN));


        Entity entity = engine.createEntity();
        addPositionComponents(engine,entity,x,y);
        entity.add(movement);
        entity.add(npcTag);
        entity.add(mortalTag);
        entity.add(texture);
        entity.add(animationComponent);
        engine.addEntity(entity);
    }
}