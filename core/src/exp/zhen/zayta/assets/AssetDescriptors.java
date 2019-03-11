package exp.zhen.zayta.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MENU_CLIP =
            new AssetDescriptor<TextureAtlas>(AssetPaths.MENU_CLIP, TextureAtlas.class);

    public static final AssetDescriptor<TiledMap> MAP_MEMLAB1 = new AssetDescriptor<TiledMap>(AssetPaths.MAPS_MEMLAB1,TiledMap.class);
    public static final AssetDescriptor<TiledMap> MAP_MEMLAB2 = new AssetDescriptor<TiledMap>(AssetPaths.MAPS_MEMLAB2,TiledMap.class);
    public static final AssetDescriptor<TiledMap> MAP_MEMLABBIG = new AssetDescriptor<TiledMap>(AssetPaths.MAPS_MEMLABBIG,TiledMap.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);

    public static final AssetDescriptor<Sound> HIT_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.HIT, Sound.class);

    private AssetDescriptors() {
    }
}
