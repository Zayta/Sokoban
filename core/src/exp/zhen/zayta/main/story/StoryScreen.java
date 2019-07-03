package exp.zhen.zayta.main.story;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.ScreenBase;
import exp.zhen.zayta.main.UiRegionNames;
import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.menu.MenuScreen;


public class StoryScreen extends ScreenBase {

    public StoryScreen(RPG game) {
        super(game);
    }

    //todo make array of n story-pics, and draw UserData.numScenesUnlocked of the n pics


    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas wakePlayAtlas = assetManager.get(UIAssetDescriptors.WAKE_PLAY);
        Skin uiSkin = assetManager.get(UIAssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = wakePlayAtlas.findRegion(WPRegionNames.BACKGROUND);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Title
        Label title = new Label("Memories", uiSkin);

        // memories label
        int numScenesUnlocked = RPG.userData.getNumScenesUnlocked();
        Label pointsLabel = new Label(String.valueOf(numScenesUnlocked), uiSkin);

        // back button
        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        // setup tables
        Table contentTable = new Table(uiSkin);
        contentTable.defaults().pad(20);
        contentTable.setBackground(UiRegionNames.WINDOW);

        contentTable.add(title).row();
        contentTable.add(pointsLabel).row();
        contentTable.add(backButton);

        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void back() {
//        log.debug("back()");
        game.setScreen(new MenuScreen(game));
    }

}
