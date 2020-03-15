package exp.zhen.zayta.main;

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

import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.assets.RegionNames;
//import exp.zhen.zayta.main.arcade_style_game.config.SizeManager;
//import exp.zhen.zayta.main.arcade_style_game.puzzle.Puzzle;
import exp.zhen.zayta.main.sokoban.Puzzle;
import exp.zhen.zayta.main.shop.ShopScreen;
import exp.zhen.zayta.main.story.StoryBoardScreen;
import exp.zhen.zayta.main.story.StoryScreen;

class MainScreen extends ScreenBase {

    private final Logger log = new Logger(exp.zhen.zayta.main.MainScreen.class.getName(), Logger.DEBUG);

    private Table table;
    private StoryScreen storyScreen;
    private PlayScreen playScreen;
    private UserData userData;

    MainScreen(Game game) {
        super(game);
        this.userData = game.getUserData();
        table = new Table();
    }

    void createScreens()
    {
        storyScreen = new StoryScreen(game);
        playScreen = new PlayScreen(game);
    }


    @Override
    protected Actor createUi() {
        table.clearChildren();
        TextureAtlas textureAtlas = assetManager.get(AssetDescriptors.LAB);

        setBackground(textureAtlas);

        Label label = new Label("Puzzle "+userData.getNumScenesUnlocked(),new Label.LabelStyle(assetManager.get(AssetDescriptors.HEADING_FONT),Color.WHITE));
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
        int time = userData.getNumScenesUnlocked()%3;
        TextureRegion sky;
        if (time == 0) {
            sky = textureAtlas.findRegion(RegionNames.SKY_NIGHT);
        }
        else if(time ==1){
            sky = textureAtlas.findRegion(RegionNames.SKY_SUNRISE);
        }
        else{
            sky = textureAtlas.findRegion(RegionNames.SKY_DAY);
        }


        table.setBackground(new TextureRegionDrawable(sky));
    }

    private ImageButton scene(TextureAtlas textureAtlas){
        TextureRegion scene = textureAtlas.findRegion(RegionNames.FULL_SCANNER);
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

        // high score button
        TextButton shopButton = new TextButton("SHOP", uiskin);
        shopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shop();
            }
        });

        // options button
        TextButton storyButton = new TextButton("STORY", uiskin);
        storyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                story();
            }
        });

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
        buttonTable.add(playButton).width(SizeManager.WIDTH/numButtons);
        buttonTable.add(shopButton).width(SizeManager.WIDTH/numButtons);
        buttonTable.add(storyButton).width(SizeManager.WIDTH/numButtons);
        buttonTable.add(quitButton).width(SizeManager.WIDTH/numButtons);


        buttonTable.bottom();
        return buttonTable;
    }

    private void play() {
//        puzzle.setCurrentLvl(chosenLvl);
        game.setScreen(playScreen);
    }

    private void shop() {
        game.setScreen(new ShopScreen(game));
    }

    private void story() {
        game.setScreen(new StoryBoardScreen(game,storyScreen));
    }

    private void quit() {
        Gdx.app.exit();
    }

}