package exp.zhen.zayta.main.game.wake.map.tiled_map.map_generator;

public enum MapType {
    NONE(""),
    CITY("roguelikeCity_tile_set"),
    PATH("generated_map_tiles_compact");

    private final String tileSetName;
    MapType(String tileSetName){
        this.tileSetName = tileSetName;
    }
    public String getTileSetName() {
        return tileSetName;
    }
}