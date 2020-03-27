package snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;

public class PushComponent implements Component,Pool.Poolable {
    private snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction = Direction.none;

    public snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction getDirection() {
        return direction;
    }

    public void setDirection(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction) {
        this.direction = direction;
    }

    private final ArrayList<Entity> carriedItems = new ArrayList<Entity>();

    public void add(Entity item){
        carriedItems.add(item);
    }

    public ArrayList<Entity> getCarriedItems() {
        return carriedItems;
    }

    public void remove(Entity entity){
        carriedItems.remove(entity);
    }

    @Override
    public void reset() {
        carriedItems.clear();
        direction = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none;
    }
}
