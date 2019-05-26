package exp.zhen.zayta.main.game.conquest.soldiers.nur;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.main.game.characters.Undead;

public class NUR {

    private TextureAtlas conquestAtlas;
    public NUR(TextureAtlas conquestAtlas){
        this.conquestAtlas = conquestAtlas;
        init();
    }
    private void init()
    {
        initNighters();
    }
    private void initNighters()
    {
        nighters.put(Undead.Lorale,
                new Nighter(conquestAtlas.findRegion("portraits/Lorale"),100,10,10));

    }

    public static HashMap<Undead,Nighter> nighters = new HashMap<Undead, Nighter>();

    public TextureAtlas getConquestAtlas(){
        return conquestAtlas;
    }

}
