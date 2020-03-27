package snow.zhen.zayta.versions_unused.conquest.soldiers.utsubyo;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import snow.zhen.zayta.versions_unused.conquest.soldiers.Soldier;

public class SoldierClickListener extends ClickListener {
    private snow.zhen.zayta.versions_unused.conquest.soldiers.Soldier soldier;

    public SoldierClickListener(Soldier soldier){
        this.soldier = soldier;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {

    }
}
