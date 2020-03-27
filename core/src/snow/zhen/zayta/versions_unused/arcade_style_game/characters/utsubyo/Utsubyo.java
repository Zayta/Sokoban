package snow.zhen.zayta.versions_unused.arcade_style_game.characters.utsubyo;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.util.KeyListMap;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.NameTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.NPCTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.UndeadTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.AttackComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.DefenseComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.id_tags.MonsterTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite.SpriteAnimationComponent;

public class Utsubyo {

    private PooledEngine engine;
    private TextureAtlas labAtlas;
    private KeyListMap <snow.zhen.zayta.versions_unused.arcade_style_game.characters.utsubyo.Monster, Stats> monsters;

    public Utsubyo(PooledEngine engine, TextureAtlas labAtlas){
        this.engine = engine;
        this.labAtlas = labAtlas;
        monsters = new KeyListMap<snow.zhen.zayta.versions_unused.arcade_style_game.characters.utsubyo.Monster, Stats>();
        initMonsters();
    }

    private void initMonsters(){
        monsters.put(snow.zhen.zayta.versions_unused.arcade_style_game.characters.utsubyo.Monster.Argon, new Stats("Argon",
                labAtlas.findRegion(RegionNames.CIVILIAN),
                100,10,10));
    }






    public Entity getMonster(Monster monster){
        Entity m = engine.createEntity();
        Stats stats = monsters.get(monster);

        addIdentityComponents(m, stats.getName());
        addAnimationComponents(m, stats.getTextureRegion());
        addBattleComponents(m, stats.getHp(), stats.getAtk(), stats.getDef());
        return m;
    }

    public Entity generateMonster(int lvl){
        int index = lvl%RegionNames.MONSTERS.length;
        Entity monster = engine.createEntity();
        addIdentityComponents(monster);
        addAnimationComponents(monster,labAtlas.findRegion(RegionNames.MONSTERS[index]));
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

        SpriteAnimationComponent spriteAnimationComponent = engine.createComponent(SpriteAnimationComponent.class);
        spriteAnimationComponent.init(textureRegion);

        monster.add(texture);
        monster.add(spriteAnimationComponent);
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
