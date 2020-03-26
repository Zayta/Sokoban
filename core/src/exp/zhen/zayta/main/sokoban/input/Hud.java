package exp.zhen.zayta.main.sokoban.input;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.GameConfig;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.main.sokoban.PlayController;

public class Hud {

    private Stage stage;
    private Viewport hudViewport;
    private BitmapFont font;
    private Skin skin;
    private PlayController playController;
    public Hud(final PlayController playController, SpriteBatch spriteBatch, AssetManager assetManager) {
        this.playController = playController;
        this.hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        this.stage = new Stage(hudViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        this.font = assetManager.get(AssetDescriptors.FONT);
        font.setColor(Color.BLACK);

        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);

        Table table = new Table(skin);
table.setBackground(RegionNames.PANEL);
        //add the Buttons etc.
        // play button
        TextButton undoButton = new TextButton("Undo", skin);
        undoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playController.undoMove();
            }
        });
        table.add(undoButton);
        stage.addActor(table);
    }

    public Stage getStage() { return stage; }

    public void dispose(){
        stage.dispose();
    }
    public void applyViewport(){
        stage.getViewport().apply();
    }
    public void draw(){
        stage.draw();
    }
    public Viewport getViewport(){
        return hudViewport;
    }
}
