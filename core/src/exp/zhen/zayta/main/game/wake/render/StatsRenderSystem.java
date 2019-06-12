package exp.zhen.zayta.main.game.wake.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
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
import exp.zhen.zayta.main.game.wake.entity.components.properties.BattleComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;

public class StatsRenderSystem extends EntitySystem {
    private static final Logger log = new Logger(HudRenderSystem.class.getName(),Logger.DEBUG);
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final float scaleX,scaleY;

    private final GlyphLayout layout = new GlyphLayout();

    private Array<Entity> renderQueue = new Array<Entity>();

    public static final Family FAMILY = Family.all(
            Position.class,
            BattleComponent.class
    ).get();


    public StatsRenderSystem(Viewport viewport,SpriteBatch batch, float scaleX, float scaleY/*, BitmapFont font*/)
    {
        this.viewport = viewport;
        this.batch = batch;
        this.font = new BitmapFont();
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        font.setUseIntegerPositions(false);
        font.setColor(Color.RED);
        font.getData().setScale(2,2);

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
        String levelString = "STATS: "/*+RPG.userData.getLevel()*/;
        layout.setText(font,levelString);
        font.draw(batch,levelString,
                20,SizeManager.HUD_HEIGHT-layout.height);

        for(Entity entity:renderQueue) {
            BattleComponent battleComponent = Mappers.BATTLESTATS.get(entity);
            Position position = Mappers.POSITION.get(entity);


            String HPString = "hp: "+battleComponent.getHp();
            layout.setText(font,HPString);
            font.draw(batch,HPString,position.getX()*scaleX,position.getY()*scaleY);
            log.debug("posX: "+position.getX()*scaleX+" \nposY: "+position.getY()*scaleY);
        }



    }

}
