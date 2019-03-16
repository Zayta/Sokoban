package exp.zhen.zayta.debug.debug_system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.quest.movement.component.CircularBoundsComponent;

public class DebugRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(CircularBoundsComponent.class).get();

    private final Viewport viewport;
    private final ShapeRenderer renderer;

    public DebugRenderSystem(Viewport viewport,ShapeRenderer renderer)
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
        CircularBoundsComponent circularBoundsComponent = Mappers.BOUNDS.get(entity);
        renderer.circle(circularBoundsComponent.getX(), circularBoundsComponent.getY(), circularBoundsComponent.getRadius(),30);
    }
}
