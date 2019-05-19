package exp.zhen.zayta.main.game.wake.entity.player.nur;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.game.characters.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.component.labels.UndeadTag;
import exp.zhen.zayta.main.game.wake.visual.AnimationComponent;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;
public class ConsciousNighter extends Entity {
    private PooledEngine engine;
    public ConsciousNighter(PooledEngine engine, TextureRegion textureRegion, int HP, int ATK, int DEF, int range){
        this.engine = engine;
        initIdentityComponents();
        initAnimationComponents(textureRegion);
//        initBattleComponents(HP,ATK,DEF,range);
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

//    private void initBattleComponents(int HP, int ATK, int DEF, int range){
//        BattleComponent battleComponent = engine.createComponent(BattleComponent.class);
//        battleComponent.init(HP,ATK,DEF);
//
//        RangeComponent rangeComponent = engine.createComponent(RangeComponent.class);
//        rangeComponent.setRange(range);
//        this.add(battleComponent);
//    }

    @Override
    public String toString() {
        return super.toString()+"\nKEY: " +PositionTracker.PositionBiMap.nightersBiMap.getBiMap().getKey(this) +"\nPOSITION: ("+Mappers.POSITION.get(this).getX()+","+Mappers.POSITION.get(this).getY()+")";
    }
}
