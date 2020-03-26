package exp.zhen.zayta.versions_unused.arcade_style_game.debug.debug_system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;

public class DebugColorSystem extends IteratingSystem {
    private static final Logger log = new Logger(DebugPositionTrackerSystem.class.getName(),Logger.DEBUG);
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final GlyphLayout layout = new GlyphLayout();

    private Array<Entity> renderQueue = new Array<Entity>();

    private static final Family FAMILY = Family.all(
//            PlayerTag.class,
            ColorComponent.class,
            Position.class,
            RectangularBoundsComponent.class
    ).get();


    public DebugColorSystem(Viewport viewport, SpriteBatch batch)
    {
        super(FAMILY);
        this.viewport = viewport;
        this.batch = batch;

        /**Customize Font**/
        this.font = new BitmapFont();
        float scaleX = SizeManager.WAKE_WORLD_WIDTH/SizeManager.WIDTH;
        float scaleY = SizeManager.WAKE_WORLD_HEIGHT/SizeManager.HEIGHT;
        float fontScale = 2;
        font.setUseIntegerPositions(false);
        font.setColor(Color.CYAN);
        font.getData().setScale(fontScale*scaleX,fontScale*scaleY);



    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);
        renderQueue.addAll(entities.toArray());

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        super.update(deltaTime);

        batch.end();

        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RectangularBoundsComponent bounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
        Position position = Mappers.POSITION.get(entity);
        ColorComponent colorComponent = Mappers.COLOR.get(entity);
        layout.setText(font,/*"Position: ("+position.getX()+","+position.getY()+")\n"+
                "Position Raw Key: "+PositionTracker.generateKey(position.getX(),position.getY())+"\n"+
                "Bounds Raw Key: "+PositionTracker.generateKey(bounds.getX(),bounds.getY())+"\n"+*/
                "Color: "+colorComponent.getColor());
        font.draw(batch,layout,bounds.getX()-layout.width/2,bounds.getY()+SizeManager.maxObjHeight/2+layout.height-0.1f);//0.1f is offset from bottom
    }



}
