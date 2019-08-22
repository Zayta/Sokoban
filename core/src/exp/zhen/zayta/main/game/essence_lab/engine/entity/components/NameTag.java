package exp.zhen.zayta.main.game.essence_lab.engine.entity.components;

import com.badlogic.ashley.core.Component;

public class NameTag implements Component {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
