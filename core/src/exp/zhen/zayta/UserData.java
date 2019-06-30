package exp.zhen.zayta;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class UserData { /*monitors how much hp a player has left in the game
 and stores levels unlocked and total points earned.*/


//    private static final String POINTS = "highscore";//todo later make new fonts skin and change this accordingly
    private static final String POINTS = "points";

    private Preferences PREFS;
    private int points;

    public static Entity Player;


    UserData() {
        PREFS = Gdx.app.getPreferences(RPG.class.getSimpleName());
        points = PREFS.getInteger(POINTS, 0);

    }

    public String getPointsString() {
        return String.valueOf(points);
    }


}
