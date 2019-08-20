package exp.zhen.zayta.main.story;

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
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.ScreenBase;
import exp.zhen.zayta.main.UiRegionNames;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;


public class StoryBoardScreen extends ScreenBase {
    //lists all episodes w no data on them picks one to play
    private static final Logger log = new Logger(StoryBoardScreen.class.getName(),Logger.DEBUG);

    public StoryBoardScreen(RPG game) {
        super(game);
    }

    //todo make array of n story-pics, and draw UserData.numScenesUnlocked of the n pics


    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas wakePlayAtlas = assetManager.get(UIAssetDescriptors.LAB);
        Skin uiSkin = assetManager.get(UIAssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = wakePlayAtlas.findRegion(WPRegionNames.BACKGROUND);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Title
        Label title = new Label("Memories", uiSkin);

        // memories label
        int numScenesUnlocked = game.getUserData().getNumScenesUnlocked();
        Label pointsLabel = new Label(String.valueOf(numScenesUnlocked), uiSkin);

        // back button
        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        Table storyTable = getStoryTable(uiSkin);

        // setup tables
        Table contentTable = new Table(uiSkin);
        contentTable.defaults().pad(20);
        contentTable.setBackground(UiRegionNames.WINDOW);

        contentTable.add(title).row();
        contentTable.add(pointsLabel).row();
        contentTable.add(storyTable).row();
        contentTable.add(backButton);

        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void back() {
//        ////log.debug("back()");
        game.goToMain();
    }
    private Table getStoryTable(Skin skin){
        Table episodeTable = new Table(skin);
        episodeTable.defaults().pad(10);
        episodeTable.setBackground(UiRegionNames.WINDOW);

        TextButton prologueButton = new TextButton("Prologue", skin);
        prologueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playStory();
            }
        });

        episodeTable.add(prologueButton);

        return episodeTable;
    }
    private void playStory(){
        game.setScreen(new StoryScreen());
    }

}
