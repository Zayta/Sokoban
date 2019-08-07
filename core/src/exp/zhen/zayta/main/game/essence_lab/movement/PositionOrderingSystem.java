package exp.zhen.zayta.main.game.essence_lab.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;

public class PositionOrderingSystem extends IteratingSystem {


    private static Family MOVING_ENTITIES = Family.all(
            Position.class,
            PositionTrackerComponent.class,
            VelocityComponent.class,
            MovementLimitationComponent.class,
            RectangularBoundsComponent.class
    ).get();

    private ImmutableArray<Entity> entities;
    private PriorityQueue<Entity> upMovingEntities,downMovingEntities,leftMovingEntities,rightMovingEntities;

    //    private PriorityQueue<Entity> upMovingEntities,downMovingEntities,leftMovingEntities,rightMovingEntities;

    public static Comparator<Entity> verticalComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity entity1, Entity entity2) {
            float scalar = 100;//arbitrary scalar
            Position position1 = Mappers.POSITION.get(entity1);
            Position position2 = Mappers.POSITION.get(entity2);
            // ascending order
            return (int)(scalar*position2.getY())-(int)(scalar*position1.getY());
            // descending order
            // return fruit1.getQuantity() - fruit2.getQuantity()
        }
    };
    public static Comparator<Entity> horizontalComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity entity1, Entity entity2) {
            float scalar = 100;//arbitrary scalar
            Position position1 = Mappers.POSITION.get(entity1);
            Position position2 = Mappers.POSITION.get(entity2);
            // ascending order
            return (int)(scalar*position2.getX())-(int)(scalar*position1.getX());
            // descending order
            // return fruit1.getQuantity() - fruit2.getQuantity()
        }
    };

    public PositionOrderingSystem(Family family) {
        super(family);
        entities = getEngine().getEntitiesFor(MOVING_ENTITIES);
        int size = entities.size();
        upMovingEntities = new PriorityQueue<Entity>(size,Collections .<Entity>reverseOrder(verticalComparator));
        downMovingEntities = new PriorityQueue<Entity>(size,verticalComparator);
        leftMovingEntities = new PriorityQueue<Entity>(size, horizontalComparator);
        rightMovingEntities = new PriorityQueue<Entity>(size, Collections.<Entity>reverseOrder(horizontalComparator));

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        switch (Mappers.MOVEMENT.get(entity).getDirection()){
            case up:
                upMovingEntities.offer(entity);
                break;
            case down:
                downMovingEntities.offer(entity);
                break;
            case left:
                leftMovingEntities.offer(entity);
                break;
            case right:
                rightMovingEntities.offer(entity);
                break;
        }
//   todo for systems using this system:
//        while(!upMovingEntities.isEmpty()){
//            processEntity(upMovingEntities.poll(),deltaTime);
//        }
//
//        while(!downMovingEntities.isEmpty()){
//            processEntity(downMovingEntities.poll(),deltaTime);
//        }
//
//        while(!leftMovingEntities.isEmpty()){
//            processEntity(leftMovingEntities.poll(),deltaTime);
//        }
//
//        while(!rightMovingEntities.isEmpty()){
//            processEntity(rightMovingEntities.poll(),deltaTime);
//        }
    }
}
