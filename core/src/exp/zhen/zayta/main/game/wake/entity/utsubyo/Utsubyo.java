package exp.zhen.zayta.main.game.wake.entity.utsubyo;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.game.characters.Monster;
import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.entity.components.NameTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.id_tags.MonsterTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.DefenseComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.wake.entity.Fighter;
import exp.zhen.zayta.main.game.wake.entity.components.labels.UndeadTag;
import exp.zhen.zayta.main.game.wake.visual.AnimationComponent;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;
import exp.zhen.zayta.util.BiMap;

public class Utsubyo {

    private PooledEngine engine;
    private TextureAtlas wakePlayAtlas;
    private BiMap <Monster, Fighter> monsters;
//    public NUR(PooledEngine engine, TextureRegion textureRegion, int hp, int atk, int def){
//        this.engine = engine;
//        initIdentityComponents();
//        initAnimationComponents(textureRegion);
//        initBattleComponents(hp,atk,def);
//    }

    public Utsubyo(PooledEngine engine, TextureAtlas wakePlayAtlas){
        this.engine = engine;
        this.wakePlayAtlas = wakePlayAtlas;
        monsters = new BiMap<Monster, Fighter>();
        initMonsters();
    }

    private void initMonsters(){
        monsters.put(Monster.Argon, new Fighter("Argon",
                wakePlayAtlas.findRegion(WPRegionNames.CIVILIAN),
                100,10,10));
    }






    public Entity getMonster(Monster monster){
        Entity m = engine.createEntity();
        Fighter fighter = monsters.get(monster);

        addIdentityComponents(m,fighter.getName());
        addAnimationComponents(m,fighter.getTextureRegion());
        addBattleComponents(m,fighter.getHp(),fighter.getAtk(),fighter.getDef());
        return m;
    }

    public Entity generateMonster(int lvl){
        int index = lvl%WPRegionNames.MONSTERS.length;
        Entity monster = engine.createEntity();
        addIdentityComponents(monster);
        addAnimationComponents(monster,wakePlayAtlas.findRegion(WPRegionNames.MONSTERS[index]));
        addBattleComponents(monster,lvl*100+10,lvl*10,lvl*10);
        return monster;
    }



















    /**Components of a monster**/
    private void addIdentityComponents(Entity monster){
        NPCTag npcTag = engine.createComponent(NPCTag.class);
        UndeadTag undeadTag = engine.createComponent(UndeadTag.class);
        MonsterTag monsterTag = engine.createComponent(MonsterTag.class);

        monster.add(npcTag);
        monster.add(undeadTag);
        monster.add(monsterTag);
    }
    private void addIdentityComponents(Entity monster,String name){
        NPCTag npcTag = engine.createComponent(NPCTag.class);
        UndeadTag undeadTag = engine.createComponent(UndeadTag.class);
        MonsterTag monsterTag = engine.createComponent(MonsterTag.class);
        NameTag nameTag = engine.createComponent(NameTag.class);
        nameTag.setName(name);

        monster.add(npcTag);
        monster.add(undeadTag);
        monster.add(monsterTag);
        monster.add(nameTag);
    }
    private void addAnimationComponents(Entity monster,TextureRegion textureRegion){
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.init(textureRegion);

        monster.add(texture);
        monster.add(animationComponent);
    }
    private void addBattleComponents(Entity monster,int hp, int atk, int def){
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        healthComponent.init(hp);

        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        attackComponent.init(atk);

        DefenseComponent defenseComponent = engine.createComponent(DefenseComponent.class);
        defenseComponent.init(def);

        monster.add(healthComponent);
        monster.add(attackComponent);
        monster.add(defenseComponent);
    }

}
