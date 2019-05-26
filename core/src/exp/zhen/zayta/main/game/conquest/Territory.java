package exp.zhen.zayta.main.game.conquest;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.conquest.soldiers.nur.NUR;
import exp.zhen.zayta.main.game.conquest.tiles.EPos;
import exp.zhen.zayta.main.game.conquest.tiles.NPos;
import exp.zhen.zayta.main.game.conquest.tiles.Tile;


public class Territory extends Stage {

    private static final Logger log = new Logger(Territory.class.getName(), Logger.DEBUG);

    enum Terrain{
        LAB
    }
    private TextureAtlas conquestAtlas;
    //background
    private final Image background; private TextureRegionDrawable backgroundRegion;
    //field
    private NPos[] nPos;
    private EPos[][] ePos;
    
    public Territory (Viewport viewport, Batch batch, TextureAtlas conquestAtlas){
        super(viewport,batch);
        this.conquestAtlas = conquestAtlas;

        backgroundRegion = new TextureRegionDrawable();
        background = new Image(backgroundRegion);
        this.addActor(background);
        
        create(Terrain.LAB,3,12);
    }

    private void create(Terrain terrain, int numNighters,int numMisc){
        setTerrain(terrain);
        selectNighters();
        if(numMisc<numNighters){
            log.debug("number of Nighters cannot be greater than numMisc");
        }
        else {
            setPositions(numNighters, numMisc);
        }
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

        System.out.println("NumMisc"+numMisc);
        float padding = 0.1f;
        float tileWidth = (getWidth()-padding -(numColumns+1)*padding)/(numColumns+1);//+1 to account for nighter row
        float tileHeight = (getHeight()-numRows*padding)/numRows;
        initCPos(numNighters,numMisc/numNighters,tileWidth,tileHeight,padding);
        initNPos(numNighters,tileWidth,tileHeight,padding);
    }
    
    private void initCPos(int numRows, int numColumns,float tileWidth, float tileHeight, float padding){
        ePos = new EPos[numRows][numColumns];
        for(int i = 0; i< numRows; i++){
            for(int j = 0; j<numColumns; j++){
                float left = padding + (j+1)*(tileWidth+padding), bottom = i*(tileHeight+padding)+padding/2;
                EPos epos = new EPos(conquestAtlas.findRegion("backgrounds/sunrise_parallax"));
                ePos[i][j] = epos;
                setTilePlacementInTerritory(epos,left,bottom,tileWidth,tileHeight);
            }
        }

    }
    
    private void initNPos(int numNighters, float tileWidth, float tileHeight, float padding){
        nPos = new NPos[numNighters];
        for(int i = 0; i<numNighters;i++){
            float left = padding, bottom = i*(tileHeight+padding)+padding/2;
            NPos npos = new NPos(conquestAtlas.findRegion("backgrounds/day sky"),NUR.nighters.get(Undead.Lorale));
            setTilePlacementInTerritory(npos,left,bottom,tileWidth,tileHeight);
        }
    }

    private void setTilePlacementInTerritory(Tile tile, float left, float bottom, float tileWidth, float tileHeight){
        tile.setBounds(left, bottom, tileWidth,tileHeight);
        tile.setOrigin(Align.center);
        this.addActor(tile);
    }
    
    



//    private Table positionTable(int numRows, int numColumns, int id){
//        float padding = 0.1f;
//        float width = 1f,height = 1f;
//        Table table = new Table();
//
//        for(int i = 0; i< numRows; i++){
//            for(int j = 0; j<numColumns; j++){
//                Tile pos = new Tile (conquestAtlas.findRegion("portraits/Lorale"),id);
//                pos.setBounds(0f,0f,width,height);
//                table.add(pos).pad(padding).uniform();
//            }
//            table.row();
//
//        }
//
//        return table;
//    }

}




