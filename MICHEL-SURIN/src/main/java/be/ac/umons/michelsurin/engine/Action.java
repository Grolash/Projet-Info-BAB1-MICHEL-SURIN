package be.ac.umons.michelsurin.engine;


import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.engine.Rules;
import be.ac.umons.michelsurin.gui.ConfirmBox;
import be.ac.umons.michelsurin.items.Pawn;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;
import be.ac.umons.michelsurin.world.Cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class Action implements Serializable {

    public static final long serialVersionUID = -2571765794130091542L;

    private static Random random = new Random();

    /**
     * Given a controller, it will, depending on its type (AI, etc)
     * call the correct method that will do an action.
     *
     *
     * @param playerArray
     * @param ctrl the controller
     * @throws IllegalArgumentException is thrown when a controller as an invalid type.
     */
    public static void getAction(PawnController[] playerArray, PawnController ctrl) throws IllegalArgumentException {
        switch (ctrl.getType()) {
            case "Debilus" :
            case "Random":
                debilusActionHandler(playerArray, ctrl);
                break;
            case "Smarted" :
            case "Easy":
                smartedActionHandler(playerArray, ctrl);
                break;
            case "Smart" :
            case "Hard":
                smartActionHandler(playerArray, ctrl);
                break;
            case "DebugDOWN":
                debugDOWNActionHandler(playerArray, ctrl);
                break;
            case "DebugUP":
                debugUPActionHandler(playerArray, ctrl);
                break;
            default:
                throw new IllegalArgumentException("the controller has an incorrect type : " + ctrl.getType());
        }
    }

    /**
     * a purely debugging AI. It must be initialized at the bottom of the board.
     * It will move forward even if there are walls
     * it will get trough it. Used to test other AI.
     *
     * @param playerArray -
     * @param ctrl -
     */
    private static void debugDOWNActionHandler(PawnController[] playerArray, PawnController ctrl) {
        if (Rules.canMove(ctrl, Game.directions.get("DOWN"))) {
            ctrl.move(Game.directions.get("DOWN"));
        } else {

        }

    }
    private static void debugUPActionHandler(PawnController[] playerArray, PawnController ctrl) {
        ctrl.move(Game.directions.get("DOWN"));
    }


    /**
     * Method used by "Smarted" and "Smart" AIs to move following a path given by the pathfinding tool.
     * It takes in account all the rules of quorridor.
     *
     *
     * @param ctrl the PawnController of the AI
     */
    private static void AIAdvancedMove(PawnController ctrl) {
        ArrayList<Coord> path = Rules.path(ctrl);
        Coord[] pathArray = path.toArray(new Coord[path.size()]);
        Coord[] reachableCell = whereCanIGo(ctrl);
        for (Coord coord : reachableCell) {
            if (coord.isIn(pathArray)) {
                ctrl.getDependency().setCoord(coord);
            }
        }
    }

    /**
     * Wall placement method of "Smart" AI.
     * It places walls in priority in the path of the most advanced player (which path is the smallest), beginning by
     * the most next move, and if not possible then following the path (because it's the best way of the target).
     * If can not place any wall on the path of the target, it places the wall which is the best possible to elongate
     * the path of the target.
     * @param playerArray
     * @param ctrl
     */
    private static void smartWallPlacement(PawnController[] playerArray,PawnController ctrl){
        if (ctrl.getNumbWall() > 0) {
            Coord placeCoord;
            Coord placeDir;

                int ordinate;
                int absciss;
                int intDir;

                PawnController target = playerArray[0];
                if (ctrl.equals(playerArray[0])){
                    target = playerArray[1];
                }

                for (PawnController pawnController : playerArray){ //controls the travelled distance of all the players
                    if (travel(pawnController) < travel(target) && !pawnController.equals(ctrl)){ //to see if self is the most advanced.
                        target = pawnController;
                    }
                }

                ArrayList<Coord> targetPath = Rules.path(target);
                Coord next = targetPath.remove(targetPath.size()-1);
                ordinate = next.getY();
                absciss = next.getX();

                int deltaY = next.getY() - target.getDependency().getCoord().getY();
                int deltaX = next.getX() - target.getDependency().getCoord().getX();

                switch (deltaY){
                    case 1:
                    case -1:
                        intDir = 3;
                        break;
                    case 0:
                        switch (deltaX){
                            case 1:
                            case -1:
                                intDir = 0;
                                break;
                            default:
                                throw new IllegalArgumentException("deltaX should be 1 or -1.");

                        }
                        break;

                    default:
                        throw new IllegalArgumentException("deltaY should be 1 or -1.");
                }


                placeCoord = new Coord(ordinate, absciss);
                placeDir = getDirection(intDir);


            if (Rules.canPlaceWall(playerArray, ctrl, placeCoord, placeDir)) {
                ctrl.placeWall(placeCoord, placeDir);
                //Tries to place a wall on the next best move of the most advanced player.
            } else {
                if(targetPath.size() > 0) {
                    do {
                        Coord prev = next;
                        Coord newNext = targetPath.remove(targetPath.size() - 1);

                        ordinate = newNext.getY();
                        absciss = newNext.getX();
                        deltaY = newNext.getY() - prev.getY();
                        deltaX = newNext.getX() - prev.getX();

                        switch (deltaY) {
                            case 1:
                            case -1:
                                intDir = 3;
                                break;
                            case 0:
                                switch (deltaX) {
                                    case 1:
                                    case -1:
                                        intDir = 0;
                                        break;
                                    default:
                                        throw new IllegalArgumentException("deltaX should be 1 or -1.");
                                }
                                break;

                            default:
                                throw new IllegalArgumentException("deltaY should be 1 or -1.");
                        }


                        placeCoord = new Coord(ordinate, absciss);
                        placeDir = getDirection(intDir);

                        prev = next;
                        next = newNext;

                    } while ( !Rules.canPlaceWall(playerArray, ctrl, placeCoord, placeDir) && targetPath.size() > 1);
                }

                if (Rules.canPlaceWall(playerArray, ctrl, placeCoord, placeDir)) {
                    ctrl.placeWall(placeCoord, placeDir);
                    //Tries to place a wall on the rest of the path (still the best move set).
                } else{
                    AIAdvancedMove(ctrl);
                }
            }
        }
    }



    /**
     * Determinist "Smart" AI based on pathfinding to be more efficient than any random resolution algorithm.
     *
     * Smart first determines which player is the closest to victory. If smart is not that player, it will try to move to
     * close the gap if the other player (the closest to victory) has not a path smaller than half the board size.
     * If the other player has a path smaller than half the board size, Smart abandons the "closing the gap" strategy
     * and places a wall instead.
     * If Smart is the closest player to victory, it will try place a wall to enlarge the gap with the others.
     * When Smart has no more walls, it moves following the best path (which is the result of the pathfinding), as always.
     *
     * @param playerArray an array of all the players in the game.
     * @param ctrl the controller it has been put in charge of.
     */
    private static void smartActionHandler(PawnController[] playerArray, PawnController ctrl) {
        int selfTravel = travel(ctrl);
        int maxTravel = travel(ctrl);
        PawnController furtherPlayer = ctrl;
        boolean further = true; //true if Smart is the most advanced player in the game at the moment (smaller path).
        for (PawnController pawnController : playerArray) { //controls the travelled distance of all the players
            if (travel(pawnController) < selfTravel) { //to see if Smart is the most advanced (the smallest value of travel)
                further = false;
            }
            if (travel(pawnController) < maxTravel) {
                furtherPlayer = pawnController;
            }
        }

        if (!further) {
            if (travel(furtherPlayer) < (ctrl.getBoard().getSize() / 2)) {
                if (ctrl.getNumbWall() > 0) {
                    smartWallPlacement(playerArray, ctrl);
                } else {
                    AIAdvancedMove(ctrl);
                }
            } else {
                AIAdvancedMove(ctrl);
            }

        }
        else {
            if (ctrl.getNumbWall() > 0) {
                smartWallPlacement(playerArray, ctrl);
            } else {
                AIAdvancedMove(ctrl);
            }
        }

    }

    /**
     * Gives the size of the path of a given player (via PawnController).
     * A smaller path means the player is closer to victory.
     * @param pawnController
     * @return the distance travelled by the pawn
     */
    private static int travel(PawnController pawnController){
        ArrayList<Coord> path = Rules.path(pawnController);
        int travel = path.size();
        return travel;
    }


    /**
     * Smarted AI:
     * A slightly less random AI, with a bit of strategy taken in consideration:
     * Smarted follows a path defined by pathfinding tool.
     * Also, we figured out placing a wall between one's start and one's current
     * position (included) leads to better situations, so Smarted place walls accordingly.
     * Nevertheless it should never place a wall between itself and the goal, it moves it instead one cell further right.
     * @param playerArray
     * @param ctrl
     * @throws IllegalArgumentException incorrect delta calculation between two coordinates.
     */
    private static void smartedActionHandler(PawnController[] playerArray, PawnController ctrl) throws IllegalArgumentException {

        if (ctrl.getSmartedActionChangelog() > 1) { //See above the method.
            ctrl.setSmartedActionChangelog(0); //Just a reinitialisation.
        }

        if (ctrl.getSmartedActionChangelog() == 0){
            AIAdvancedMove(ctrl);
        }
        else if (ctrl.getSmartedActionChangelog() == 1){ //Tries and place a wall
            if (ctrl.getNumbWall() > 0) {
                Coord placeCoord;
                Coord placeDir;
                int triesWalls = 0;
                do {
                    int ordinate;
                    if (ctrl.getDependency().getCoord().getY() == 0 ||
                            ctrl.getDependency().getCoord().getY() - 1 == 0) {
                        ordinate = random.nextInt(2);
                    }
                    else {
                        Pawn pawn = (Pawn) ctrl.getDependency();
                        if(pawn.getStart().getY() == 0) { //If start at top, place between O and self position
                            ordinate = random.nextInt(ctrl.getDependency().getCoord().getY() - 1);
                        }
                        else { //If start at bottom, place between board size and self position
                            do {
                                ordinate = random.nextInt(ctrl.getBoard().getSize() );
                                }
                            while(ordinate < ctrl.getDependency().getCoord().getY() );
                        }
                    }
                    if ((ordinate == 0) && (ctrl.getDependency().getCoord().getY() != 0)) {
                        ordinate = ctrl.getDependency().getCoord().getY();
                    }
                    else if ((ordinate == 0) && (ctrl.getDependency().getCoord().getY() == 0)){
                        ordinate = 2;
                    }
                    //Does not place a wall further than itself.
                    int absciss = random.nextInt(ctrl.getBoard().getSize() - 1);

                    int intDir = random.nextInt(2);
                    if (intDir == 1)
                        intDir = 3;

                    if ((absciss == ctrl.getDependency().getCoord().getX()) && (intDir == 3))
                        absciss += 1;
                    //If the wall is in the way of the goal, displace it.

                    placeCoord = new Coord(ordinate, absciss);
                    placeDir = getDirection(intDir);

                    triesWalls++;
                }
                while (!Rules.canPlaceWall(playerArray, ctrl, placeCoord, placeDir) && triesWalls <= 25);
                if (triesWalls <= 25) {
                    ctrl.placeWall(placeCoord, placeDir);
                } else {
                    ctrl.setSmartedActionChangelog(ctrl.getSmartedActionChangelog() + 1);
                    smartedActionHandler(playerArray, ctrl);
                }

            }
            else {
                ctrl.setSmartedActionChangelog(ctrl.getSmartedActionChangelog() + 1);
                smartedActionHandler(playerArray, ctrl);
            }

        }

        ctrl.setSmartedActionChangelog(ctrl.getSmartedActionChangelog() + 1);
    }



    /**
     * "Debilus" AI:
     * Most basic AI, entirely random due to it's purpose. Is totally intended to be unintelligent and inefficient.
     * Chooses all it's actions and their directions via a random number.
     * @param playerArray
     * @param ctrl
     */
    private static void debilusActionHandler(PawnController[] playerArray, PawnController ctrl) {

        int action = random.nextInt(2); //Choose randomly between moving and placing a wall.
        Coord currentCell = ctrl.getDependency().getCoord();

        if (action == 0){ //Tries and moves.
            Coord direction;
            int randint;

            do {
                randint = randomizeDirection();
                direction = getDirection(randint); //Choose a random direction
            }
            while (!(Rules.canMove(ctrl, direction))); //Does so until it can move.

            Coord forwardCell = Coord.add(ctrl.getDependency().getCoord(), direction);
            //Coordinates of the intended move cell

            if (ctrl.getBoard().getCell(forwardCell).hasPawn()) {
                //If there is a pawn in "front" of itself, tries to bypass it.

                if (!(Rules.canMove(ctrl, direction, forwardCell)) || ctrl.getBoard().getCell(Coord.add(forwardCell, direction)).hasPawn()) {
                    //If it can not go behind, tries to move diagonally
                    // actually it makes moves forward then on the chosen side).
                    int tries = 0; //The number of tried moves.
                    //Coordinates of the would-be cell it will use to move "diagonally".
                    int choice = random.nextInt(1); //choose random diagonal option.
                    Coord directionBis;

                    if (choice == 0) {
                        //Choose the non-clockwise option.
                        int randintBis;
                        //Changes its direction accordingly taking in account the bounds of the array.
                        if (randint == 0)
                            randintBis = 3;
                        else
                            randintBis = randint - 1;
                        directionBis = getDirection(randintBis);
                        if (Rules.canMove(ctrl, directionBis, forwardCell) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(1))).hasPawn()){
                            ctrl.move(direction); //moves on the same cell as the other pawn
                            ctrl.move(directionBis); //moves to the side not to end in the wall
                        } else {
                            if (tries == 0) {
                                //The chosen option (non-clockwise) was not possible, changes choice.
                                choice = 1;
                                tries += 1;
                            } else action = 1;
                        }
                        //CASE IN WHICH THERE IS A PAWN IN FRONT AND A WALL BEHIND IT HALF-HANDLED.
                    } else if (choice == 1) {
                        //Choose the clockwise option
                        int randintBis;
                        //Changes its direction accordingly taking in account the bounds of the array.
                        randintBis = randint + 1;
                        if (randintBis == 4)
                            randintBis = 0;
                        directionBis = getDirection(randintBis);
                        if (Rules.canMove(ctrl, directionBis, forwardCell) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(1))).hasPawn()){
                            ctrl.move(direction); //moves on the same cell as the other pawn
                            ctrl.move(directionBis); //moves to the side not to end in the wall
                        }

                        else {

                            //The chosen option (non-clockwise) was not possible either, tries to move side or back
                            if (Rules.canMove(ctrl,getDirection(randint - 1)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(randint - 1))).hasPawn()) {
                                ctrl.move(getDirection(randint - 1));
                            }
                            else if (Rules.canMove(ctrl, getDirection(randint + 1)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(randint + 1))).hasPawn()){
                                ctrl.move(getDirection(randint + 1));
                            }
                            else {
                                if(randint == 0 && Rules.canMove(ctrl, getDirection(2)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(2))).hasPawn()){
                                    randint = 2;
                                    ctrl.move(getDirection(randint));
                                }
                                else if (randint == 1 && Rules.canMove(ctrl, getDirection(3)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(3))).hasPawn()){
                                    randint = 3;
                                    ctrl.move(getDirection(randint));
                                }
                                else if (randint == 2 && Rules.canMove(ctrl, getDirection(0)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(0))).hasPawn()){
                                    randint = 0;
                                    ctrl.move(getDirection(randint));
                                }
                                else if (randint == 3 && Rules.canMove(ctrl, getDirection(1)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(1))).hasPawn()){
                                    randint = 1;
                                    ctrl.move(getDirection(randint));
                                }
                                else {
                                    while (ctrl.getBoard().getCell(forwardCell).hasPawn()){
                                        if(ctrl.getBoard().getCell(Coord.add(forwardCell, direction)).hasPawn()){
                                            forwardCell = Coord.add(forwardCell, direction);
                                        }
                                        else if (!Rules.canMove(ctrl, direction, forwardCell)){
                                            switch (randint){
                                                case 0:
                                                    randint = 2;
                                                    break;
                                                case 1:
                                                    randint = 3;
                                                    break;
                                                case 2:
                                                    randint = 0;
                                                    break;
                                                case 3:
                                                    randint = 1;
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                        if (Rules.canMove(ctrl,getDirection(randint - 1)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(randint - 1))).hasPawn()) {
                                            ctrl.move(getDirection(randint - 1));
                                        }
                                        else if (Rules.canMove(ctrl, getDirection(randint + 1)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(randint + 1))).hasPawn()){
                                            ctrl.move(getDirection(randint + 1));
                                        }
                                        else {
                                            if (randint == 0 && Rules.canMove(ctrl, getDirection(2)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(2))).hasPawn()) {
                                                randint = 2;
                                                ctrl.move(getDirection(randint));
                                            } else if (randint == 1 && Rules.canMove(ctrl, getDirection(3)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(3))).hasPawn()) {
                                                randint = 3;
                                                ctrl.move(getDirection(randint));
                                            } else if (randint == 2 && Rules.canMove(ctrl, getDirection(0)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(0))).hasPawn()) {
                                                randint = 0;
                                                ctrl.move(getDirection(randint));
                                            } else if (randint == 3 && Rules.canMove(ctrl, getDirection(1)) && !ctrl.getBoard().getCell(Coord.add(currentCell, getDirection(1))).hasPawn()) {
                                                randint = 1;
                                                ctrl.move(getDirection(randint));
                                            }
                                            else {
                                                if (tries == 0) {
                                                    //The chosen option (non-clockwise) was not possible, changes choice.
                                                    choice = 0;
                                                    tries += 1;
                                                } else action = 1;
                                            }
                                        }
                                    }
                                }
                            }

                        }

                        //CASE IN WHICH THERE IS A PAWN IN FRONT AND A WALL BEHIND HANDLED.
                    }
                } else {
                    //If it can go behind.
                    ctrl.move(direction); //moves on the same cell as the other pawn
                    ctrl.move(direction); //moves on the cell behind the other pawn
                }
            } else {
                //If there is no special condition like a pawn in front of itself
                ctrl.move(direction);
            }


        } else if (action == 1){ //Tries and place a wall

            if (ctrl.getNumbWall() > 0) {
                Coord placeCoord;
                Coord placeDir;
                int triesWalls = 0;
                do {
                    int ordinate = random.nextInt(ctrl.getBoard().getSize() - 1);
                    if (ordinate == 0){ //We want ordinates 1 to 8
                        ordinate = ctrl.getBoard().getSize()-1;
                    }

                    int absciss = random.nextInt(ctrl.getBoard().getSize() - 2);
                    //We want abcisses 0 to 7
                    placeCoord = new Coord(ordinate, absciss);

                    int intDir = random.nextInt(2);
                    if (intDir == 1)
                        intDir = 3;
                    placeDir = getDirection(intDir);
                    triesWalls++;
                }
                while ( !Rules.canPlaceWall(playerArray, ctrl, placeCoord, placeDir) && triesWalls <= 25);
                if (triesWalls <= 25) {
                    ctrl.placeWall(placeCoord, placeDir);
                } else {
                    debilusActionHandler(playerArray, ctrl);
                }
            } else {
                debilusActionHandler(playerArray, ctrl);
            }
        }


    }

    /**
     * Use a integer between 0 and 3 (both included) to permit a random choice via a random integer.
     * @param i the integer mentioned above.
     * @return a direction from Game.direction
     * @throws IllegalArgumentException in case the method does not receive an integer between 0 and 3 both included.
     */
    protected static Coord getDirection(int i) throws IllegalArgumentException {
        if (i > 3){
            i = i%3;
        }
        switch (i){
            case 0:
                return Game.directions.get("UP");

            case 1:
                return Game.directions.get("LEFT");

            case 2:
                return Game.directions.get("DOWN");

            case 3:
                return Game.directions.get("RIGHT");

            default:
                throw new IllegalArgumentException("Did not receive 0, 1, 2 or 3 as integer.");

        }
    }

    /**
     * Use the Random module to generate a random integer (see usage in getDirection() method).
     * @return an integer between 0 and 3 both included.
     */
    protected static int randomizeDirection(){
        float randfloat = random.nextInt(4);
        return Math.round(randfloat);

    }

    /**
     * given a player (PawnController). It will check all the cell around its position and return an array
     * containing all the cells where the player can go from it's position.
     * It takes in consideration walls and pawns.
     *
     * @param ctrl the player/controller for whom we are looking for reachable cell.
     * @return an array with all the cell reachable from the player's position.
     */
    public static Coord[] whereCanIGo(PawnController ctrl) {
        //TODO the code is awful and I believe it can ba greatly optimized and cleaned up. But we don't really have time to improve it.
        ArrayList<Coord> list = new ArrayList<Coord>();
        Board board = ctrl.getBoard();
        Coord ctrlCoord = ctrl.getDependency().getCoord();
        Set<String> keys = Game.directions.keySet();
        for (String key : keys) {
            Coord dir = Game.directions.get(key);
            if (Rules.canMove(ctrl, Game.directions.get(key)) && !board.getCell(Coord.add(ctrlCoord, dir)).hasPawn()) {
                //no pawn, no wall, free to go
                list.add(Coord.add(ctrlCoord, dir));
            } else {
                //there is a pawn
                Coord currentCoord = ctrl.getDependency().getCoord();
                Coord encounteredPawnCoord = Coord.add(currentCoord, dir);
                Coord behindCoord = Coord.add(encounteredPawnCoord, dir);
                boolean mainFlag = true;
                while (Rules.canMove(ctrl, Game.directions.get(key), currentCoord) && board.getCell(encounteredPawnCoord).hasPawn() && mainFlag) {
                    //there is a pawn, we need to check if there is a wall or a pawn behind it
                    if (!Rules.canMove(ctrl, dir, encounteredPawnCoord)) {
                        //there is a wall behind the encountered pawn. We need to check on
                        //encountered pawn's sides.
                        //there is 2 sides direction, if the current dir is UP or DOWN --> side dir will be LEFT and RIGHT
                        //if the current dir is LEFT or RIGHT --> side dir will be UP and DOWN
                        if (dir.compareTo(Game.directions.get("UP")) == 0 || dir.compareTo(Game.directions.get("DOWN")) == 0) {
                            //side dir are LEFT and RIGHT
                            boolean[] flag = {false, false};
                            if (Rules.canMove(ctrl, Game.directions.get("LEFT"), encounteredPawnCoord)
                                    && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("LEFT"))).hasPawn()) {
                                //no wall no pawn
                                list.add(Coord.add(encounteredPawnCoord, Game.directions.get("LEFT")));
                            } else if (Rules.canMove(ctrl, Game.directions.get("LEFT"), encounteredPawnCoord)
                                    && board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("LEFT"))).hasPawn()) {
                                //no wall and a pawn
                                currentCoord = encounteredPawnCoord;
                                dir = Game.directions.get("LEFT");
                                encounteredPawnCoord = Coord.add(encounteredPawnCoord, dir);
                                behindCoord = Coord.add(encounteredPawnCoord, dir);
                            } else {
                                //there is a wall or a pawn on encountered pawn's sides. We go behind him
                                flag[0] = true;
                            }
                            if (Rules.canMove(ctrl, Game.directions.get("RIGHT"), encounteredPawnCoord)
                                    && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("RIGHT"))).hasPawn()) {
                                list.add(Coord.add(encounteredPawnCoord, Game.directions.get("RIGHT")));
                            } else if (Rules.canMove(ctrl, Game.directions.get("RIGHT"), encounteredPawnCoord)
                                    && board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("RIGHT"))).hasPawn()) {
                                //no wall and a pawn
                                currentCoord = encounteredPawnCoord;
                                dir = Game.directions.get("RIGHT");
                                encounteredPawnCoord = Coord.add(encounteredPawnCoord, dir);
                                behindCoord = Coord.add(encounteredPawnCoord, dir);
                            } else {
                                //there is a wall or a pawn on encountered pawn's sides. We go behind him
                                flag[1] = true;
                            }
                            if (flag[0] && flag[1]) {
                                currentCoord = encounteredPawnCoord;
                                encounteredPawnCoord = Coord.add(encounteredPawnCoord, dir);
                                behindCoord = Coord.add(encounteredPawnCoord, dir);
                            } else {
                                mainFlag = false;
                            }
                        } else {
                            //side dir are UP and DOWN
                            boolean[] flag = {false, false};
                            if (Rules.canMove(ctrl, Game.directions.get("UP"), encounteredPawnCoord)
                                    && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("UP"))).hasPawn()) {
                                list.add(Coord.add(encounteredPawnCoord, Game.directions.get("UP")));
                            } else {
                                //there is a wall or a pawn on encountered pawn's sides. We go behind him
                                flag[0] = true;
                            }
                            if (Rules.canMove(ctrl, Game.directions.get("DOWN"), encounteredPawnCoord)
                                    && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("DOWN"))).hasPawn()) {
                                list.add(Coord.add(encounteredPawnCoord, Game.directions.get("DOWN")));
                            } else {
                                //there is a wall or a pawn on encountered pawn's sides. We go behind him
                                flag[1] = true;
                            }
                            if (flag[0] && flag[1]) {
                                currentCoord = encounteredPawnCoord;
                                encounteredPawnCoord = Coord.add(encounteredPawnCoord, dir);
                                behindCoord = Coord.add(encounteredPawnCoord, dir);
                            } else {
                                mainFlag = false;
                            }
                        }
                    } else if (board.getCell(Coord.add(encounteredPawnCoord, dir)).hasPawn()) {
                        //there is a pawn behind the encountered pawn. We need to check on
                        //encountered pawn's sides.
                        //there is 2 sides direction, if the current dir is UP or DOWN --> side dir will be LEFT and RIGHT
                        //if the current dir is LEFT or RIGHT --> side dir will be UP and DOWN
                        if (dir.compareTo(Game.directions.get("UP")) == 0 || dir.compareTo(Game.directions.get("DOWN")) == 0) {
                            //side dir are LEFT and RIGHT
                            boolean[] flag = {false, false};
                            if (Rules.canMove(ctrl, Game.directions.get("LEFT"), encounteredPawnCoord)
                                    && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("LEFT"))).hasPawn()) {
                                list.add(Coord.add(encounteredPawnCoord, Game.directions.get("LEFT")));
                            } else {
                                //there is a wall or a pawn on encountered pawn's sides. We go behind him
                                flag[0] = true;
                            }
                            if (Rules.canMove(ctrl, Game.directions.get("RIGHT"), encounteredPawnCoord)
                                    && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("RIGHT"))).hasPawn()) {
                                list.add(Coord.add(encounteredPawnCoord, Game.directions.get("RIGHT")));
                            } else {
                                //there is a wall or a pawn on encountered pawn's sides. We go behind him
                                flag[1] = true;
                            }
                            if (flag[0] && flag[1]) {
                                currentCoord = encounteredPawnCoord;
                                encounteredPawnCoord = Coord.add(encounteredPawnCoord, dir);
                                behindCoord = Coord.add(encounteredPawnCoord, dir);
                            } else {
                                mainFlag = false;
                            }
                        } else {
                            //side dir are UP and DOWN
                            boolean[] flag = {false, false};
                            if (Rules.canMove(ctrl, Game.directions.get("UP"), encounteredPawnCoord)
                                    && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("UP"))).hasPawn()) {
                                list.add(Coord.add(encounteredPawnCoord, Game.directions.get("UP")));
                            } else {
                                //there is a wall or a pawn on encountered pawn's sides. We go behind him
                                flag[0] = true;
                            }
                            if (Rules.canMove(ctrl, Game.directions.get("DOWN"), encounteredPawnCoord)
                                    && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("DOWN"))).hasPawn()) {
                                list.add(Coord.add(encounteredPawnCoord, Game.directions.get("DOWN")));
                            } else {
                                //there is a wall or a pawn on encountered pawn's sides. We go behind him
                                flag[1] = true;
                            }
                            if (flag[0] && flag[1]) {
                                currentCoord = encounteredPawnCoord;
                                encounteredPawnCoord = Coord.add(encounteredPawnCoord, dir);
                                behindCoord = Coord.add(encounteredPawnCoord, dir);
                            } else {
                                mainFlag = false;
                            }
                        }
                    } else if (Rules.canMove(ctrl, dir, encounteredPawnCoord) && !board.getCell(behindCoord).hasPawn()) {
                        //no pawn, no wall, free to go !
                        list.add(behindCoord);
                        currentCoord = encounteredPawnCoord;
                        encounteredPawnCoord = Coord.add(encounteredPawnCoord, dir);
                        behindCoord = Coord.add(encounteredPawnCoord, dir);
                    } else {
                        currentCoord = encounteredPawnCoord;
                        encounteredPawnCoord = Coord.add(encounteredPawnCoord, dir);
                        behindCoord = Coord.add(encounteredPawnCoord, dir);
                    }
                }
            }
        }
        Coord[] array = list.toArray(Coord[]::new); //convert to an array to make sure it won't change
        return  array;
    }

}
