package exp.zhen.zayta.game.wake_mode.entity.game_objects;

import com.badlogic.ashley.systems.IntervalSystem;

import exp.zhen.zayta.game.wake_mode.entity.EntityFactory;

public class ObstacleSpawnSystem extends IntervalSystem {
    private final EntityFactory factory;
    public ObstacleSpawnSystem(EntityFactory entityFactory){
        super(0.2f);
        this.factory = entityFactory;
    }
    @Override
    protected void updateInterval() {
//        float min = 0; float max = SizeManager.WORLD_WIDTH-SizeManager.OBSTACLE_SIZE;

//        float obstacleX = MathUtils.random(min,max);
//        float obstacleY = SizeManager.WORLD_HEIGHT;

//        factory.addObstacle(obstacleX,obstacleY);

    }
}
