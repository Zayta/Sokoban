package exp.zhen.zayta.main.game.characters.nur;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.Stats;
import exp.zhen.zayta.util.KeyListMap;

public class NUR {
  private static final Logger log = new Logger(NUR.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;
    public KeyListMap <Undead, Stats> nighters;


    public NUR(TextureAtlas labAtlas){
        this.labAtlas = labAtlas;
        nighters = new KeyListMap<Undead, Stats>();
        initNighters();
    }
    
    private void initNighters(){
        nighters.put(Undead.Tenyu, new Stats("Tenyu",
                labAtlas.findRegion(WPRegionNames.TENYU),
                100,10,10));
        nighters.put(Undead.Lorale, new Stats("Lorale",
                labAtlas.findRegion(WPRegionNames.LORALE),
                100,10,10));


        nighters.put(Undead.Anonymous, new Stats("???",
                labAtlas.findRegion(WPRegionNames.CIVILIAN),
                0,0,0));

    }





    
    
    
    
    
    
    
    
    
    
    
    


}
