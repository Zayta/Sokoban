package exp.zhen.zayta.main.sokoban_OOP;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import exp.zhen.zayta.main.sokoban_OOP.objects.GameObject;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.Crate;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.NullObject;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.Place;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.Sokoban;

public class Puzzle implements Disposable{

    private Map map;
    //this is how to access textureatlas regions
//    TextureAtlas sokobanAtlas = assetManager.get(AssetDescriptors.LAB);
//    TextureRegion backgroundRegion = sokobanAtlas.findRegion(RegionNames.SQUARE_FLOOR);

    public Puzzle(TextureAtlas sokobanAtlas){
        this.map = new Map(sokobanAtlas);
    }

    public int getWidth() {
        return map.getMapWidth();
    }

    public int getHeight() {
        return map.getMapHeight();
    }

    public Sokoban getPlayer() {
        return (Sokoban)map.getLayer(MapLayer.SOKOBAN).getObjects().get(0);
    }

    public void update(float delta) {
        for (MapLayer layer : map.getMapLayers()) {
            layer.updateObjects(delta);
        }
    }

    public void render(SpriteBatch batch, float scale) {
        for (MapLayer layer : map.getMapLayers()) {
            layer.renderObjects(batch, scale);
        }
    }

    @Override
    public void dispose() {
        disposeGameObjects();
    }

    private void disposeGameObjects() {
        for (MapLayer layer : map.getMapLayers()) {
            layer.disposeObjects();
        }
    }

    public void loadLevel(final int level) {
        System.out.println("Loading level: " + level);

        disposeGameObjects();
        map.create(level);
        invalidateCrates();
    }

    private void invalidateCrates() {
        MapLayer placeLayer = map.getLayer(MapLayer.PLACE);

        for (GameObject object : placeLayer.getObjects()) {

            Place place = (Place)object;
            Crate crateOnPlace = getObjectTypeAt(Crate.class, place.getPosition());
            if (crateOnPlace != null) {
                crateOnPlace.bindPlace(place);
            }

        }
    }

    public boolean areCratesOnPlaces() {
        MapLayer placeLayer = map.getLayer(MapLayer.PLACE);

        for (GameObject object : placeLayer.getObjects()) {
            Place place = (Place)object;
            if (!place.haveCrate()) {
                return false;
            }
        }
        return true;
    }

    public GameObject getObjectAt(Vector2 targetPosition) {
        for (MapLayer layer : map.getMapLayers()) {
            for (GameObject object : layer.getObjects()) {

                Vector2 position = object.getPosition();
                if (position.epsilonEquals(targetPosition, 1.0f)) {
                    return object;
                }

            }
        }

        return NullObject.instance;
    }

    public <T extends GameObject> T getObjectTypeAt(Class<T> type, Vector2 targetPosition) {
        for (MapLayer layer : map.getMapLayers()) {
            for (GameObject object : layer.getObjects()) {

                Vector2 position = object.getPosition();
                if (position.epsilonEquals(targetPosition, 1.0f) &&
                        type.isAssignableFrom(object.getClass())) {
                    return type.cast(object);
                }

            }
        }
        return null;
    }
}
