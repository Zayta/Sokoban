package exp.zhen.zayta.main.game.conquest.soldiers.nur;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

import exp.zhen.zayta.main.game.characters.Undead;

public class NUR {

    private TextureAtlas conquestAtlas;
    private static HashMap<Undead,Nighter> nighters = new HashMap<Undead, Nighter>();

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
                new Nighter("Lorale",conquestAtlas.findRegion("portraits/Lorale"),100,10,10));

    }
    /*Must make sure nighters is initted*/
    public static Nighter summon(Undead name){

        Nighter nighter = nighters.get(name);
        return new Nighter(nighter.getName(),nighter.getTextureRegion(),nighter.getHp(),nighter.getAtk(),nighter.getDef());
    }

    public TextureAtlas getConquestAtlas(){
        return conquestAtlas;
    }

}
