package exp.zhen.zayta.main.game.wake.entity.nur;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.entity.components.NameTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.BattleComponent;
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
        addBattleComponents(nighter,fighter.getHp(),fighter.getAtk(),fighter.getDef());
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
    private void addBattleComponents(Entity nighter,int hp, int atk, int def){
        BattleComponent battleComponent = engine.createComponent(BattleComponent.class);
        battleComponent.init(hp,atk,def);
        nighter.add(battleComponent);
    }

}
