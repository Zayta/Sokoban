package exp.zhen.zayta.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by goran on 31/08/2016.
 */
public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = false;

    private static final String RAW_ASSETS_PATH = "desktop/src/raw_assets";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.debug = DRAW_DEBUG_OUTLINE;
//        settings.scale = new  float[]{0.5f};

        //for conquest
//        TexturePacker.process(settings,
//                RAW_ASSETS_PATH + "/gameplay/conquest",
//                ASSETS_PATH + "/gameplay/conquest",
//                "conquest"
//        );

//        //for wakeplay
//        TexturePacker.process(settings,
//                RAW_ASSETS_PATH + "/gameplay/wakeplay",
//                ASSETS_PATH + "/gameplay/wakeplay",
//                "wakeplay"
//        );

        //for ui
        TexturePacker.process(settings,
                RAW_ASSETS_PATH +"/ui_skin/neon_skin_raw",
                ASSETS_PATH + "/ui/neon_skin",
                "neon-ui"
        );


    }
}
