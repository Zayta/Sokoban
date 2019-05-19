package exp.zhen.zayta.main.game.wake.map.input;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.Viewport;


public class TiledMapStageSystem extends EntitySystem {
    private TiledMapStage stage;
    public TiledMapStageSystem(TiledMap tiledMap, Viewport viewport,PooledEngine engine) {
        stage = new TiledMapStage(tiledMap, (PooledEngine) engine);
        stage.setViewport(viewport);

    }

    @Override
    public void update(float deltaTime) {
        stage.act();
    }
}
