package exp.zhen.zayta.mode.quest.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.config.SizeManager;

public class MapRenderSystem extends EntitySystem{


    private OrthogonalTiledMapRenderer mapRenderer;
    private final Viewport viewport;
    private Array<Entity> renderQueue = new Array<Entity>();

    public MapRenderSystem(TiledMap map, Viewport viewport){
        this.viewport=viewport;
        mapRenderer = new OrthogonalTiledMapRenderer(map,1/SizeManager.maxObjWidth);
    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        Camera camera = viewport.getCamera();
        camera.update();
        mapRenderer.setView((OrthographicCamera) camera);
        mapRenderer.render();
    }
}

