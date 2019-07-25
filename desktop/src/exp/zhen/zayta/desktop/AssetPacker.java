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
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        //for conquest
//        settings.debug = DRAW_DEBUG_OUTLINE;
//        settings.scale = new  float[]{0.5f};
//        TexturePacker.process(settings,
//                RAW_ASSETS_PATH + "/gameplay/conquest",
//                ASSETS_PATH + "/gameplay/conquest",
//                "conquest"
//        );

//        //for personality_engineering_lab
        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/gameplay/personality_engineering_lab",
                ASSETS_PATH + "/gameplay/personality_engineering_lab",
                "personality_engineering_lab"
        );

        //for ui
//        TexturePacker.process(settings,
//                RAW_ASSETS_PATH +"/ui_skin/neon_skin_raw",
//                ASSETS_PATH + "/ui/neon_skin",
//                "neon-ui"
//        );

//        //misc
//        TexturePacker.process(settings,
//                RAW_ASSETS_PATH + "/gameplay/personality_engineering_lab/generated_map_tiles",
//                ASSETS_PATH + "/gameplay/personality_engineering_lab/generated_map_tiles",
//                "generated_map_tiles"
//        );


    }
}
