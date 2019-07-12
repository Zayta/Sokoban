package exp.zhen.zayta.main.game.wake.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;

public class StatsRenderSystem extends IteratingSystem {
    private static final Logger log = new Logger(StatsRenderSystem.class.getName(),Logger.DEBUG);
    private final Viewport viewport;
    private final ShapeRenderer renderer;


    private static final Family FAMILY = Family.all(
            Position.class,
            DimensionComponent.class,
            HealthComponent.class
    ).get();


    public StatsRenderSystem(Viewport viewport, ShapeRenderer renderer)
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
//        renderer.setColor(Color.ORANGE);
        renderer.setAutoShapeType(true);

        super.update(deltaTime);

        renderer.end();
        renderer.setColor(oldColor);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Position position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        HealthComponent healthComponent = Mappers.HEALTH.get(entity);

        float height = dimension.getHeight()/10;

//        renderer.set(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.CLEAR);
        renderer.rect(position.getX(),position.getY()-height,dimension.getWidth(),height);


//        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.ORANGE);
        renderer.rect(position.getX(),position.getY()-height,(healthComponent.getHp()/ healthComponent.getFull_hp())*dimension.getWidth(),height);




    }

}
