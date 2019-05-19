package exp.zhen.zayta.main.game.wake;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.wake.entity.player.nur.NUR_Awake_List;
import exp.zhen.zayta.main.game.wake.entity.player.nur.ConsciousNighter;

public class CameraUpdater extends EntitySystem {
    OrthographicCamera camera;
    public CameraUpdater(OrthographicCamera camera){
        this.camera = camera;
    }
    @Override
    public void update(float deltaTime) {
        ConsciousNighter consciousNighterToFollow = NUR_Awake_List.consciousNighters.get(Undead.Lorale);
        Position position = Mappers.POSITION.get(consciousNighterToFollow);
        camera.position.set(position.getX(),position.getY(),0);
        camera.update();
    }
}

