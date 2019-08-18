package exp.zhen.zayta.main.game.essence_lab.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.config.SizeManager;

public class HudRenderSystem extends EntitySystem {
    private static final Logger log = new Logger(HudRenderSystem.class.getName(),Logger.DEBUG);
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final GlyphLayout layout = new GlyphLayout();


    public HudRenderSystem(Viewport viewport,SpriteBatch batch, BitmapFont font)
    {
        this.viewport = viewport;
        this.batch = batch;
        this.font = font;
        font.getData().setScale(2,2);
    }

    @Override
    public void update(float deltaTime) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw();

        batch.end();
    }

    private void draw(){
        String levelString = "LEVEL: "/*+RPG.userData.getLevel()*/;
        layout.setText(font,levelString);
        font.draw(batch,levelString,
                20,SizeManager.HUD_HEIGHT-layout.height);

        String objectiveString = "Turn Embers to Flares"/*+RPG.userData.getStonesInLevel()*/;
        layout.setText(font,objectiveString);
        font.draw(batch,objectiveString,
                SizeManager.HUD_WIDTH - layout.width-20,SizeManager.HUD_HEIGHT-layout.height);


    }

}
