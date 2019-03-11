package exp.zhen.zayta.game.quest.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.quest.component.properties.movement.Position;
import exp.zhen.zayta.game.quest.entity.undead.Undead;
import exp.zhen.zayta.game.quest.entity.undead.nur.NUR;
import exp.zhen.zayta.game.quest.entity.undead.nur.Nighter;

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

