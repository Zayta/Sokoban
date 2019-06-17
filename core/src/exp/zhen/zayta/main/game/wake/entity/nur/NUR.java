package exp.zhen.zayta.main.game.wake.entity.nur;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.entity.components.NameTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.DefenseComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.wake.entity.Fighter;
import exp.zhen.zayta.main.game.wake.entity.components.labels.UndeadTag;
import exp.zhen.zayta.main.game.wake.visual.AnimationComponent;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;
import exp.zhen.zayta.util.BiMap;

public class NUR {
  
    private PooledEngine engine;
    private TextureAtlas wakePlayAtlas;
    private BiMap <Undead, Fighter> nighters;
//    public NUR(PooledEngine engine, TextureRegion textureRegion, int hp, int atk, int def){
//        this.engine = engine;
//        initIdentityComponents();
//        initAnimationComponents(textureRegion);
//        initBattleComponents(hp,atk,def);
//    }

    public NUR(PooledEngine engine, TextureAtlas wakePlayAtlas){
        this.engine = engine;
        this.wakePlayAtlas = wakePlayAtlas;
        nighters = new BiMap<Undead, Fighter>();
        initNighters();
    }
    
    private void initNighters(){
        nighters.put(Undead.Lorale, new Fighter("Lorale",
                wakePlayAtlas.findRegion(WPRegionNames.LORALE),
                100,10,10));    
    }
    
    
    
    
    

    public Entity getNighter(Undead undead){
        Entity nighter = engine.createEntity();
        Fighter fighter = nighters.get(undead);

        addIdentityComponents(nighter,fighter.getName());
        addAnimationComponents(nighter,fighter.getTextureRegion());
        addHealthComponent(nighter,fighter.getHp());
        return nighter;
    }

    public Entity getWakeNighter(Undead undead){
        Entity nighter = engine.createEntity();
        Fighter fighter = nighters.get(undead);

        addIdentityComponents(nighter,fighter.getName());
        addAnimationComponents(nighter,fighter.getTextureRegion());
        addHealthComponent(nighter,fighter.getHp());
        return nighter;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**Components of a Nighter**/
    private void addIdentityComponents(Entity nighter){
        UndeadTag undeadTag = engine.createComponent(UndeadTag.class);
        NighterTag nighterTag = engine.createComponent(NighterTag.class);
        nighter.add(undeadTag);
        nighter.add(nighterTag);
    }
    private void addIdentityComponents(Entity nighter,String name){
        UndeadTag undeadTag = engine.createComponent(UndeadTag.class);
        NighterTag nighterTag = engine.createComponent(NighterTag.class);
        NameTag nameTag = engine.createComponent(NameTag.class);
        nameTag.setName(name);

        nighter.add(undeadTag);
        nighter.add(nighterTag);
        nighter.add(nameTag);
    }
    private void addAnimationComponents(Entity nighter,TextureRegion textureRegion){
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.init(textureRegion);

        nighter.add(texture);
        nighter.add(animationComponent);
    }


    private void addBattleComponents(Entity monster,int hp, int atk, int def){
        addHealthComponent(monster,hp);

        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        attackComponent.init(atk);

        DefenseComponent defenseComponent = engine.createComponent(DefenseComponent.class);
        defenseComponent.init(def);

        monster.add(attackComponent);
        monster.add(defenseComponent);
    }
    private void addHealthComponent(Entity nighter,int hp){
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        healthComponent.init(hp);
        nighter.add(healthComponent);
    }

}
