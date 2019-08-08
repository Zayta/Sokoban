package exp.zhen.zayta.main.game.essence_lab.entity.nur;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.entity.components.NameTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.DefenseComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.Fighter;
import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.UndeadTag;
import exp.zhen.zayta.main.game.essence_lab.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.TextureComponent;
import exp.zhen.zayta.util.KeyListMap;

public class NUR {
  private static final Logger log = new Logger(NUR.class.getName(),Logger.DEBUG);
    private PooledEngine engine;
    private TextureAtlas labAtlas;
    private KeyListMap <Undead, Fighter> nighters;


    public NUR(PooledEngine engine, TextureAtlas labAtlas){
        this.engine = engine;
        this.labAtlas = labAtlas;
        nighters = new KeyListMap<Undead, Fighter>();
        initNighters();
    }
    
    private void initNighters(){
        nighters.put(Undead.Tenyu, new Fighter("Tenyu",
                labAtlas.findRegion(WPRegionNames.TENYU),
                100,10,10));
        nighters.put(Undead.Lorale, new Fighter("Lorale",
                labAtlas.findRegion(WPRegionNames.LORALE),
                100,10,10));

    }

    public Entity getNighter(Undead undead){
        Entity nighter = engine.createEntity();
        Fighter fighter = nighters.get(undead);
//        ////log.debug("Fighter is "+fighter);
        addIdentityComponents(nighter,fighter.getName());
        addAnimationComponents(nighter,fighter.getTextureRegion());
        addBattleComponents(nighter,fighter.getHp(),fighter.getAtk(),fighter.getDef());
//        addAbilities(nighter,undead.getAttributes(),fighter.getAtk());
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

        SpriteAnimationComponent spriteAnimationComponent = engine.createComponent(SpriteAnimationComponent.class);
        spriteAnimationComponent.init(textureRegion);

        nighter.add(texture);
        nighter.add(spriteAnimationComponent);
    }


    private void addBattleComponents(Entity nighter, int hp, int atk, int def){
        addHealthComponent(nighter,hp);

        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        attackComponent.init(atk);

        DefenseComponent defenseComponent = engine.createComponent(DefenseComponent.class);
        defenseComponent.init(def);

        nighter.add(attackComponent);
        nighter.add(defenseComponent);
    }

    private void addHealthComponent(Entity nighter,int hp){
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        healthComponent.init(hp);
        nighter.add(healthComponent);
    }

//    private void addAbilities(Entity nighter,ArrayList<CharacterClass> characterClasses,int abilityStrength){
//
//        for(CharacterClass characterClass : characterClasses){
//            switch (characterClass){
//                case Bomber:
//                    ExplosiveHolderComponent explosivePocket = engine.createComponent(ExplosiveHolderComponent.class);
//                    explosivePocket.setCharge(abilityStrength);
//                    nighter.add(explosivePocket);
//                    break;
//            }
//        }
//    }

    private void addColor(Undead undead, Entity entity){
        ColorComponent colorComponent = engine.createComponent(ColorComponent.class);
        switch (undead){
            case Ruzo:
                break;
            case Tenyu:
                colorComponent.setColor(Color.SKY);
                break;
            case Lorale:
                colorComponent.setColor(Color.GOLD);
                break;
            case Letra:
                colorComponent.setColor(Color.FIREBRICK);
                break;
            case Taria:
                colorComponent.setColor(Color.PURPLE);
                break;
            case Cumin:
                break;
            case Kira:
                break;
            case Foofi:
                break;

        }
        entity.add(colorComponent);
    }

}
