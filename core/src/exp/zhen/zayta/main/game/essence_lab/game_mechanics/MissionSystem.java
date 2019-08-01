package exp.zhen.zayta.main.game.essence_lab.game_mechanics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import exp.zhen.zayta.RPG;

@Deprecated
public class MissionSystem extends GameControllingSystem{

    private Entity quest; private int numMissions = 0;


    public MissionSystem(RPG game, PooledEngine engine) {
        super(game, engine);
        quest = engine.createEntity();

        engine.addEntity(quest);

        addMission();
    }
    public void addMission(){
        switch (RPG.userData.getNumScenesUnlocked()){
            case 0:
                break;
            default:
//                StonesMission stonesMission = getEngine().createComponent(StonesMission.class);
//                quest.add(stonesMission);
                numMissions++;
                break;
        }
    }

//    @Override
//    public void update(float deltaTime) {
//        for(int i = missionComponents.size()-1; i>=0; i--){
//            MissionComponent missionComponent = Mappers.MISSION.get(quest);
//            if(missionComponents.get(i).isComplete()){
//                missionComponents.remove(i);
//            }
//        }
//
//        if(numMissions == 0){
//            setNextLevel();
//        }
//    }

    @Override
    public void reset() {

    }
}
