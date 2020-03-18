package exp.zhen.zayta.main.sokoban.map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Hashtable;

import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.main.sokoban.entity.Character;
import exp.zhen.zayta.main.sokoban.entity.CharacterName;
import exp.zhen.zayta.main.sokoban.entity.Crate;
import exp.zhen.zayta.main.sokoban.entity.Goal;
import exp.zhen.zayta.main.sokoban.entity.Wall;
import exp.zhen.zayta.util.BiMap;

public class EntityBuilder {
    private TextureAtlas sokobanAtlas;
    public EntityBuilder(TextureAtlas sokobanAtlas){
        this.sokobanAtlas = sokobanAtlas;

        characters = new BiMap<CharacterName, Character>();
        characters.put(CharacterName.TENYU, new Character
                            (sokobanAtlas.findRegion(RegionNames.TENYU),0,0));
        characters.put(CharacterName.LETRA, new Character
                (sokobanAtlas.findRegion(RegionNames.LETRA),0,0));
        characters.put(CharacterName.LORALE, new Character
                (sokobanAtlas.findRegion(RegionNames.LORALE),0,0));
        characters.put(CharacterName.TARIA, new Character
                (sokobanAtlas.findRegion(RegionNames.TARIA),0,0));
        characters.put(CharacterName.XIF, new Character
                (sokobanAtlas.findRegion(RegionNames.XIF),0,0));

    }
    public BiMap<CharacterName,Character> characters;

    public Character getCharacter(CharacterName characterName, float x, float y){
        Character character = characters.get(characterName);
        character.setPosition(x,y);
        return character;
    }

    public Goal buildGoal(float x, float y){
        Goal goal = new Goal(sokobanAtlas.findRegion(RegionNames.OVERLAY[3]),x,y);

        return goal;
    }

    public Wall buildWall(float x, float y,int lvl){
        Wall wall = new Wall(sokobanAtlas.findRegion(RegionNames.BRICK_STONE_CRATE),x,y);
        if(lvl<10){
            wall.setTextureRegion(sokobanAtlas.findRegion(RegionNames.BRICK_STONE_CRATE));
        }
        return wall;
    }


    public Crate buildCrate(float x, float y, int lvl){
        Hashtable<Crate.State,TextureRegion> crateRegions= new Hashtable<Crate.State, TextureRegion>();
        crateRegions.put(Crate.State.NORMAL,sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_NORMAL));
        crateRegions.put(Crate.State.IN_GOAL,sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_SOLVED));
        crateRegions.put(Crate.State.IN_HOLE,sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_HOLE));
        crateRegions.put(Crate.State.IMMOVABLE,sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_BLOCK));

        Crate crate = new Crate(crateRegions,x,y);

        return crate;
    }
}
