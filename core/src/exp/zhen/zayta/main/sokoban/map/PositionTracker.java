package exp.zhen.zayta.main.sokoban.map;

import exp.zhen.zayta.main.GameConfig;
import exp.zhen.zayta.main.arcade_style_game.config.SizeManager;
import exp.zhen.zayta.main.sokoban.Updateable;
import exp.zhen.zayta.main.sokoban.entity.EntityBase;
import exp.zhen.zayta.util.BiMap;

public class PositionTracker {
    private int mapWidth;
    private BiMap<Integer, EntityBase> globalTracker;
    public PositionTracker(int mapWidth){
        globalTracker = new BiMap<Integer, EntityBase>();
        this.mapWidth = mapWidth;
    }
    public void init(int mapWidth){
        globalTracker.clear();
        this.mapWidth = mapWidth;
    }
    public void update(EntityBase entity, int x, int y) {

        int key=generateKey(x,y);

        globalTracker.remove(key);
        globalTracker.put(key,entity);
    }
    private int generateKey(int x, int y)
    {
        int n = mapWidth/ GameConfig.ENTITY_SIZE;
        return x*n+y;
    }

    public EntityBase getEntityAtPos(int x, int y){
        return globalTracker.get(generateKey(x,y));
    }

    public void clear(){
        globalTracker.clear();
    }
}
