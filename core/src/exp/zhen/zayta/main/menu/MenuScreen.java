package exp.zhen.zayta.main.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import exp.zhen.zayta.RPG;
import exp.zhen.zayta.assets.AssetDescriptors;
import exp.zhen.zayta.main.ScreenBase;
import exp.zhen.zayta.main.game.GameScreen;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.shop.ShopScreen;
import exp.zhen.zayta.main.story.StoryScreen;


public class MenuScreen extends ScreenBase {

    private static final Logger log = new Logger(MenuScreen.class.getName(), Logger.DEBUG);

    public MenuScreen(RPG game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas menuAtlas = assetManager.get(AssetDescriptors.MENU_CLIP);

        TextureRegion backgroundRegion = menuAtlas.findRegion("fullscanner");
        table.setBackground(new TextureRegionDrawable(backgroundRegion));


        table.add(clip(menuAtlas));
        table.row();
        table.pad(20);
        table.add(buttonTable());
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private ImageButton clip(TextureAtlas menuAtlas){
        TextureRegion clip = menuAtlas.findRegion("Lorale");
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(clip);
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
        game.setScreen(new GameScreen(game));
    }

    private void shop() {
        game.setScreen(new ShopScreen(game));
    }

    private void story() {
        game.setScreen(new StoryScreen(game));
    }

    private void quit() {
        Gdx.app.exit();
    }

}
