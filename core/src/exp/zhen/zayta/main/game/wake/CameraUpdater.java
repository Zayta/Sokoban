package exp.zhen.zayta.main.game.wake;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraUpdater extends EntitySystem {
    OrthographicCamera camera;
    public CameraUpdater(OrthographicCamera camera){
        this.camera = camera;
    }
//    @Override
//    public void update(float deltaTime) {
//        NUR consciousNighterToFollow = NighterPool.consciousNighters.get(Undead.Lorale);
//        Position position = Mappers.POSITION.get(consciousNighterToFollow);
//        camera.position.set(position.getX(),position.getY(),0);
//        camera.update();
//    }
}

