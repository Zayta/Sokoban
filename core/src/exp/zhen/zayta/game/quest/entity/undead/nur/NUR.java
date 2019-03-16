package exp.zhen.zayta.game.quest.entity.undead.nur;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

import exp.zhen.zayta.game.quest.movement.PositionTracker;
import exp.zhen.zayta.game.quest.entity.MovingEntityMaker;
import exp.zhen.zayta.UserData;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.game.quest.component.labels.PlayerTag;
import exp.zhen.zayta.game.quest.entity.EntityPositioner;
import exp.zhen.zayta.game.quest.entity.undead.Undead;

public class NUR extends MovingEntityMaker implements EntityPositioner {
    private TextureAtlas gamePlayAtlas;
    private PooledEngine engine;
    public NUR(TextureAtlas gamePlayAtlas,PooledEngine engine){
        this.gamePlayAtlas = gamePlayAtlas;
        this.engine = engine;
        init();
    }
    private void init()
    {
        initNighters();
    }
    private void initNighters()
    {
        nighters.put(Undead.Lorale,
                new Nighter(engine,gamePlayAtlas.findRegion(RegionNames.LORALE),
                        100,10,0));

    }

    @Override
    public void addEntityInPos(float x, float y) {
        Nighter nighter = nighters.get(Undead.Lorale);//todo later make ability to choose Nighter
        PlayerTag playerTag = engine.createComponent(PlayerTag.class);
        nighter.add(playerTag);
        addMovementComponents(engine,nighter,x,y,PositionTracker.PositionBiMap.nightersBiMap);
        engine.addEntity(nighter);
        UserData.Player = nighter;
    }


    public static HashMap<Undead,Nighter> nighters = new HashMap<Undead, Nighter>();








}
