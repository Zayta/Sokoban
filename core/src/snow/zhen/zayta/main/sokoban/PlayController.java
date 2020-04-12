package snow.zhen.zayta.main.sokoban;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;


import java.util.ArrayList;
import java.util.Arrays;


import snow.zhen.zayta.main.sokoban.entity.templates.EntityTemplate;
import snow.zhen.zayta.main.sokoban.entity.EntityType;
import snow.zhen.zayta.main.sokoban.entity.units.Crate;
import snow.zhen.zayta.main.sokoban.entity.units.Nighter;
import snow.zhen.zayta.main.sokoban.map.Map;
import snow.zhen.zayta.main.sokoban.movement.PositionTracker;
import snow.zhen.zayta.main.sokoban.movement.Direction;

public class PlayController implements Updateable {

    //in-gameplay
    private Map map;
    private PositionTracker positionTracker;
    private MovesPool movesPool;

    //for lvl
    private int curLvl;
    private int placedCrates=0;
    private Array<Move> moveHistory;
//    private boolean isComplete = false;


    public PlayController(Map map){
        this.map = map;
        positionTracker = new PositionTracker(map.getMapWidth());
        moveHistory = new Array<Move>();
        movesPool = new MovesPool();
    }
    public void initLvl(int curLvl){
        moveHistory.clear();
        this.curLvl = curLvl;
        map.init(curLvl);//need to init map before getting map width
        positionTracker.init(Math.max(map.getMapWidth(),map.getMapHeight()));
        positionTracker.updateGlobalTracker(map.getEntities());

        //see how many crates area alrdy at goal initially
        placedCrates=0;
        for(Crate crate: map.getCrates()){
            if(crate.getState()== Crate.State.IN_GOAL)
                placedCrates++;
        }
    }

    @Override
    public void update(float delta){

        positionTracker.updateGlobalTracker(map.getEntities());
//        positionTracker.updateCharacterTracker();
//        positionTracker.updateCrateTracker();
        for(Nighter n: map.getNighters())
            n.update(delta);
        for(Crate c: map.getCrates())
            c.update(delta);


    }


    public void moveNighters(Direction direction){
        Move move = movesPool.obtain();//creates the move
        move.setDirection(direction);
        for(Nighter nighter: map.getNighters()){
            if(direction==Direction.none)
                continue;
            //check for collision
            EntityTemplate collidedEntity = getCollidedEntity(nighter, direction);
            //check if nighter can move
            if(canPush(collidedEntity,direction)){
                move.addNighter(nighter);
            }
            //check if crate can move
            if(collidedEntity instanceof Crate){//if collided entity is crate
                if(canCrateMove(getCollidedEntity(collidedEntity,direction))) {//if that crate can move
                    move.addCrate((Crate) collidedEntity);
//                    System.out.println("Adding crate to move");
                }
            }
        }
        if(move.apply()) {//performs the move
            moveHistory.add(move);//add move to history
        }

    }

    /*Collision detection*/
    private boolean canPush(EntityTemplate entity, Direction direction){
        if(entity==null)
            return true;
        if(entity.is(EntityType.CRATE)){
            return canCrateMove(getCollidedEntity(entity,direction));
        }
        return entity.is(EntityType.GOAL);
    }
    private boolean canCrateMove(EntityTemplate collidedEntity){
        return collidedEntity==null||collidedEntity.is(EntityType.GOAL);
    }

    //returns the Entity that the moveableEntity will collide with, if it moves in the specified direction
    private EntityTemplate getCollidedEntity(EntityTemplate moveableEntity, Direction direction){
        float x = moveableEntity.getX()+direction.directionX,
                y = moveableEntity.getY()+direction.directionY;
        EntityTemplate entityTemplate = positionTracker.getEntityAtPos(x,y);
        return entityTemplate;
    }


    /*Moves*/
    private class MovesPool extends Pool<Move>{

        @Override
        protected Move newObject() {
            return new Move();
        }
    }
    private class Move implements Pool.Poolable {
        private ArrayList<Nighter> nighters;
        private ArrayList<Crate> crates;
        private Direction direction;
        Move(){
            this.direction = direction.none;
            nighters = new ArrayList<Nighter>();
            crates = new ArrayList<Crate>();
        }
        void opposite(Move move){
            this.direction = Direction.getOpposite(move.direction);
            this.nighters = move.nighters;
            this.crates = move.crates;
        }
        //moves all entities in the direction. returns true if move changed entity positions
        public boolean apply(){

            for(Crate c: crates) {
                moveCrate(c,direction);
            }
            for(Nighter n: nighters) {
                moveNighter(n,direction);
            }
            return direction!=Direction.none&&(!nighters.isEmpty()||!crates.isEmpty());
        }

        private void moveCrate(Crate crate, Direction direction){

            EntityTemplate crateCollider = getCollidedEntity(crate,direction);
//            System.out.println("Crate collider is "+crateCollider);
            //if crate was in goal but is now moved out of goal
            if(crate.getState()== Crate.State.IN_GOAL){//must be before crate setting newstate
                placedCrates--;
            }
            if(crateCollider==null) //if crate no collide, move crate, then move nighter
            {
                crate.move(direction);
                crate.setState(Crate.State.NORMAL);

            }
            else {

                if (positionTracker.isGoalPos(crateCollider.getX(), crateCollider.getY())) {
                    crate.move(direction);
                    crate.setState(Crate.State.IN_GOAL);
                    System.out.println("DEBUG: Crates placed: " + placedCrates + " out of " + map.getNumGoals());
                    placedCrates++;
                }
                else if(crateCollider.is(EntityType.CHARACTER)){
                    crate.move(direction);
                    crate.setState(Crate.State.NORMAL);
                }
            }
        }

        private void moveNighter(Nighter nighter, Direction direction){
            nighter.move(direction);
        }


        public void setDirection(Direction direction){
            this.direction = direction;
        }

        public void addNighter(Nighter nighter){
            nighters.add(nighter);
        }
        public void addCrate(Crate crate){
            crates.add(crate);
        }
        @Override
        public void reset() {
            direction = Direction.none;
            crates.clear();
            nighters.clear();
        }
        @Override
        public String toString(){
            return "Move: ("+direction+", Nighters: "+ Arrays.toString(nighters.toArray())+", Crates: "+Arrays.toString(crates.toArray());
        }
    }


    public void undoMove(){
        if(moveHistory.isEmpty()) {
            System.out.println("No move history");
            return;
        }
        Move undoMove = moveHistory.pop();

        Move move = movesPool.obtain();
        move.opposite(undoMove);
        move.apply();
        movesPool.free(undoMove);


    }
    public void restart(){
        initLvl(curLvl);
    }
    

    /*gameplay info*/
    public boolean isComplete(){
        boolean complete = placedCrates>=map.getNumGoals();

        return complete;
    }

    public void hide(){
        movesPool.freeAll(moveHistory);
    }


    //==Debug==//
    public void debug(){
        System.out.println("DEBUG: numEntities = "+map.getEntities().size());
        for(EntityTemplate entityTemplate : map.getEntities()){
            System.out.println("Entity "+ entityTemplate +" at pos "+ entityTemplate.getPosition()+" and has positionTracker key "+positionTracker.getKeyForEntity(entityTemplate));
        }

        for(Nighter nighter: map.getNighters()) {

            //check if nighter can move
            System.out.println("Debug Nigher move: ");
            for(Direction direction: Direction.values()) {

                //check for collision
                EntityTemplate collidedEntity = getCollidedEntity(nighter, direction);
                if (canPush(collidedEntity, direction)) {
                    System.out.println("Nighter can move " + direction);
                } else {
                    System.out.println("Nighter CANNOT move " + direction+" bc collidedEntity is "+collidedEntity);
                }
            }
        }
    }
}
