package exp.zhen.zayta.main.game.movable_items.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

import exp.zhen.zayta.main.game.experiment.engine.movement.Direction;

public class PushComponent implements Component,Pool.Poolable {
    private Direction direction = Direction.none;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
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
        direction = Direction.none;
    }
}
