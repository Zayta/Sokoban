package exp.zhen.zayta.main.game.wake.entity;

import com.badlogic.gdx.math.MathUtils;

import exp.zhen.zayta.main.game.config.SizeManager;

public class EntityFactoryController {
    private EntityFactory entityFactory;
    public EntityFactoryController(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }
    public void addEntities() {
        addCivilians();
        addPlayer();
    }

    private void addCivilians(){
        //todo also in future make civilians change direction randomly
        /*add Civilization*/
        int numCivilians = 5;
        float minX = 0; float maxX = SizeManager.WAKE_WORLD_WIDTH-SizeManager.maxObjWidth;
        float minY = 0; float maxY = SizeManager.WAKE_WORLD_HEIGHT-SizeManager.maxObjHeight;
        for(int i = 0; i<numCivilians; i++) {
            float civX = MathUtils.random(minX,maxX);
            float civY = MathUtils.random(minY,maxY);
            entityFactory.addCivilian(civX, civY);
        }
        //todo in future make sure civ doesnt start where nighter is. change maxX and maxY above or minX and minY

    }
    private void addPlayer(){
        /*add NUR_Awake_List*/
        float playerStartX = (SizeManager.WAKE_WORLD_WIDTH - SizeManager.maxObjWidth)/2;
        float playerStartY = 1-SizeManager.maxObjHeight/2;
        entityFactory.addPlayer(playerStartX,playerStartY);
    }
}
