package snow.zhen.zayta.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Logger;


public class UserData { /*monitors how much hp a player has left in the gameplay
 and stores levels unlocked and total points earned.*/
private static final Logger log = new Logger(UserData.class.getName(),Logger.DEBUG);

//todo later make new fonts skin and change this accordingly
    private final String numCompleted = "numCompleted";

    private Preferences preferences;

    private static UserData userData = new UserData();
    public static UserData getInstance(){
        return userData;
    }
    private UserData() {
        preferences = Gdx.app.getPreferences("Preference");
    }
    
    public void complete(int completedLvl){
        //if the completed lvl has not been previously completed
        if(completedLvl>=preferences.getInteger(numCompleted)){
            unlockNxtLvl();
        }
    }
    
    private void unlockNxtLvl(){
        setNumCompleted(preferences.getInteger(numCompleted)+1);
    }

    public int getNumCompleted() {
        return preferences.getInteger(numCompleted);
    }

    public void setNumCompleted(int numCompleted) {
        preferences.putInteger(this.numCompleted,numCompleted);

        preferences.flush();
    }



}
