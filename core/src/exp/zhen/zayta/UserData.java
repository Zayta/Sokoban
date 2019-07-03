package exp.zhen.zayta;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class UserData { /*monitors how much hp a player has left in the game
 and stores levels unlocked and total points earned.*/


//todo later make new fonts skin and change this accordingly
    private final String numScenesUnlocked = "numScenesUnlocked";
    private final String essence = "essence";
    private Preferences preferences;

    public Entity Player;


    UserData() {
        preferences = Gdx.app.getPreferences(RPG.class.getSimpleName());
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
        setNumScenesUnlocked(preferences.getInteger(numScenesUnlocked)+1);
    }

    public int getNumScenesUnlocked() {
        return preferences.getInteger(numScenesUnlocked);
    }

    public void setNumScenesUnlocked(int numScenesUnlocked) {
        preferences.putInteger(this.numScenesUnlocked,numScenesUnlocked);
        preferences.flush();

    }

    //
//    public String getEssence() {
//        return String.valueOf(essence);
//    }
//
//    public String getNumScenesUnlocked() {
//        return String.valueOf(numScenesUnlocked);
//    }

//
//    public void updateHighScore(int score) {
//        if(score < highscore) {
//            return;
//        }
//
//        highscore = score;
//        preferences.putInteger(HIGH_SCORE_KEY, highscore);
//        preferences.flush();
//    }
//
//    public String getHighScoreString() {
//        return String.valueOf(highscore);
//    }
//
//    public DifficultyLevel getDifficultyLevel() {
//        return difficultyLevel;
//    }
//
//    public void updateDifficulty(DifficultyLevel newDifficultyLevel) {
//        if(difficultyLevel == newDifficultyLevel) {
//            return;
//        }
//
//        difficultyLevel = newDifficultyLevel;
//        preferences.putString(DIFFICULTY_KEY, difficultyLevel.name());
//        preferences.flush();
//    }


}
