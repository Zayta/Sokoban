package snow.zhen.zayta.main.sokoban.input;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import snow.zhen.zayta.main.Game;
import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.assets.AssetDescriptors;
import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.main.assets.UIRegionNames;
import snow.zhen.zayta.main.sokoban.PlayController;
import snow.zhen.zayta.main.sokoban.movement.Direction;

public class Hud extends Stage{

    private BitmapFont font;
    private Skin skin;
    private snow.zhen.zayta.main.sokoban.PlayController playController;
    private Table table;
    private TextureAtlas btnsAtlas;
    private final int btnSize = 120;
    private final int smallBtnHeight = 95;
    private boolean showSettings = false;
    private Table settingsTable;
    private final Game game;

    public Hud(final PlayController playController, Game game) {
        super(new ExtendViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT), game.getBatch());
        this.game = game;
        AssetManager assetManager = game.getAssetManager();
        this.btnsAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);
        this.playController = playController;

        this.font = assetManager.get(AssetDescriptors.FONT);
        font.setColor(Color.BLACK);

        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);

        table = new Table(skin);
        table.setFillParent(true);
        this.settingsTable = settingsTable();
        this.addActor(table);
//        table.setBackground(RegionNames.PANEL);

        createHud(getViewport().getScreenWidth(),getViewport().getScreenHeight());
    }
    private void createHud(int width, int height){
        Stack stack = new Stack();

        if(width>height) {//landscape
            stack.add(horizontalHud());//.expand().fill();
        }
        else{//portrait

            stack.add(verticalFlexHud());//.expand().fill();
//            table.add(verticalHud()).expand().fill();
        }

        Table settingsBtnWrapper = new Table();
        settingsBtnWrapper.add(settingsBtns()).expand().left().top();
        stack.add(settingsBtnWrapper);


        stack.add(settingsTable);

        showSettings = false;
        settingsTable.setVisible(showSettings);
        table.add(stack).expand().fill();
        table.pack();
    }

    public void resize(int width, int height) {
        getViewport().setWorldSize(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT);
        getViewport().update(width, height,true);
        table.clearChildren();
        table.invalidateHierarchy();
        createHud(width,height);
    }

    /*Vertical controls*/
    public Table verticalHud(){
        Table table = new Table(skin);
        table.add(gameStateBtnsVertical()).expand().bottom();
        table.add(directionBtnControlsCross()).expand().bottom().right().padBottom(GameConfig.PADDING);
        return table;
    }
    private Table gameStateBtnsVertical(){
        Table table = new Table();
        Table resetMvementTable = new Table();
        resetMvementTable.add(restart()).left().size(btnSize).pad(GameConfig.PADDING);
        resetMvementTable.add(undoBtn()).right().size(btnSize).pad(GameConfig.PADDING);
        table.add(resetMvementTable).expandX().right();
        return table;
    }
    private Table directionBtnControlsCross(){
        Table controls = new Table(skin);
        Button upBtn = directionBtn(Direction.up), leftBtn = directionBtn(Direction.left),
        rightBtn = directionBtn(Direction.right),downBtn = directionBtn(Direction.down);


        controls.add(upBtn).padLeft(btnSize).size(btnSize).row();
        controls.add(leftBtn).padRight(btnSize).size(btnSize);
        controls.add(rightBtn).size(btnSize).row();
        controls.add(downBtn).padLeft(btnSize).size(btnSize);
        return controls;
    }

    /**/
    private Table verticalFlexHud(){
        Table table = new Table();


        table.add(gameStateBtnsVertical()).expand().left().bottom().padBottom(btnSize+GameConfig.PADDING);
        table.add(joystick()).expand().bottom().right().size(350);
        return table;
    }
    private Table settingsBtns(){
        Table verticalGroup = new Table();
        verticalGroup.add(settingsBtn()).left().top().size(smallBtnHeight).row();

        return verticalGroup;
    }

    /*Horizontal controls*/
    private Table horizontalHud(){
        Table table = new Table(skin);
        table.add(gameStateBtnsLandscape()).top().fillX().padTop(GameConfig.PADDING).row();
        table.add(directionBtnControlsLandscape()).fillX().expand().bottom().left();

//        table.add(directionBtnControlsCross()).expand().bottom().right().padBottom(GameConfig.PADDING);
        return table;
    }
    private Table gameStateBtnsLandscape(){
        Table table = new Table();
//        Button settingsBtn = settingsBtn();
//        table.add(settingsBtn).expandX().left();
        Table resetMvementTable = new Table();
        resetMvementTable.add(restart()).left().pad(GameConfig.PADDING);
        resetMvementTable.add(undoBtn()).right().pad(GameConfig.PADDING);
        table.add(resetMvementTable).expandX().right();


        return table;
    }
    private Table directionBtnControlsLandscape(){
        Table btnControls = new Table(skin);
        Table verticalCtrls = new Table(skin);
        Table horizontalCtrls = new Table(skin);


        verticalCtrls.add(directionBtn(Direction.up)).size(btnSize).padBottom(GameConfig.PADDING).row();
        verticalCtrls.add(directionBtn(Direction.down)).size(btnSize).pad(GameConfig.PADDING);

        horizontalCtrls.add(directionBtn(Direction.left)).size(btnSize).padRight(GameConfig.PADDING);
        horizontalCtrls.add(directionBtn(Direction.right)).size(btnSize).padLeft(GameConfig.PADDING);


        btnControls.add(horizontalCtrls).expandX().left();
        btnControls.add(verticalCtrls).expandX().right();

        return btnControls;
    }

    private Table settingsTable(){
        Table wrapper = new Table();

        Table table = new Table(skin);

        table.add(new Label("Settings",skin)).expandY().top().row();
//        table.setDebug(true);
        table.setBackground(UIRegionNames.WINDOW);
        table.add(exitBtn()).width(btnSize).expandY().center().row();
        //for exiting settings menu
        TextureRegionDrawable x = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_X));
        ImageTextButton cancelBtn = new ImageTextButton("Cancel", skin);
//        cancelBtn.getStyle().imageUp=x;
//        cancelBtn.getStyle().imageDown=x.tint(GameConfig.DARK_TINT);

        cancelBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSettings = false;
                settingsTable.setVisible(showSettings);
            }
        });
        table.add(cancelBtn).expandY().bottom();
        wrapper.add(table).expand().fillY().width(500).pad(GameConfig.PADDING);

        return wrapper;
    }
    //==Game state buttons for undo and restart lvl==//
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
    private Button settingsBtn(){
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_LIST));
//        final ImageTextButton settingsButton = new ImageTextButton("",skin);
//        final ImageButton settingsButton = new ImageButton(skin);
//        settingsButton.getStyle().imageUp=textureRegionDrawable;
//        settingsButton.getStyle().imageDown=textureRegionDrawable.tint(GameConfig.DARK_TINT);
        final ImageButton settingsButton = new ImageButton(textureRegionDrawable,textureRegionDrawable.tint(GameConfig.DARK_TINT));
//        TextButton restartButton = new TextButton("Restart", skin);
        settingsButton.align(Align.left);

        settingsButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSettings = !showSettings;
                settingsTable.setVisible(showSettings);
            }
        });
        return settingsButton;
    }
    private Button exitBtn(){
        ImageTextButton exitButton = new ImageTextButton("Exit",skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMain();
            }
        });
        return exitButton;
    }
    //==Btns for controlling the playable character==//

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



    private Stack joystick(){
        final Stack stack = new Stack();
//        stack.setDebug(true);
        final TextureRegionDrawable touchpadBckgrnd = null;//new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.UI_TOUCHPAD_BCKGRND));

        final FourDirectionalTouchpad joystick = new FourDirectionalTouchpad(GameConfig.JOYSTICK_RADIUS, new FourDirectionalTouchpad.TouchpadStyle(
                touchpadBckgrnd,null)
        );
        joystick.addListener(

                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        // This is run when anything is changed on this actor.
                        float deltaX = ((FourDirectionalTouchpad) actor).getKnobPercentX();
                        float deltaY = ((FourDirectionalTouchpad) actor).getKnobPercentY();

                        if(deltaX==0&&deltaY==0){
//                            joystick.getStyle().background = touchpadBckgrnd;
                            playController.moveNighters(Direction.none);
                            return;
                        }

                        if(Math.abs(deltaX)>Math.abs(deltaY)) {
                            if (deltaX > 0){

//                                joystick.getStyle().background = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_RIGHT));
                                playController.moveNighters(snow.zhen.zayta.main.sokoban.movement.Direction.right);}
                            else if (deltaX < 0){

//                                joystick.getStyle().background = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_LEFT));
                                playController.moveNighters(snow.zhen.zayta.main.sokoban.movement.Direction.left);}
                        }
                        else{
                            if (deltaY > 0){

//                                joystick.getStyle().background = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_UP));
                                playController.moveNighters(snow.zhen.zayta.main.sokoban.movement.Direction.up);}
                            else if (deltaY < 0){

//                                joystick.getStyle().background = new TextureRegionDrawable(btnsAtlas.findRegion(RegionNames.BTN_DOWN));
                                playController.moveNighters(Direction.down);}
                        }

                    }
                }
        );
        stack.add(joystick);
        stack.add(directionBtnControlsCross());
        return stack;
    }

}
