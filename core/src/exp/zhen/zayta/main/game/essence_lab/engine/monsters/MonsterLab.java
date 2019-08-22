package exp.zhen.zayta.main.game.essence_lab.engine.monsters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.config.SpeedManager;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.components.NameTag;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.components.labels.UndeadTag;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.components.properties.DefenseComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.id_tags.MonsterTag;
import exp.zhen.zayta.main.game.essence_lab.engine.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.main.game.movable_items.components.PushComponent;
import exp.zhen.zayta.util.KeyListMap;

public class MonsterLab extends EntitySystem {

    private PooledEngine engine; private TextureAtlas labAtlas;

    private KeyListMap<Integer,Entity> monstersKeyListMap;
    public MonsterLab(PooledEngine engine, TextureAtlas labAtlas){
        this.engine = engine;
        this.labAtlas = labAtlas;
        monstersKeyListMap = new KeyListMap<Integer, Entity>();

    }

    /**For monsters**/
    void addMonsters(int numMonsters){
        //todo also in future make civilians change direction randomly
        /*add Monsters*/
        float minX = 0; float maxX = SizeManager.WAKE_WORLD_WIDTH-SizeManager.maxObjWidth;
        float minY = 0; float maxY = SizeManager.WAKE_WORLD_HEIGHT-SizeManager.maxObjHeight;
        for(int i = 0; i<numMonsters; i++) {
            float civX = MathUtils.random(minX,maxX);
            float civY = MathUtils.random(minY,maxY);
            addMonster(civX, civY);
        }

    }
    private void addMonster(float x, float y){
        Entity entity = generateMonster(1);
        Position position = engine.createComponent(Position.class);
        position.set(x,y);
        entity.add(position);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);
        entity.add(dimension);


        RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());
        entity.add(bounds);


        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());
        entity.add(worldWrap);


        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(monstersKeyListMap);
        entity.add(positionTrackerComponent);

        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setSpeed(SpeedManager.DEFAULT_SPEED,SpeedManager.DEFAULT_SPEED);
        entity.add(movement);

        MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);



        PushComponent pocketComponent = engine.createComponent(PushComponent.class);
        entity.add(pocketComponent);


        AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);





        engine.addEntity(entity);

        Mappers.MOVEMENT.get(entity).setDirection(Direction.generateRandomDirection());
    }





    /*Monster specific*/
    private Entity generateMonster(int lvl){
        int index = lvl%WPRegionNames.MONSTERS.length;
        Entity monster = engine.createEntity();
        addIdentityComponents(monster);
        addAnimationComponents(monster,labAtlas.findRegion(WPRegionNames.MONSTERS[index]));
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
