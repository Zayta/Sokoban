package exp.zhen.zayta.main.game.personality_engineering_lab.entity.components.properties.explosion;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

public class ExplosiveHolderComponent implements Component {
    private ArrayList<Entity> landmines = new ArrayList<Entity>();
    private int charge;

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public ArrayList<Entity> getLandmines() {
        return landmines;
    }

}
