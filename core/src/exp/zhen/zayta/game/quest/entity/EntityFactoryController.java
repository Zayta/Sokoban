package exp.zhen.zayta.game.quest.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import exp.zhen.zayta.config.SizeManager;

public class EntityFactoryController {
    private EntityFactory entityFactory;
    public EntityFactoryController(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }
    public void addEntities() {
        entityFactory.addBackground();
        addStones();
        addCivilians();
        addPlayer();
    }
    private void addStones(){
        int numStones = 4;
        Vector2[] points = Arrangements.circle(numStones,SizeManager.WORLD_CENTER_X,SizeManager.WORLD_CENTER_Y,SizeManager.WORLD_WIDTH/3);
        for(int i =0; i<numStones; i++)
        {
            entityFactory.addStone(points[i].x,points[i].y);
        }
    }
    private void addCivilians(){
        //todo also in future make civilians change direction randomly
        /*add Civilization*/
        int numCivilians = 5;
        float minX = 0; float maxX = SizeManager.WORLD_WIDTH-SizeManager.maxObjWidth;
        float minY = 0; float maxY = SizeManager.WORLD_HEIGHT-SizeManager.maxObjHeight;
        for(int i = 0; i<numCivilians; i++) {
            float civX = MathUtils.random(minX,maxX);
            float civY = MathUtils.random(minY,maxY);
            entityFactory.addCivilian(civX, civY);
        }
        //todo in future make sure civ doesnt start where nighter is. change maxX and maxY above or minX and minY

    }
    private void addPlayer(){
        /*add NUR*/
        float playerStartX = (SizeManager.WORLD_WIDTH - SizeManager.maxObjWidth)/2;
        float playerStartY = 1-SizeManager.maxObjHeight/2;
        entityFactory.addPlayer(playerStartX,playerStartY);
    }
}
