package exp.zhen.zayta.main.game.debug.debug_system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.CircularBoundsComponent;

public class DebugCircularBoundsRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(CircularBoundsComponent.class).get();

    private final Viewport viewport;
    private final ShapeRenderer renderer;

    public DebugCircularBoundsRenderSystem(Viewport viewport, ShapeRenderer renderer)
    {
        super(FAMILY);
        this.viewport = viewport;
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {

        Color oldColor = renderer.getColor().cpy();

        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);

        super.update(deltaTime);

        renderer.end();
        renderer.setColor(oldColor);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CircularBoundsComponent circularBoundsComponent = Mappers.CIRCULAR_BOUNDS.get(entity);
        renderer.circle(circularBoundsComponent.getX(), circularBoundsComponent.getY(), circularBoundsComponent.getRadius(),30);
    }
}
