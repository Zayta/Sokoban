package exp.zhen.zayta.main.game.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.conquest.soldiers.nur.NUR;
import exp.zhen.zayta.main.game.conquest.soldiers.utsubyo.Utsubyo;
import exp.zhen.zayta.main.game.conquest.tiles.Tile;

public class Territory extends Stage {

    private static final Logger log = new Logger(Territory.class.getName(), Logger.DEBUG);

    enum Terrain{
        LAB
    }
    private TextureAtlas conquestAtlas; private BitmapFont font;
    //background
    private final Image background; private TextureRegionDrawable backgroundRegion;
    //fields
    private Tile[] nPos;
    private Tile[][] mPos;
    private int numNighters = 3, numMisc = 6;

    public Territory (Viewport viewport, Batch batch, TextureAtlas conquestAtlas){
        super(viewport,batch);
        this.conquestAtlas = conquestAtlas;

        backgroundRegion = new TextureRegionDrawable();
        background = new Image(backgroundRegion);
        this.addActor(background);

        create(Terrain.LAB,numNighters,numMisc);
    }

    private void create(Terrain terrain, int numNighters,int numMisc){
        setFont();
        setTerrain(terrain);
        selectNighters();
        if(numMisc<numNighters){
            log.debug("number of Nighters cannot be greater than numMisc");
        }
        else {
            setPositions(numNighters, numMisc);
        }
    }
    private void setFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fonts/font_amble/Amble-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        font.getData().setScale(.2f);
    }
    private void selectNighters(){
        //todo make table, add to this stage/territory

        //based on user input, create list to store nighters chosen


    }
    private void setTerrain(Terrain terrain){
        switch (terrain){
            case LAB:
                backgroundRegion.setRegion(conquestAtlas.findRegion("backgrounds/fullscanner"));
                break;
            default:
                backgroundRegion.setRegion(conquestAtlas.findRegion("backgrounds/fullscanner"));
                break;
        }
        background.setDrawable(backgroundRegion);
    }

    private void setPositions(int numNighters,int numMisc){
        int numRows = numNighters,numColumns = numMisc/numRows;//todo might have to ceiling this integer division
        float padding = 0.1f;
        float tileWidth = (getWidth()-padding -(numColumns+1)*padding)/(numColumns+1);//+1 to account for nighter row
        float tileHeight = (getHeight()-numRows*padding)/numRows;

        //important! initNPos before initCPos because CPos requires Tile for battle listener
        initNPos(numNighters,tileWidth,tileHeight,padding);
        initCPos(numNighters,numMisc/numNighters,tileWidth,tileHeight,padding);

    }


    private void initCPos(int numRows, int numColumns,float tileWidth, float tileHeight, float padding){
        mPos = new Tile[numRows][numColumns];
        for(int i = 0; i< numRows; i++){
            for(int j = 0; j<numColumns; j++){
                float left = padding + (j+1)*(tileWidth+padding), bottom = i*(tileHeight+padding)+padding/2;
                Tile mpos = new Tile(conquestAtlas.findRegion("backgrounds/sunrise_parallax"),font,Utsubyo.generateMonster(5));
                mpos.addListener(new Battle(nPos,mPos,i));

                mPos[i][j] = mpos;
                setTilePlacementInTerritory(mpos,left,bottom,tileWidth,tileHeight);
            }
        }

    }

    private void initNPos(int numNighters, float tileWidth, float tileHeight, float padding){
        nPos = new Tile[numNighters];
        for(int i = 0; i<numNighters;i++){
            float left = padding, bottom = i*(tileHeight+padding)+padding/2;
            Tile npos = new Tile(conquestAtlas.findRegion("backgrounds/day sky"),font,NUR.nighters.get(Undead.Lorale));
            nPos[i]= npos;
            setTilePlacementInTerritory(npos,left,bottom,tileWidth,tileHeight);
        }

    }


    private void setTilePlacementInTerritory(Tile tile, float left, float bottom, float tileWidth, float tileHeight){
        tile.setBounds(left, bottom, tileWidth,tileHeight);
        tile.setOrigin(Align.center);
        this.addActor(tile);
    }

 

}




