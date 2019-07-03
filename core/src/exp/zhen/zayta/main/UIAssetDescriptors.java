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

    public static final AssetDescriptor<TextureAtlas> CONQUEST =
            new AssetDescriptor<TextureAtlas>("gameplay/conquest/conquest.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MENU_CLIP =
            new AssetDescriptor<TextureAtlas>("menu/clips.atlas", TextureAtlas.class);




    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>("ui/neon_skin/fonts/font-export.fnt", BitmapFont.class);

    public static final AssetDescriptor<BitmapFont> FONT_OVER =
            new AssetDescriptor<BitmapFont>("ui/neon_skin/fonts/font-over-export.fnt", BitmapFont.class);

    public static final AssetDescriptor<BitmapFont> FONT_PRESSED =
            new AssetDescriptor<BitmapFont>("ui/neon_skin/fonts/font-pressed-export.fnt", BitmapFont.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>("ui/neon_skin/neon-ui.json", Skin.class);



//    public static final AssetDescriptor<BitmapFont> FONT =
//            new AssetDescriptor<BitmapFont>("ui/yellow_basic_skin/fonts/ui_font_32.fnt", BitmapFont.class);
//
//    public static final AssetDescriptor<Skin> UI_SKIN =
//            new AssetDescriptor<Skin>("ui/yellow_basic_skin/uiskin.json", Skin.class);


//    public enum Theme{
//        Yellow_Basic,Neon
//    }
//    public static void setUITheme(Theme theme){
//        switch (theme){
//            case Yellow_Basic:
//                break;
//            case Neon:
//                break;
//        }
//
//    }

//    public static final AssetDescriptor<Sound> HIT_SOUND =
//            new AssetDescriptor<Sound>("sounds/hit.wav", Sound.class);


}
