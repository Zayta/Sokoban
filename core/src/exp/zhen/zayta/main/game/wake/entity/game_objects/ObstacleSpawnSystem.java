package exp.zhen.zayta.main.game.wake.entity.game_objects;

import com.badlogic.ashley.systems.IntervalSystem;

import exp.zhen.zayta.main.game.wake.entity.EntityFactory;

public class ObstacleSpawnSystem extends IntervalSystem {
    private final EntityFactory factory;
    public ObstacleSpawnSystem(EntityFactory entityFactory){
        super(0.2f);
        this.factory = entityFactory;
    }
    @Override
    protected void updateInterval() {
//        float min = 0; float max = SizeManager.WAKE_WORLD_WIDTH-SizeManager.OBSTACLE_SIZE;

//        float obstacleX = MathUtils.random(min,max);
//        float obstacleY = SizeManager.WAKE_WORLD_HEIGHT;

//        factory.addObstacle(obstacleX,obstacleY);

    }
}
