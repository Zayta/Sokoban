package exp.zhen.zayta.main.game.conquest;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.conquest.tiles.Tile;

/*Handles game logic and input*/
public class BattleListener extends ClickListener {
    private static final Logger log = new Logger(BattleListener.class.getName(), Logger.DEBUG);

    private Tile[] nPos; private Tile[][] mPos;
    private int rowOfBattle;
    public BattleListener(Tile[] nPos, Tile[][] mPos, int i){
        this.nPos = nPos;
        this.mPos = mPos;
        rowOfBattle = i;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        fight();
    }

    private void fight(){
        System.out.println("BattleListener happening at row "+rowOfBattle);
        System.out.println("nPos"+nPos);
        nPos[rowOfBattle].getSoldier().attack(mPos[rowOfBattle][0].getSoldier());
    }

    //shifts soldiers left until the first soldier is at the startIndex
    private void shiftLeft(int startIndex, Tile[] tiles){
        if(startIndex<=0||startIndex>=tiles.length) {
            System.out.println("Array Index out of bounds");
            return;
        }

        for(int i = tiles.length-1; i>startIndex; i--){
            tiles[i].setSoldier(tiles[i-1].getSoldier());
        }
    }



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
