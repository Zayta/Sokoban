package exp.zhen.zayta.game.quest.user_control;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TiledMapStageSystem extends EntitySystem {
    private Stage stage;
    public TiledMapStageSystem(TiledMap tiledMap, Viewport viewport) {
        stage = new TiledMapStage(tiledMap);
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);

    }

    @Override
    public void update(float deltaTime) {
        stage.act();
    }
}
