package exp.zhen.zayta.game.quest.entity.undead.nur;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.quest.movement.PositionTracker;
import exp.zhen.zayta.game.quest.component.properties.battle.BattleComponent;
import exp.zhen.zayta.game.quest.component.labels.UndeadTag;
import exp.zhen.zayta.game.quest.component.properties.visual.AnimationComponent;
import exp.zhen.zayta.game.quest.component.properties.visual.TextureComponent;
public class Nighter extends Entity {
    private PooledEngine engine;
    public Nighter(PooledEngine engine, TextureRegion textureRegion, int HP, int ATK, int DEF){
        this.engine = engine;
        initIdentityComponents();
        initAnimationComponents(textureRegion);
        initBattleComponents(HP,ATK,DEF);
        
    }
    private void initIdentityComponents(){
        UndeadTag undeadTag = engine.createComponent(UndeadTag.class);
        NighterTag nighterTag = engine.createComponent(NighterTag.class);
        this.add(undeadTag);
        this.add(nighterTag);
    }

    private void initAnimationComponents(TextureRegion textureRegion){
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.init(textureRegion);

        this.add(texture);
        this.add(animationComponent);
    }

    private void initBattleComponents(int HP, int ATK, int DEF){
        BattleComponent battleComponent = engine.createComponent(BattleComponent.class);
        battleComponent.init(HP,ATK,DEF);
        this.add(battleComponent);
        
    }

    @Override
    public String toString() {
        return super.toString()+"\nKEY: " +PositionTracker.PositionBiMap.nightersBiMap.getBiMap().getKey(this) +"\nPOSITION: ("+Mappers.POSITION.get(this).getX()+","+Mappers.POSITION.get(this).getY()+")";
    }
}
