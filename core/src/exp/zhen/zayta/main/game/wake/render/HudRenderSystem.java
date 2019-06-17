package exp.zhen.zayta.main.game.wake.render;

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


    public HudRenderSystem(Viewport viewport,SpriteBatch batch/*, BitmapFont font*/)
    {
        this.viewport = viewport;
        this.batch = batch;
        this.font = new BitmapFont();
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
//        String levelString = "LEVEL: "/*+RPG.userData.getLevel()*/;
//        layout.setText(font,levelString);
//        font.draw(batch,levelString,
//                20,SizeManager.HUD_HEIGHT-layout.height);

        String stonesString = "STONES IN LEVEL: "/*+RPG.userData.getStonesInLevel()*/;
        layout.setText(font,stonesString);
        font.draw(batch,stonesString,
                SizeManager.HUD_WIDTH - layout.width-20,SizeManager.HUD_HEIGHT-layout.height);

//
//        HealthComponent playerStats = UserData.Player.getComponent(HealthComponent.class);
//        String HPString = "hp: "+playerStats.getHP();
//        layout.setText(font,HPString);
//        font.draw(batch,HPString,20,3*layout.height);
//
//        String ATKString = "atk: "+playerStats.getATK();
//        layout.setText(font,ATKString);
//        font.draw(batch,ATKString,20,2*layout.height);
//
//        String DEFString = "def: "+playerStats.getATK();
//        layout.setText(font,DEFString);
//        font.draw(batch,DEFString,20,layout.height);


    }

}
