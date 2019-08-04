package exp.zhen.zayta.main.game.essence_lab.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;

import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;

public class CameraUpdateSystem extends EntitySystem {

    private static final Logger log = new Logger(CameraUpdateSystem.class.getName(),Logger.DEBUG);
    private Camera camera; private TiledMap tiledMap;

    private Family PLAYABLE_CHARACTERS = Family.all(
            PlayerTag.class,Position.class
    ).get();
    private ImmutableArray<Entity> playableCharacters;
    public CameraUpdateSystem(Camera camera, TiledMap tiledMap){
        this.camera = camera;
        this.tiledMap = tiledMap;
        this.playableCharacters = getEngine().getEntitiesFor(PLAYABLE_CHARACTERS);
    }

    @Override
    public void update(float deltaTime) {
        //todo need to make a way to zoom out if two players are too far apart, then can't see either of them.
        int num = playableCharacters.size();
        float posX =0, posY =0;
        for(int i = 0; i<num;i++) {
            Entity player = this.playableCharacters.get(i);

            Position playerPos = player.getComponent(Position.class);
            posX +=playerPos.getX();
            posY +=playerPos.getY();
        }
        camera.position.x = posX/num;
        camera.position.y = posY/num;

        checkBoundsOfCamera();


        camera.update();

    }

    private void checkBoundsOfCamera(){
        MapProperties prop = tiledMap.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
//        int tilePixelWidth = prop.get("tilewidth", Integer.class);
//        int tilePixelHeight = prop.get("tileheight", Integer.class);

    //            int mapPixelWidth = mapWidth * tilePixelWidth;
    //            int mapPixelHeight = mapHeight * tilePixelHeight;

        // These values likely need to be scaled according to world coordinates.
    // The left boundary of the tiledMap (x)
        int tiledMapLeft = 0;
    // The right boundary of the tiledMap (x + width)
        int tiledMapRight = 0 + mapWidth;
    // The bottom boundary of the tiledMap (y)
        int tiledMapBottom = 0;
    // The top boundary of the tiledMap (y + height)
        int tiledMapTop = 0 + mapHeight;
    // The camera dimensions, halved
        float cameraHalfWidth = camera.viewportWidth * .5f;
        float cameraHalfHeight = camera.viewportHeight * .5f;

    // Move camera after player as normal

        float cameraLeft = camera.position.x - cameraHalfWidth;
        float cameraRight = camera.position.x + cameraHalfWidth;
        float cameraBottom = camera.position.y - cameraHalfHeight;
        float cameraTop = camera.position.y + cameraHalfHeight;

    // Horizontal axis
        if(mapWidth < camera.viewportWidth)
        {
            camera.position.x = tiledMapRight / 2;
        }
        else if(cameraLeft <= tiledMapLeft)
        {
            camera.position.x = tiledMapLeft + cameraHalfWidth;
        }
        else if(cameraRight >= tiledMapRight)
        {
            camera.position.x = tiledMapRight - cameraHalfWidth;
        }

    // Vertical axis
        if(mapHeight < camera.viewportHeight)
        {
            camera.position.y = tiledMapTop / 2;
        }
        else if(cameraBottom <= tiledMapBottom)
        {
            camera.position.y = tiledMapBottom + cameraHalfHeight;
        }
        else if(cameraTop >= tiledMapTop)
        {
            camera.position.y = tiledMapTop - cameraHalfHeight;
        }
    }
}
