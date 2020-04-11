package snow.zhen.zayta.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.main.assets.AssetDescriptors;
import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.main.sokoban.LevelSelectionScreen;
import snow.zhen.zayta.main.sokoban.PlayScreen;
import snow.zhen.zayta.main.story.StoryBoardScreen;
import snow.zhen.zayta.main.story.StoryScreen;

class MainScreen extends ScreenBase {

    private final Logger log = new Logger(MainScreen.class.getName(), Logger.DEBUG);

    private Table table;
    private snow.zhen.zayta.main.story.StoryScreen storyScreen;
    private snow.zhen.zayta.main.sokoban.PlayScreen playScreen;
    private UserData userData;

    MainScreen(Game game, PlayScreen playScreen,StoryScreen storyScreen) {
        super(game);
        this.userData = game.getUserData();
        table = new Table();
        this.storyScreen = storyScreen;
        this.playScreen = playScreen;
    }


    @Override
    protected Actor createUi() {
        table.clearChildren();
        TextureAtlas textureAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        setBackground(textureAtlas);

        Label label = new Label("Experiment "+userData.getNumCompleted(),
                new Label.LabelStyle(assetManager.get(AssetDescriptors.FONT),Color.WHITE));
        label.setFontScale(2);

        table.add(label).top().left();
        table.row();

        table.add(scene(textureAtlas));
        table.row();
        table.pad(20);
        table.add(buttonTable());
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }
    
    private void setBackground(TextureAtlas textureAtlas){
        int time = userData.getNumCompleted()%3;
        TextureRegion sky;
        if (time == 0) {
            sky = textureAtlas.findRegion(RegionNames.NIGHT_SKY);
        }
        else if(time ==1){
            sky = textureAtlas.findRegion(RegionNames.SUNRISE);
        }
        else{
            sky = textureAtlas.findRegion(RegionNames.DAY_SKY);
        }


        table.setBackground(new TextureRegionDrawable(sky));
    }

    private ImageButton scene(TextureAtlas textureAtlas){
        TextureRegion scene = textureAtlas.findRegion(RegionNames.UI_TOUCHPAD_BCKGRND);
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(scene);
        ImageButton imageButton = new ImageButton(textureRegionDrawable){};
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });
        imageButton.top();
        return imageButton;
    }


    private Table buttonTable(){
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);

        // play button
        TextButton playButton = new TextButton("PLAY", uiskin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });
        //lvl select button
        TextButton lvlSelectBtn = new TextButton("VIEW LVLS", uiskin);
        lvlSelectBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                viewLvls();
            }
        });
//
//        // story button
//        TextButton storyButton = new TextButton("STORY", uiskin);
//        storyButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                story();
//            }
//        });

        // quit button
        TextButton quitButton = new TextButton("QUIT", uiskin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });

        // setup table
        Table buttonTable = new Table(uiskin);
        int numButtons = 4;
        buttonTable.add(playButton).width(GameConfig.WIDTH/numButtons);
        buttonTable.add(lvlSelectBtn).width(GameConfig.WIDTH/numButtons);
//        buttonTable.add(storyButton).width(GameConfig.WIDTH/numButtons);
        buttonTable.add(quitButton).width(GameConfig.WIDTH/numButtons);


        buttonTable.bottom();
        return buttonTable;
    }

    private void play() {
        playScreen.setLvl(game.getUserData().getNumCompleted());
        game.setScreen(playScreen);
    }

    private void viewLvls() {
        game.setScreen(new LevelSelectionScreen(game,playScreen));
    }

    private void story() {
        game.setScreen(new StoryBoardScreen(game,storyScreen));
    }

    private void quit() {
        Gdx.app.exit();
    }



}