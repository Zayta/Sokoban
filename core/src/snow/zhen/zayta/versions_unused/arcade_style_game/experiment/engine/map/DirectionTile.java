package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;

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
