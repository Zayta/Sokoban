package exp.zhen.zayta.main.game.experiment.engine.entity.components.labels;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;


//just used to mark entity as player
public class PlayerTag implements Component {

    private int progress = 0;

    public int getProgress() {
        return progress;
    }

    private void setProgress(int progress) {
        this.progress = MathUtils.clamp(progress,0,100);
    }

    public void updateProgress(int amount){
        setProgress(progress+amount);
    }
}
