package exp.zhen.zayta.main.game.essence_lab.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.locker.LockerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;

public class LockerRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(LockerComponent.class,
            RectangularBoundsComponent.class,
            ColorComponent.class).get();

    private final Viewport viewport;
    private final ShapeRenderer renderer;

    public LockerRenderSystem(Viewport viewport, ShapeRenderer renderer)
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
        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(Color.RED);

        super.update(deltaTime);

        renderer.end();
        renderer.setColor(oldColor);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Color color = Mappers.COLOR.get(entity).getColor();
        renderer.setColor(color);
        RectangularBoundsComponent rectangularBoundsComponent = Mappers.RECTANGULAR_BOUNDS.get(entity);
        renderer.rect(rectangularBoundsComponent.getX(), rectangularBoundsComponent.getY(), rectangularBoundsComponent.getWidth(),rectangularBoundsComponent.getHeight());
    }
}
