package exp.zhen.zayta.main.game.wake.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;

public class QuestRenderSystem extends EntitySystem {
    public static final Family FAMILY = Family.all(
            TextureComponent.class,
            Position.class,
            DimensionComponent.class
    ).get();

    private final Viewport viewport;
    private final SpriteBatch batch;

    private Array<Entity> renderQueue = new Array<Entity>();

    public QuestRenderSystem(Viewport viewport, SpriteBatch batch){
        this.viewport=viewport;
        this.batch = batch;
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
            Position position = Mappers.POSITION.get(entity);
            DimensionComponent dimension = Mappers.DIMENSION.get(entity);
            TextureComponent texture = Mappers.TEXTURE.get(entity);


            batch.draw(texture.getRegion(),position.getX(),position.getY(),dimension.getWidth(),dimension.getHeight());
        }

    }
}
