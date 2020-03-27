package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.mono_color.MonoColorRenderTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent;

public class MonoColorEntityRenderSystem extends EntitySystem {
    private final Color default_color = Color.WHITE;
    public static final Family FAMILY = Family.all(
            TextureComponent.class,
            Position.class,
            DimensionComponent.class,
            ColorComponent.class,
            MonoColorRenderTag.class).get();

    private final Viewport viewport;
    private final SpriteBatch batch;

    private Array<Entity> renderQueue = new Array<Entity>();

    public MonoColorEntityRenderSystem(Viewport viewport){
        this.viewport=viewport;
        this.batch = new SpriteBatch();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);
        renderQueue.addAll(entities.toArray());

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw();

        batch.end();


        renderQueue.clear();
    }
    private void draw(){
        for(Entity entity:renderQueue) {

            Color color = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.COLOR.get(entity).getColor();
            batch.setColor(color);

            Position position = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(entity);
            DimensionComponent dimension = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.DIMENSION.get(entity);
            TextureComponent texture = Mappers.TEXTURE.get(entity);


            batch.draw(texture.getRegion(),position.getX(),position.getY(),dimension.getWidth(),dimension.getHeight());
        }

    }
}
