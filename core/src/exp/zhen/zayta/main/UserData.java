package exp.zhen.zayta.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Logger;


public class UserData { /*monitors how much hp a player has left in the game
 and stores levels unlocked and total points earned.*/
private static final Logger log = new Logger(UserData.class.getName(),Logger.DEBUG);

//todo later make new fonts skin and change this accordingly
    private final String numScenesUnlocked = "numScenesUnlocked";
    private final String essence = "essence";
    private Preferences preferences;

//    public ArrayList<Entity> Character = new;


    UserData() {
        preferences = Gdx.app.getPreferences(Game.class.getSimpleName());
        setNumScenesUnlocked(0);

    }

    public int getEssence() {
        return preferences.getInteger(essence);
    }

    public void setEssence(int essence) {
        preferences.putInteger(this.essence,essence);
        preferences.flush();
    }
    public void unlockScene(){

        //log.debug("scene is unlocking");
        setNumScenesUnlocked(preferences.getInteger(numScenesUnlocked)+1);
    }

    public int getNumScenesUnlocked() {
        return preferences.getInteger(numScenesUnlocked);
    }

    public void setNumScenesUnlocked(int numScenesUnlocked) {
        preferences.putInteger(this.numScenesUnlocked,numScenesUnlocked);

        preferences.flush();
        //log.debug("preferences are flushed");
    }



}
