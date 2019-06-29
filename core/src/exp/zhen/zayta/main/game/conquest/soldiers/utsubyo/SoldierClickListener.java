package exp.zhen.zayta.main.game.conquest.soldiers.utsubyo;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import exp.zhen.zayta.main.game.conquest.soldiers.Soldier;

public class SoldierClickListener extends ClickListener {
    private Soldier soldier;

    public SoldierClickListener(Soldier soldier){
        this.soldier = soldier;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {

    }
}
