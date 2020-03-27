package snow.zhen.zayta.versions_unused.conquest.soldiers.nur;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

import snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead;

public class NUR {

    private TextureAtlas conquestAtlas;
    private static HashMap<snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead,Nighter> nighters = new HashMap<snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead, Nighter>();

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
        nighters.put(snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead.Lorale,
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
