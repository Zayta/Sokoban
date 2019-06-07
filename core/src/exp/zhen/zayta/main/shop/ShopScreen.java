package exp.zhen.zayta.main.shop;

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
import exp.zhen.zayta.RPG;
import exp.zhen.zayta.assets.AssetDescriptors;
import exp.zhen.zayta.assets.UiRegionNames;
import exp.zhen.zayta.main.game.wake.assets.WakePlayRegionNames;
import exp.zhen.zayta.main.menu.MenuScreen;
import exp.zhen.zayta.main.ScreenBase;


public class ShopScreen extends ScreenBase {

    private static final Logger log = new Logger(ShopScreen.class.getName(), Logger.DEBUG);

    public ShopScreen(RPG game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas wakePlayAtlas = assetManager.get(AssetDescriptors.WAKE_PLAY);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
        
        TextureRegion backgroundRegion = wakePlayAtlas.findRegion(WakePlayRegionNames.BACKGROUND);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // highscore text
        Label pointsText = new Label("TOTAL POINTS", uiSkin);

        // total points label
        String pointsString = RPG.userData.getPointsString();
        Label pointsLabel = new Label(pointsString, uiSkin);

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
        contentTable.setBackground(UiRegionNames.PANEL);

        contentTable.add(pointsText).row();
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
