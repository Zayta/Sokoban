package snow.zhen.zayta.main.story;

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
import snow.zhen.zayta.main.assets.UIRegionNames;


public class StoryBoardScreen extends ScreenBase {
    //lists all episodes w no data on them picks one to play
    private static final Logger log = new Logger(StoryBoardScreen.class.getName(),Logger.DEBUG);
    private final int numPerRow = 3;
    private snow.zhen.zayta.main.story.StoryScreen storyScreen;
    public StoryBoardScreen(Game game, StoryScreen storyScreen) {
        super(game);
        this.storyScreen = storyScreen;
    }

    //todo make array of n story-pics, and draw UserData.numScenesUnlocked of the n pics


    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.YELLOW);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Title
        Label title = new Label("Memories", uiSkin);

        // memories label
        int numScenesUnlocked = game.getUserData().getNumCompleted();
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
        contentTable.setBackground(UIRegionNames.WINDOW);

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
//        //////log.debug("back()");
        game.goToMain();
    }
    private Table getStoryTable(Skin skin){
        Table episodeTable = new Table(skin);
        episodeTable.defaults().pad(10);
        episodeTable.setBackground(UIRegionNames.WINDOW);

        for(int i = 0; i<=game.getUserData().getNumCompleted();i++) {
            TextButton episodeButton = new TextButton("Memory "+i, skin);
            final int episode = i;
            episodeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    playStory(episode);
                }
            });

            episodeTable.add(episodeButton).padRight(10);
            if(i%numPerRow==numPerRow-1)
                episodeTable.row();

        }


        return episodeTable;
    }
    private void playStory(int episode){
        storyScreen.loadEpisode(episode);
        game.setScreen(storyScreen);
    }

}
