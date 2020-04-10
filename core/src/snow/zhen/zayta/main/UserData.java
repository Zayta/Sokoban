package snow.zhen.zayta.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Logger;


public class UserData { /*monitors how much hp a player has left in the gameplay
 and stores levels unlocked and total points earned.*/
private static final Logger log = new Logger(UserData.class.getName(),Logger.DEBUG);

//todo later make new fonts skin and change this accordingly
    private final String numScenesUnlocked = "numScenesUnlocked";

    private Preferences preferences;

    private static UserData userData = new UserData();
    public static UserData getInstance(){
        return userData;
    }
    private UserData() {
        preferences = Gdx.app.getPreferences("Preference");
    }

    public void unlockScene(){

        setNumScenesUnlocked(preferences.getInteger(numScenesUnlocked)+1);
    }

    public int getNumScenesUnlocked() {
        return preferences.getInteger(numScenesUnlocked);
    }

    public void setNumScenesUnlocked(int numScenesUnlocked) {
        preferences.putInteger(this.numScenesUnlocked,numScenesUnlocked);

        preferences.flush();
    }



}
