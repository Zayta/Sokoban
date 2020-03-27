package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.World;

//import snow.zhen.zayta.main.game.wake.map.tiled_map.my_generated_map.World;

public class GeneratedMapRenderSystem extends EntitySystem {
    private snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.World world; private SpriteBatch batch;
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
