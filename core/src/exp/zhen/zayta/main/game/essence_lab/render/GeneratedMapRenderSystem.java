package exp.zhen.zayta.main.game.essence_lab.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.essence_lab.map.sample_code_generated_map.World;

//import exp.zhen.zayta.main.game.wake.map.tiled_map.my_generated_map.World;

public class GeneratedMapRenderSystem extends EntitySystem {
    private World world; private SpriteBatch batch;
    private final Viewport viewport;

    public GeneratedMapRenderSystem(World world, Viewport viewport, SpriteBatch batch){
        this.world = world;
        this.viewport=viewport;
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        world.draw(batch);

        batch.end();
    }
}
