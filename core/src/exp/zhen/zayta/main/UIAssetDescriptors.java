package exp.zhen.zayta.main;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class UIAssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>("ui/fonts/ui_font_32.fnt", BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> WAKE_PLAY =
            new AssetDescriptor<TextureAtlas>("gameplay/wakeplay/wakeplay.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> CONQUEST =
            new AssetDescriptor<TextureAtlas>("gameplay/conquest/conquest.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MENU_CLIP =
            new AssetDescriptor<TextureAtlas>("menu/clips.atlas", TextureAtlas.class);



    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>("ui/uiskin.json", Skin.class);

    public static final AssetDescriptor<Sound> HIT_SOUND =
            new AssetDescriptor<Sound>("sounds/hit.wav", Sound.class);

    private UIAssetDescriptors() {
    }
}
