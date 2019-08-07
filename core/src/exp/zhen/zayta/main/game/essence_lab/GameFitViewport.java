package exp.zhen.zayta.main.game.essence_lab;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;

import exp.zhen.zayta.main.game.config.SizeManager;

public class GameFitViewport extends FitViewport {

    private static final Logger log = new Logger(LabGame.class.getName(),Logger.DEBUG);

    public GameFitViewport(float worldWidth, float worldHeight, Camera camera) {
        super(worldWidth, worldHeight, camera);

    }
    @Override
    public void update (int screenWidth, int screenHeight, boolean centerCamera) {
        if(screenWidth>screenHeight)
            landscapeView(screenWidth,screenHeight,centerCamera);
        else
            portraitView(screenWidth,screenHeight,centerCamera);
    }

    private void portraitView(int screenWidth, int screenHeight, boolean centerCamera){

        int gameHeight = (int)(screenHeight*SizeManager.GAME_TO_CONTROL_RATIO);
        Vector2 scaled = getScaling().apply(getWorldWidth(), getWorldHeight(), screenWidth, gameHeight);
        int viewportWidth = Math.round(scaled.x);
        int viewportHeight = Math.round(scaled.y);

        // Center.
        setScreenBounds((screenWidth - viewportWidth) / 2,(gameHeight - viewportHeight) / 2+ (int)(SizeManager.CONTROLLER_DIAMETER*screenHeight/SizeManager.HEIGHT), viewportWidth, viewportHeight);
        apply(centerCamera);
    }


    private void landscapeView(int screenWidth, int screenHeight, boolean centerCamera){
        //log.debug("landscapeView was called");

        int gameWidth = (int)(screenWidth*SizeManager.GAME_TO_CONTROL_RATIO);
        Vector2 scaled = getScaling().apply(getWorldWidth(), getWorldHeight(), gameWidth, screenHeight);
        int viewportWidth = Math.round(scaled.x);
        int viewportHeight = Math.round(scaled.y);


        setScreenBounds((gameWidth - viewportWidth) / 2+ (int)(SizeManager.CONTROLLER_DIAMETER*screenWidth/SizeManager.WIDTH),(screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);
        apply(centerCamera);
    }
//
//    @Override
//    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
//        super.update(screenWidth,(int)(screenHeight*SizeManager.GAME_TO_CONTROL_RATIO),true);
////        Vector2 scaled = getScaling().apply(getWorldWidth(), getWorldHeight(), screenWidth, screenHeight*SizeManager.GAME_TO_CONTROL_RATIO);
////        int viewportWidth = Math.round(scaled.x);
////        int viewportHeight = Math.round(scaled.y);
////
////        // Center.
////        setScreenBounds((screenWidth - viewportWidth) / 2, ((int)(screenHeight*SizeManager.GAME_TO_CONTROL_RATIO) - viewportHeight) / 2+SizeManager.CONTROLLER_DIAMETER,
////                viewportWidth, viewportHeight);
////
////        apply(centerCamera);
//    }
}
