package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

public class PocketComponent implements Component {
    private ArrayList<Entity> carriedItems = new ArrayList<Entity>();

    public void add(Entity item){
        carriedItems.add(item);
    }

    public ArrayList<Entity> getCarriedItems() {
        return carriedItems;
    }

    public void remove(Entity entity){
        carriedItems.remove(entity);
    }

}
