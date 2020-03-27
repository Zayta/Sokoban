package snow.zhen.zayta.main.shop;

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

import snow.zhen.zayta.main.Game;
import snow.zhen.zayta.main.assets.AssetDescriptors;
import snow.zhen.zayta.main.ScreenBase;
import snow.zhen.zayta.main.assets.RegionNames;


public class ShopScreen extends ScreenBase {

    private static final Logger log = new Logger(ShopScreen.class.getName(), Logger.DEBUG);

    public ShopScreen(Game game) {
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

        // essence text
        Label title = new Label("Essence", uiSkin);

        // total points label
        int amountEssence = game.getUserData().getEssence();
        Label essenceLabel = new Label(String.valueOf(amountEssence), uiSkin);

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
        contentTable.setBackground(RegionNames.PANEL);

        contentTable.add(title).row();
        contentTable.add(essenceLabel).row();
        contentTable.add(backButton);

        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void back() {
//        //////log.debug("back()");
        game.goToMain();
    }
}
