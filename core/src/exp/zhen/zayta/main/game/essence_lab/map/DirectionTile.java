package exp.zhen.zayta.main.game.essence_lab.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
@Deprecated
//experimental class
public class DirectionTile extends StaticTiledMapTile {
    private Direction direction;
    public DirectionTile(TextureRegion textureRegion) {
        super(textureRegion);
        direction = Direction.generateRandomDirection();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
