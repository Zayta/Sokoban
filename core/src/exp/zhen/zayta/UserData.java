package exp.zhen.zayta;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import exp.zhen.zayta.mode.quest.entity.undead.nur.Nighter;
import exp.zhen.zayta.util.BiMap;


public class UserData { /*monitors how much HP a player has left in the game
 and stores levels unlocked and total points earned.*/


//    private static final String POINTS = "highscore";//todo later make new ui skin and change this accordingly
    private static final String POINTS = "points";

    private Preferences PREFS;
    private int points;

    public static Nighter Player;


    UserData() {
        PREFS = Gdx.app.getPreferences(RPG.class.getSimpleName());
        points = PREFS.getInteger(POINTS, 0);
    }

    public String getPointsString() {
        return String.valueOf(points);
    }


}
