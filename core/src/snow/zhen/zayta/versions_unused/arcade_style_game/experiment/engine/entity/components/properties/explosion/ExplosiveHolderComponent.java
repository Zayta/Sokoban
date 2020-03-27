package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.explosion;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

public class ExplosiveHolderComponent implements Component,Pool.Poolable {
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


    @Override
    public void reset() {
        charge = 0;
    }
}
