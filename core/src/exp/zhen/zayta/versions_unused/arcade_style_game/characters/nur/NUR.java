package exp.zhen.zayta.versions_unused.arcade_style_game.characters.nur;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.util.KeyListMap;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats;

public class NUR {
  private static final Logger log = new Logger(NUR.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;
    public KeyListMap <exp.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead, exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats> nighters;


    public NUR(TextureAtlas labAtlas){
        this.labAtlas = labAtlas;
        nighters = new KeyListMap<exp.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead, exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats>();
        initNighters();
    }
    
    private void initNighters(){
        nighters.put(exp.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead.Tenyu, new exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats("Tenyu",
                labAtlas.findRegion(RegionNames.TENYU),
                100,10,10));
        nighters.put(exp.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead.Lorale, new exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats("Lorale",
                labAtlas.findRegion(RegionNames.LORALE),
                100,10,10));
        nighters.put(exp.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead.Letra, new exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats("Letra",
                labAtlas.findRegion(RegionNames.LETRA),
                100,10,10));


        nighters.put(Undead.Anonymous, new Stats("???",
                labAtlas.findRegion(RegionNames.CIVILIAN),
                0,0,0));

    }





    
    
    
    
    
    
    
    
    
    
    
    


}
