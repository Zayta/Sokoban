package exp.zhen.zayta.game.wake_mode.entity.society;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.game.wake_mode.movement.Direction;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.wake_mode.movement.PositionTracker;
import exp.zhen.zayta.game.wake_mode.component.labels.id.MortalTag;
import exp.zhen.zayta.game.wake_mode.component.labels.NPCTag;
import exp.zhen.zayta.game.wake_mode.visual.AnimationComponent;
import exp.zhen.zayta.game.wake_mode.visual.TextureComponent;
import exp.zhen.zayta.game.wake_mode.entity.MovingEntityMaker;
import exp.zhen.zayta.game.wake_mode.entity.EntityPositioner;

public class Civilization extends MovingEntityMaker implements EntityPositioner {
    private TextureAtlas gamePlayAtlas;private PooledEngine engine;
    public Civilization(TextureAtlas gamePlayAtlas,PooledEngine engine){
        this.gamePlayAtlas = gamePlayAtlas;
        this.engine = engine;
    }

    @Override
    public void addEntityInPos(float x, float y) {
        NPCTag npcTag = engine.createComponent(NPCTag.class);

        MortalTag mortalTag = engine.createComponent(MortalTag.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.init(gamePlayAtlas.findRegion(RegionNames.CIVILIAN));


        Entity entity = engine.createEntity();
        addMovementComponents(engine,entity,x,y,PositionTracker.PositionBiMap.civiliansBiMap);
        entity.add(npcTag);
        entity.add(mortalTag);
        entity.add(texture);
        entity.add(animationComponent);
        engine.addEntity(entity);

        Mappers.MOVEMENT.get(entity).setDirection(Direction.generateRandomDirection());
    }
}