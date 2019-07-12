package exp.zhen.zayta.main;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class UIAssetDescriptors {

    public static final AssetDescriptor<TextureAtlas> WAKE_PLAY =
            new AssetDescriptor<TextureAtlas>("gameplay/wakeplay/wakeplay.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MAP_GENERATOR =
            new AssetDescriptor<TextureAtlas>("gameplay/wakeplay/generated_map_tiles/generated_map_tiles.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> CONQUEST =
            new AssetDescriptor<TextureAtlas>("gameplay/conquest/conquest.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MENU_CLIP =
            new AssetDescriptor<TextureAtlas>("menu/clips.atlas", TextureAtlas.class);


/*Holo*/

        public static final AssetDescriptor<BitmapFont> HOLO_FONT =
                new AssetDescriptor<BitmapFont>("ui/holo_skin/skin/default.fnt", BitmapFont.class);

        public static final AssetDescriptor<Skin> HOLO_SKIN =
                new AssetDescriptor<Skin>("ui/holo_skin/skin/uiskin.json", Skin.class);


        /*Neon*/
    public static final AssetDescriptor<BitmapFont> NEON_FONT =
            new AssetDescriptor<BitmapFont>("ui/neon_skin/fonts/font-export.fnt", BitmapFont.class);

    public static final AssetDescriptor<BitmapFont> NEON_FONT_OVER =
            new AssetDescriptor<BitmapFont>("ui/neon_skin/fonts/font-over-export.fnt", BitmapFont.class);
//
    public static final AssetDescriptor<BitmapFont> NEON_FONT_PRESSED =
            new AssetDescriptor<BitmapFont>("ui/neon_skin/fonts/font-pressed-export.fnt", BitmapFont.class);

    public static final AssetDescriptor<Skin> NEON_SKIN =
            new AssetDescriptor<Skin>("ui/neon_skin/neon-ui.json", Skin.class);


/**Basic Yellow**/
    public static final AssetDescriptor<BitmapFont> BASIC_YELLOW_FONT =
            new AssetDescriptor<BitmapFont>("ui/yellow_basic_skin/fonts/ui_font_32.fnt", BitmapFont.class);

    public static final AssetDescriptor<Skin> BASIC_YELLOW_SKIN =
            new AssetDescriptor<Skin>("ui/yellow_basic_skin/uiskin.json", Skin.class);

/*Neutralizer*/
public static final AssetDescriptor<BitmapFont> NEUTRALIZER_FONT =
        new AssetDescriptor<BitmapFont>("ui/neutralizer_skin/skin/fonts/font-export.fnt", BitmapFont.class);

    public static final AssetDescriptor<Skin> NEUTRALIZER_SKIN =
            new AssetDescriptor<Skin>("ui/neutralizer_skin/skin/neutralizer-ui.json", Skin.class);

    /**Pixthulhu**/
    public static final AssetDescriptor<BitmapFont> PIXTHULHU_FONT =
            new AssetDescriptor<BitmapFont>("ui/pixthulhu_skin/skin/fonts/font-export.fnt", BitmapFont.class);

    public static final AssetDescriptor<Skin> PIXTHULHU_SKIN =
            new AssetDescriptor<Skin>("ui/pixthulhu_skin/skin/pixthulhu-ui.json", Skin.class);



    public static final AssetDescriptor<BitmapFont> FONT = PIXTHULHU_FONT;
    public static final AssetDescriptor<Skin> UI_SKIN = NEON_SKIN;


}
