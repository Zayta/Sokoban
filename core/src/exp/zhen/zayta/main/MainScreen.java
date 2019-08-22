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

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.Experiment;
import exp.zhen.zayta.main.shop.ShopScreen;
import exp.zhen.zayta.main.story.StoryBoardScreen;
import exp.zhen.zayta.main.story.StoryScreen;

class MainScreen extends ScreenBase {

    private final Logger log = new Logger(exp.zhen.zayta.main.MainScreen.class.getName(), Logger.DEBUG);

    private StoryScreen storyScreen;
    private Experiment experiment; private UserData userData;

    MainScreen(RPG game) {
        super(game);
        this.userData = game.getUserData();
    }

    void createScreens()
    {
        storyScreen = new StoryScreen(game);
        experiment = new Experiment(game);
    }


    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas menuAtlas = assetManager.get(UIAssetDescriptors.MENU_CLIP);

//            TextureRegion backgroundRegion = menuAtlas.findRegion("fullscanner");
//            table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Label label = new Label("Experiment "+userData.getNumScenesUnlocked(),new Label.LabelStyle(assetManager.get(UIAssetDescriptors.HEADING_FONT),Color.WHITE));
        label.setFontScale(2);

        table.add(label).top().left();
        table.row();

        table.add(scene(menuAtlas));
        table.row();
        table.pad(20);
        table.add(buttonTable());
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private ImageButton scene(TextureAtlas menuAtlas){
        TextureRegion scene = menuAtlas.findRegion("fullscanner");
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(scene);
        ImageButton imageButton = new ImageButton(textureRegionDrawable){};
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play(userData.getNumScenesUnlocked());
            }
        });
        imageButton.top();
        return imageButton;
    }


    private Table buttonTable(){
        Skin uiskin = assetManager.get(UIAssetDescriptors.UI_SKIN);
        // play button
        TextButton playButton = new TextButton("PLAY", uiskin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play(userData.getNumScenesUnlocked());
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

    private void play(int chosenLvl) {
        experiment.setCurrentLvl(chosenLvl);
        game.setScreen(experiment);
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