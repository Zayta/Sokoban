package exp.zhen.zayta.versions_unused.conquest;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.versions_unused.conquest.soldiers.Soldier;
import exp.zhen.zayta.versions_unused.conquest.tiles.Tile;

/*Handles game logic and input*/
public class Battle extends ClickListener {
    private static final Logger log = new Logger(Battle.class.getName(), Logger.DEBUG);

    private Tile[] nPos; private Tile[][] mPos;
    private int rowOfBattle;
    public Battle(Tile[] nPos, Tile[][] mPos, int i){
        this.nPos = nPos;
        this.mPos = mPos;
        rowOfBattle = i;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        fight();
    }

    private void fight(){
        System.out.println("Battle happening at row "+rowOfBattle);
//        System.out.println("nPos"+nPos);
        Soldier nighter = nPos[rowOfBattle].getSoldier();
        Soldier monster0 = mPos[rowOfBattle][0].getSoldier();
        if(monster0!=null && nighter!=null){
            nighter.attack(monster0);
            if(monster0.isKnockedOut()){
//                ////log.debug("monster0.isKnockedOut is true");
//                mPos[rowOfBattle][0].removeSoldier();
                shiftLeft(0,mPos);
            }
        }
//        ////log.debug("Monster fought is "+monster0);
    }

    //shifts soldiers left until the first soldier is at the startIndex

    private void shiftLeft(int startIndex, Tile[][] tiles){
//        ////log.debug("called shiftLeft");
        if(startIndex<0||startIndex>=tiles.length) {
            System.out.println("Array Index out of bounds");
            return;
        }

        for(int i = startIndex; i<tiles[rowOfBattle].length-1;i++){
            tiles[rowOfBattle][i].setSoldier(tiles[rowOfBattle][i+1].getSoldier());
            tiles[rowOfBattle][i+1].removeSoldier();
        }

//        for(Tile t: morgue_tiles[rowOfBattle]){
//            ////log.debug("Tile "+t+" has soldier "+t.getSoldier());
//        }

    }
//    private void shiftLeft(int startIndex, Tile[][] morgue_tiles){
//        ////log.debug("called shiftLeft");
//        if(startIndex<0||startIndex>=morgue_tiles.length) {
//            System.out.println("Array Index out of bounds");
//            return;
//        }
//
//        for(int i = startIndex; i<morgue_tiles[rowOfBattle].length-1;i++){
//            morgue_tiles[rowOfBattle][i].setSoldier(morgue_tiles[rowOfBattle][i+1].getSoldier());
//        }
//        morgue_tiles[rowOfBattle][morgue_tiles.length-1] = null;
//    }



    public Tile[][] getMPos() {
        return mPos;
    }

    public void setMPos(Tile[][] mPos) {
        this.mPos = mPos;
    }

    public int getRowOfBattle() {
        return rowOfBattle;
    }

    public void setRowOfBattle(int rowOfBattle) {
        this.rowOfBattle = rowOfBattle;
    }

    public Tile[] getNPos() {
        return nPos;
    }

    public void setNPos(Tile[] nPos) {
        this.nPos = nPos;
    }
}
