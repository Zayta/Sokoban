package exp.zhen.zayta.main.game.wake.entity.society;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.characters.id_tags.MortalTag;
import exp.zhen.zayta.main.game.wake.component.labels.NPCTag;
import exp.zhen.zayta.main.game.wake.visual.AnimationComponent;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;
import exp.zhen.zayta.main.game.wake.entity.MovingEntityMaker;
import exp.zhen.zayta.main.game.wake.entity.EntityPositioner;

public class Civilization extends MovingEntityMaker implements EntityPositioner {
    private TextureAtlas wakePlayAtlas;private PooledEngine engine;
    public Civilization(TextureAtlas wakePlayAtlas,PooledEngine engine){
        this.wakePlayAtlas = wakePlayAtlas;
        this.engine = engine;
    }

    @Override
    public void addEntityInPos(float x, float y) {
        NPCTag npcTag = engine.createComponent(NPCTag.class);

        MortalTag mortalTag = engine.createComponent(MortalTag.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.init(wakePlayAtlas.findRegion(RegionNames.WakePlay.CIVILIAN));


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