package snow.zhen.zayta.main.sokoban.input;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.assets.AssetDescriptors;
import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.main.sokoban.PlayController;
import snow.zhen.zayta.main.sokoban.movement.Direction;

public class Hud extends Stage{

    private BitmapFont font;
    private Skin skin;
    private snow.zhen.zayta.main.sokoban.PlayController playController;
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

        table.add(gameStateBtns()).top().fillX().padTop(GameConfig.PADDING).row();
        table.add(directionBtnControls()).fillX().expand().bottom().left();
        this.addActor(table);
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
        table.invalidateHierarchy();
    }

    //==Game state buttons for undo and restart lvl==//
    private Table gameStateBtns(){
        Table table = new Table();
        table.add(infoBtn()).expandX().left();
        Table resetMvementTable = new Table();
        resetMvementTable.add(restart()).left().pad(GameConfig.PADDING);
        resetMvementTable.add(undoBtn()).right().pad(GameConfig.PADDING);
        table.add(resetMvementTable).expandX().right();


        return table;
    }
    private Button undoBtn(){
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_UNDO));
        ImageButton undoButton = new ImageButton(textureRegionDrawable);
        undoButton.getStyle().imageUp=textureRegionDrawable;
        undoButton.getStyle().imageDown=textureRegionDrawable.tint(GameConfig.DARK_TINT);

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
    private Button infoBtn(){
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_INFO));
        final ImageTextButton infoButton = new ImageTextButton("",skin);
        infoButton.getStyle().imageUp=textureRegionDrawable;
        infoButton.getStyle().imageDown=textureRegionDrawable.tint(GameConfig.DARK_TINT);

//        TextButton restartButton = new TextButton("Restart", skin);
        infoButton.addListener(new ChangeListener() {
            boolean toggledInfo =false;
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggledInfo = !toggledInfo;
                if(toggledInfo){
                    infoButton.setText("Put the crates in place.\n Move only one at a time.");
                }
                else{
                    infoButton.setText("");
                }
            }
        });
        return infoButton;
    }

    //==Btns for controlling the playable character==//
    private Table directionBtnControls(){
        Table btnControls = new Table(skin);
        Table verticalCtrls = new Table(skin);
        Table horizontalCtrls = new Table(skin);

        horizontalCtrls.add(directionBtn(Direction.left)).padRight(GameConfig.PADDING);
        horizontalCtrls.add(directionBtn(Direction.right));

        verticalCtrls.add(directionBtn(Direction.up)).row();
        verticalCtrls.add(directionBtn(Direction.down)).padTop(GameConfig.PADDING);

        btnControls.add(horizontalCtrls).expandX().left();
        btnControls.add(verticalCtrls).expandX().right();

        return btnControls;
    }
    private Button directionBtn(final Direction direction){
        String regionName="";
        switch (direction){
            case none:
                regionName= RegionNames.BTN_STOP;
            case up:
                regionName = RegionNames.BTN_UP;
                break;
            case down:
                regionName = RegionNames.BTN_DOWN;
                break;
            case left:
                regionName = RegionNames.BTN_LEFT;
                break;
            case right:
                regionName = RegionNames.BTN_RIGHT;
                break;
        }
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable
                (btnsAtlas.findRegion(regionName));
        ImageButton dirButton = new ImageButton(textureRegionDrawable);
        dirButton.getStyle().imageUp=textureRegionDrawable;
        dirButton.getStyle().imageDown=textureRegionDrawable.tint(GameConfig.DARK_TINT);
        dirButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playController.moveNighters(direction);
            }
        });

        return dirButton;
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
                            playController.moveNighters(snow.zhen.zayta.main.sokoban.movement.Direction.none);
                            return;
                        }

                        if(Math.abs(deltaX)>Math.abs(deltaY)) {
                            if (deltaX > 0)
                                playController.moveNighters(snow.zhen.zayta.main.sokoban.movement.Direction.right);
                            else if (deltaX < 0)
                                playController.moveNighters(snow.zhen.zayta.main.sokoban.movement.Direction.left);
                        }
                        else{
                            if (deltaY > 0)
                                playController.moveNighters(snow.zhen.zayta.main.sokoban.movement.Direction.up);
                            else if (deltaY < 0)
                                playController.moveNighters(Direction.down);
                        }

                    }
                }
        );
        return joystick;
    }

}
