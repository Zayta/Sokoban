package exp.zhen.zayta.main.sokoban.input;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import exp.zhen.zayta.main.GameConfig;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.main.sokoban.PlayController;
import exp.zhen.zayta.main.sokoban.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.input.move_character.joystick.JoyStickMovementController;

public class Hud extends Stage{

    private BitmapFont font;
    private Skin skin;
    private PlayController playController;
    private Table table;
    private TextureAtlas btnsAtlas;
    public Hud(final PlayController playController, SpriteBatch spriteBatch, AssetManager assetManager) {
        super(new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT), spriteBatch);
        this.btnsAtlas = assetManager.get(AssetDescriptors.UI_BTNS);
        this.playController = playController;

        this.font = assetManager.get(AssetDescriptors.FONT);
        font.setColor(Color.BLACK);

        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);

        table = new Table(skin);
        table.setFillParent(true);
        table.setDebug(true);
//        table.setBackground(RegionNames.PANEL);

        table.add(joystick()).expand().bottom().left();
        table.add(restart()).row();
        table.add(undoBtn()).pad(10f);
        this.addActor(table);
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
        table.invalidateHierarchy();
    }

    private Button undoBtn(){
        TextButton undoButton = new TextButton("Undo", skin);
        undoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playController.undoMove();
            }
        });
        return undoButton;
    }
    private Button restart(){

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_REFRESH));

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        ImageButton restartButton = new ImageButton(textureRegionDrawable);
        restartButton.getStyle().imageUp=textureRegionDrawable;
        restartButton.getStyle().imageDown=textureRegionDrawable.tint(GameConfig.DARK_TINT);

//        TextButton restartButton = new TextButton("Restart", skin);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playController.restart();
            }
        });
        return restartButton;
    }

    private Table btnControls(){
        Table btnControls = new Table(skin);
        return btnControls;
    }



    private Touchpad joystick(){
        Touchpad joystick = new Touchpad(GameConfig.JOYSTICK_RADIUS,skin);
        joystick.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        // This is run when anything is changed on this actor.
                        float deltaX = ((Touchpad) actor).getKnobPercentX();
                        float deltaY = ((Touchpad) actor).getKnobPercentY();

                        if(deltaX==0&&deltaY==0){
                            playController.moveNighters(Direction.none);
                            return;
                        }

                        if(Math.abs(deltaX)>Math.abs(deltaY)) {
                            if (deltaX > 0)
                                playController.moveNighters(Direction.right);
                            else if (deltaX < 0)
                                playController.moveNighters(Direction.left);
                        }
                        else{
                            if (deltaY > 0)
                                playController.moveNighters(Direction.up);
                            else if (deltaY < 0)
                                playController.moveNighters(Direction.down);
                        }

                    }
                }
        );
        return joystick;
    }

}
