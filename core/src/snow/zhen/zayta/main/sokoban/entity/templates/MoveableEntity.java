package snow.zhen.zayta.main.sokoban.entity.templates;

import com.badlogic.gdx.math.Vector2;

import snow.zhen.zayta.main.sokoban.entity.EntityBase;
import snow.zhen.zayta.main.sokoban.movement.Direction;
import snow.zhen.zayta.main.sokoban.movement.Movement;

public abstract class MoveableEntity extends EntityBase implements Movement {
    private Vector2 targetPosition;
    private snow.zhen.zayta.main.sokoban.movement.Direction direction;
    private boolean canMove = false;

    public MoveableEntity(){
        super();
        targetPosition = new Vector2();
        direction = snow.zhen.zayta.main.sokoban.movement.Direction.none;
    }
    public MoveableEntity(float x, float y){
        super(x,y);
        targetPosition = new Vector2(x,y);
        direction = snow.zhen.zayta.main.sokoban.movement.Direction.none;
    }

    //= abstract methods =//
//    @Override
//    public abstract void handleCollision(EntityBase collidedEntity);

    //= public methods =//
    @Override
    public void setPosition(float x,float y){
        super.setPosition(x,y);
    }
    public void setPosition(Vector2 newPos){
        super.setPosition(newPos.x,newPos.y);
    }

    @Override
    public void move(snow.zhen.zayta.main.sokoban.movement.Direction direction) {
        this.direction = direction;
        targetPosition.add(direction.vector);
        targetPosition.set(Math.round(targetPosition.x),Math.round(targetPosition.y));
    }


    @Override
    public Direction getDirection() {
        return direction;
    }

    public void setTargetPosition(float x, float y){
        targetPosition.set((int)x,(int)y);
    }
    @Override
    public Vector2 getTargetPosition(){
        return targetPosition;
    }

}
