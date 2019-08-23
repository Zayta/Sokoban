package exp.zhen.zayta.versions_unused;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import exp.zhen.zayta.main.RPG;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.ScreenBase;
import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.versions_unused.conquest.Conquest;

@Deprecated
public class GameScreen extends ScreenBase {

    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);

    public GameScreen(RPG game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas wakePlayAtlas = assetManager.get(AssetDescriptors.LAB);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = wakePlayAtlas.findRegion(RegionNames.SQUARE_FLOOR);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // title
        Label title = new Label("PLAY", uiSkin);

        table.add(title);
        table.row();
        table.add(modeSelectionTable(uiSkin));
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private Table modeSelectionTable(Skin uiSkin){

        // wake mode button
        TextButton wakeButton = new TextButton("LIFE", uiSkin);
        wakeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playWakeMode();
            }
        });

        // wake mode button
        TextButton conquestButton = new TextButton("CONQUEST", uiSkin);
        conquestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playConquest();
            }
        });

        // back button
        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        // setup tables
        Table modeSelectionTable = new Table(uiSkin);
        modeSelectionTable.defaults().pad(20);
        modeSelectionTable.setBackground(RegionNames.PANEL);

        modeSelectionTable.add(wakeButton);
        modeSelectionTable.row();
        modeSelectionTable.add(conquestButton);
        modeSelectionTable.row();
        modeSelectionTable.add(backButton);

        modeSelectionTable.center();

        return modeSelectionTable;
    }


    private void back() {
//        ////log.debug("back()");
        game.goToMain();
    }


    private void playWakeMode() {
//        game.setScreen(new Experiment(game));
    }

    private void playConquest() {
        game.setScreen(new Conquest(game));
    }
}
