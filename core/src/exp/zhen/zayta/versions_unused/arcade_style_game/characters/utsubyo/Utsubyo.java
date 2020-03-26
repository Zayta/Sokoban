package exp.zhen.zayta.versions_unused.arcade_style_game.characters.utsubyo;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.util.KeyListMap;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.NameTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.NPCTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.UndeadTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.AttackComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.DefenseComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.id_tags.MonsterTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite.SpriteAnimationComponent;

public class Utsubyo {

    private PooledEngine engine;
    private TextureAtlas labAtlas;
    private KeyListMap <exp.zhen.zayta.versions_unused.arcade_style_game.characters.utsubyo.Monster, exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats> monsters;

    public Utsubyo(PooledEngine engine, TextureAtlas labAtlas){
        this.engine = engine;
        this.labAtlas = labAtlas;
        monsters = new KeyListMap<exp.zhen.zayta.versions_unused.arcade_style_game.characters.utsubyo.Monster, exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats>();
        initMonsters();
    }

    private void initMonsters(){
        monsters.put(exp.zhen.zayta.versions_unused.arcade_style_game.characters.utsubyo.Monster.Argon, new exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats("Argon",
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
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.NPCTag npcTag = engine.createComponent(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.NPCTag.class);
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.UndeadTag undeadTag = engine.createComponent(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.UndeadTag.class);
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.id_tags.MonsterTag monsterTag = engine.createComponent(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.id_tags.MonsterTag.class);

        monster.add(npcTag);
        monster.add(undeadTag);
        monster.add(monsterTag);
    }
    private void addIdentityComponents(Entity monster,String name){
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.NPCTag npcTag = engine.createComponent(NPCTag.class);
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.UndeadTag undeadTag = engine.createComponent(UndeadTag.class);
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.id_tags.MonsterTag monsterTag = engine.createComponent(MonsterTag.class);
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.NameTag nameTag = engine.createComponent(NameTag.class);
        nameTag.setName(name);

        monster.add(npcTag);
        monster.add(undeadTag);
        monster.add(monsterTag);
        monster.add(nameTag);
    }
    private void addAnimationComponents(Entity monster,TextureRegion textureRegion){
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent texture = engine.createComponent(TextureComponent.class);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite.SpriteAnimationComponent spriteAnimationComponent = engine.createComponent(SpriteAnimationComponent.class);
        spriteAnimationComponent.init(textureRegion);

        monster.add(texture);
        monster.add(spriteAnimationComponent);
    }
    private void addBattleComponents(Entity monster,int hp, int atk, int def){
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        healthComponent.init(hp);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        attackComponent.init(atk);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.DefenseComponent defenseComponent = engine.createComponent(DefenseComponent.class);
        defenseComponent.init(def);

        monster.add(healthComponent);
        monster.add(attackComponent);
        monster.add(defenseComponent);
    }

}
