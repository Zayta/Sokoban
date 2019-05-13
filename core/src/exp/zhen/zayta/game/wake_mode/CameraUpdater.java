package exp.zhen.zayta.game.wake_mode;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.wake_mode.movement.component.Position;
import exp.zhen.zayta.game.wake_mode.entity.undead.Undead;
import exp.zhen.zayta.game.wake_mode.entity.undead.nur.NUR;
import exp.zhen.zayta.game.wake_mode.entity.undead.nur.Nighter;

public class CameraUpdater extends EntitySystem {
    OrthographicCamera camera;
    public CameraUpdater(OrthographicCamera camera){
        this.camera = camera;
    }
    @Override
    public void update(float deltaTime) {
        Nighter nighterToFollow = NUR.nighters.get(Undead.Lorale);
        Position position = Mappers.POSITION.get(nighterToFollow);
        camera.position.set(position.getX(),position.getY(),0);
        camera.update();
    }
}

