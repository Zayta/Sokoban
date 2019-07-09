package exp.zhen.zayta.main.game.characters;

import java.util.ArrayList;

public enum Undead {
    Ruzo(new CharacterClass[] {}),
    Xenon(new CharacterClass[] {}),
    Lorale(new CharacterClass[] {CharacterClass.Bomber}),
    Letra(new CharacterClass[] {}),
    Taria(new CharacterClass[] {}),
    Cumin(new CharacterClass[] {}),
    Kira(new CharacterClass[] {}),
    Foofi(new CharacterClass[]{});

    private ArrayList<CharacterClass> characterClass;
    Undead(CharacterClass[] characterClass){
        this.characterClass = new ArrayList<CharacterClass>();
        for(CharacterClass attribute: characterClass){
            this.characterClass.add(attribute);
        }
    }

    public ArrayList<CharacterClass> getAttributes() {
        return characterClass;
    }
}

