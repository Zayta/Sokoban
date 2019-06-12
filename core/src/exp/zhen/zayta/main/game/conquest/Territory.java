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
import exp.zhen.zayta.main.game.conquest.soldiers.utsubyo.Utsubyo;
import exp.zhen.zayta.main.game.conquest.tiles.Tile;

public class Territory extends Stage {

    private static final Logger log = new Logger(Territory.class.getName(), Logger.DEBUG);

    enum Terrain{
        LAB
    }
    private TextureAtlas conquestAtlas;
    //background
    private final Image background; private TextureRegionDrawable backgroundRegion;
    //dimensions
   private float tileWidth,tileHeight, padding;
   private int numRows,numColumns;

    public Territory (Viewport viewport, Batch batch, TextureAtlas conquestAtlas){
        super(viewport,batch);
        this.conquestAtlas = conquestAtlas;

        backgroundRegion = new TextureRegionDrawable();
        background = new Image(backgroundRegion);
        this.addActor(background);

    }

    public void create(Terrain terrain, int numNighters, int numMisc){
        setTerrain(terrain);
//        selectNighters();
        numRows = numNighters;numColumns = numMisc/numRows;//todo might have to ceiling this integer division
        padding = 0.1f;
        tileWidth = (getWidth()-padding -(numColumns+1)*padding)/(numColumns+1);//+1 to account for nighter row
        tileHeight = (getHeight()-numRows*padding)/numRows;
//        menuButton();
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



    public Tile [][] initMPos(Tile [] nPos){
        Tile [] [] mPos = new Tile[numRows][numColumns];
        for(int i = 0; i< numRows; i++){
            for(int j = 0; j<numColumns; j++){
                float left = padding + (j+1)*(tileWidth+padding), bottom = i*(tileHeight+padding)+padding/2;
                Tile mpos = new Tile(conquestAtlas.findRegion("backgrounds/sunrise_parallax"),Utsubyo.generateMonster(j));
                mpos.addListener(new Battle(nPos,mPos,i));

                mPos[i][j] = mpos;
                setTilePlacementInTerritory(mpos,left,bottom,tileWidth,tileHeight);
            }
        }
        return mPos;
    }

    public Tile [] initNPos(NUR nur){
        Tile [] nPos = new Tile[numRows];
        for(int i = 0; i<numRows;i++){
            float left = padding, bottom = i*(tileHeight+padding)+padding/2;
            Tile npos = new Tile(conquestAtlas.findRegion("backgrounds/day sky"),nur.summon(Undead.Lorale));
            nPos[i]= npos;

            setTilePlacementInTerritory(npos,left,bottom,tileWidth,tileHeight);
        }
        return nPos;

    }


    private void setTilePlacementInTerritory(Tile tile, float left, float bottom, float tileWidth, float tileHeight){
        tile.setBounds(left, bottom, tileWidth,tileHeight);
        tile.setOrigin(Align.center);
        this.addActor(tile);
    }








    /**Setters and Getters**/

    public TextureAtlas getConquestAtlas() {
        return conquestAtlas;
    }

    public void setConquestAtlas(TextureAtlas conquestAtlas) {
        this.conquestAtlas = conquestAtlas;
    }

    public Image getBackground() {
        return background;
    }

    public TextureRegionDrawable getBackgroundRegion() {
        return backgroundRegion;
    }

    public void setBackgroundRegion(TextureRegionDrawable backgroundRegion) {
        this.backgroundRegion = backgroundRegion;
    }

    public float getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(float tileWidth) {
        this.tileWidth = tileWidth;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(float tileHeight) {
        this.tileHeight = tileHeight;
    }

    public float getPadding() {
        return padding;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }
}




