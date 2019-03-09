package exp.zhen.zayta.mode.quest.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.util.ViewportUtils;

public class GridRenderSystem extends EntitySystem {
    //constants
    private static final Logger log = new Logger(GridRenderSystem.class.getName(),Logger.DEBUG);
    //attributes
    private final Viewport viewport;
    private final ShapeRenderer renderer;
    //consturcotrs
    public GridRenderSystem(Viewport viewport,ShapeRenderer renderer){
        this.viewport = viewport;
        this.renderer = renderer;

    }

    //update

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        ViewportUtils.drawGrid(viewport,renderer);
    }
}
