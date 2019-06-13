package exp.zhen.zayta.main.game.wake.render;

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

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.NameTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.BattleComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;

public class NameTagRenderSystem extends IteratingSystem {
    private static final Logger log = new Logger(HudRenderSystem.class.getName(),Logger.DEBUG);
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final GlyphLayout layout = new GlyphLayout();

    private Array<Entity> renderQueue = new Array<Entity>();

    private static final Family FAMILY = Family.all(
            Position.class,
            NameTag.class
    ).get();


    public NameTagRenderSystem(Viewport viewport, SpriteBatch batch)
    {
        super(FAMILY);
        this.viewport = viewport;
        this.batch = batch;

        /**Customize Font**/
        this.font = new BitmapFont();
        float scaleX = SizeManager.WAKE_WORLD_WIDTH/SizeManager.WIDTH;
        float scaleY = SizeManager.WAKE_WORLD_HEIGHT/SizeManager.HEIGHT;
        float fontScale = 3;
        font.setUseIntegerPositions(false);
        font.setColor(Color.RED);
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
        draw();
    }

    private void draw(){
        String levelString = "STATS: "/*+RPG.userData.getLevel()*/;
        layout.setText(font,levelString);
        font.draw(batch,levelString,
                20,SizeManager.HUD_HEIGHT-layout.height);

        for(Entity entity:renderQueue) {
            Position position = Mappers.POSITION.get(entity);
            NameTag nameTag = Mappers.NAMETAG.get(entity);

            layout.setText(font,nameTag.getName());
            font.draw(batch,layout,position.getX(),position.getY());
        }



    }

}
