package be.ac.umons.michelsurin.engine;


import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.items.Pawn;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;

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
            case "Debilus":
            case "Dabilus":
                debilusActionHandler(playerArray, ctrl);
                break;
            case "Smart":
                smartActionHandler(playerArray, ctrl);
                break;
            case "Smarted":
                smartedActionHandler(playerArray, ctrl);
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

    private static void smartActionHandler(PawnController[] playerArray, PawnController ctrl) {

    }

    private static int smartedActionChangelog = 0; //Used not to do the same action twice in a row.

    /**
     * Smarted AI:
     * A slightly less random AI, with a bit of strategy taken in consideration:
     * Smarted follows a path defined by pathfinding tool.
     * Also, we figured out placing a wall between one's start and one's current
     * position (included) leads to better situations, so Smarted place walls accordingly.
     * Nevertheless it does never place a wall between itself and the goal, it moves it one cell further right.
     * @param playerArray
     * @param ctrl
     * @throws IllegalArgumentException incorrect delta calculation between two coordinates.
     */
    private static void smartedActionHandler(PawnController[] playerArray, PawnController ctrl) throws IllegalArgumentException {
        int half = ctrl.getBoard().getSize()/2;
        if (smartedActionChangelog > 1) { //See above the method.
            smartedActionChangelog = 0; //Just a reinitialisation.
        }
        if (smartedActionChangelog == 0){
            //Tries and move.
            // Almost same as Debilus but follows a path
            Coord direction;
            int randint; //   /!\    Was a random tool, moved to be a direction indicator
            Pawn pawned = (Pawn) ctrl.getDependency();

            ArrayList<Coord> path = Rules.path(ctrl);
            Coord next = path.remove(path.size()-1);
            int deltaY = next.getY() - ctrl.getDependency().getCoord().getY();
            int deltaX = next.getX() - ctrl.getDependency().getCoord().getX();

            switch (deltaY){
                case 1:
                    direction = getDirection(2);
                    randint = 2;
                    break;

                case -1:
                    direction = getDirection(0);
                    randint = 0;
                    break;

                case 0:
                    switch (deltaX){
                        case 1:
                            direction = getDirection(3);
                            randint = 3;
                            break;

                        case -1:
                            direction = getDirection(1);
                            randint = 1;
                            break;

                        default:
                            throw new IllegalArgumentException("deltaX should be 1 or -1.");

                    }
                    break;

                default:
                    throw new IllegalArgumentException("deltaY should be 1 or -1.");
            }




            Coord forwardCell = Coord.add(ctrl.getDependency().getCoord(), direction);
            //Coordinates of the intended move cell

            if (ctrl.getBoard().getCell(forwardCell).hasPawn()) {
                //If there is a pawn in "front" of itself, tries to bypass it.

                if (!(Rules.canMove(ctrl, direction, forwardCell))) {
                    //If it can not go behind, tries to move diagonally
                    // (actually it makes moves forward then on the chosen side).
                    int tries = 0; //The number of tried moves.
                    //Coordinates of the would-be cell it will use to move "diagonally".
                    int choice = 0; //choose diagonal option (was random, is not anymore).
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
                        if (Rules.canMove(ctrl, directionBis, forwardCell)){
                            ctrl.move(direction); //moves on the same cell as the other pawn
                            ctrl.move(directionBis); //moves to the side not to end in the wall
                        }
                        else {
                            //The chosen option (non-clockwise) was not possible, changes choice.
                            choice = 1;
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
                        if (Rules.canMove(ctrl, directionBis, forwardCell)){
                            ctrl.move(direction); //moves on the same cell as the other pawn
                            ctrl.move(directionBis); //moves to the side not to end in the wall
                        }
                        else {
                            //The chosen option (non-clockwise) was not possible either, tries to move side or back
                            if (Rules.canMove(ctrl,getDirection(randint - 1))) {
                                ctrl.move(getDirection(randint - 1));
                            }
                            else if (Rules.canMove(ctrl, getDirection(randint + 1))){
                                ctrl.move(getDirection(randint + 1));
                            }
                            else {
                                if(randint == 0){
                                    randint = 2;
                                    ctrl.move(getDirection(randint));
                                }
                                else if (randint == 1){
                                    randint = 3;
                                    ctrl.move(getDirection(randint));
                                }
                                else if (randint == 2){
                                    randint = 0;
                                    ctrl.move(getDirection(randint));
                                }
                                else if (randint == 3){
                                    randint = 1;
                                    ctrl.move(getDirection(randint));
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


        }
        else if (smartedActionChangelog == 1){ //Tries and place a wall
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
                        if(pawn.getStart().getY() == 0) {
                            ordinate = random.nextInt(ctrl.getDependency().getCoord().getY() - 1);
                        }
                        else {
                            do {
                                ordinate = random.nextInt(ctrl.getBoard().getSize() );
                                }
                            while(ordinate < ctrl.getDependency().getCoord().getY() );
                        }
                    }
                    if ((ordinate == 0) & (ctrl.getDependency().getCoord().getY() != 0)) {
                        ordinate = ctrl.getDependency().getCoord().getY();
                    }
                    else if ((ordinate == 0) & (ctrl.getDependency().getCoord().getY() == 0)){
                        ordinate = 2;
                    }
                    //Does not place a wall further than itself.
                    int absciss = random.nextInt(ctrl.getBoard().getSize() - 1);

                    int intDir = random.nextInt(2);
                    if (intDir == 1)
                        intDir = 3;

                    if ((absciss == ctrl.getDependency().getCoord().getX()) & (intDir == 3))
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
                    smartedActionChangelog += 1;
                    smartedActionHandler(playerArray, ctrl);
                }

            }
            else {
                smartedActionChangelog += 1;
                smartedActionHandler(playerArray, ctrl);
            }

        }

        smartedActionChangelog += 1;
    }



    /**
     * "Debilus" AI:
     * Most basic AI, entirely random due to it's purpose. Is totally intended to be unintelligent and inefficient.
     * @param playerArray
     * @param ctrl
     */
    private static void debilusActionHandler(PawnController[] playerArray, PawnController ctrl) {

        int action = random.nextInt(2); //Choose randomly between moving and placing a wall.

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

                if (!(Rules.canMove(ctrl, direction, forwardCell))) {
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
                        if (Rules.canMove(ctrl, directionBis, forwardCell)){
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
                        if (Rules.canMove(ctrl, directionBis, forwardCell)){
                            ctrl.move(direction); //moves on the same cell as the other pawn
                            ctrl.move(directionBis); //moves to the side not to end in the wall
                        } else {
                            if (tries == 0) {
                                //The chosen option (non-clockwise) was not possible, changes choice.
                                choice = 0;
                                tries += 1;
                            } else action = 1;
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
     * It takes in consideration walls and pawns
     *
     * @param ctrl the player/controller for whom we are looking for reachable cell.
     * @return an array with all the cell reachable from the player's position.
     */
    public static Coord[] whereCanIGo(PawnController ctrl) {
        ArrayList<Coord> list = new ArrayList<Coord>();
        Board board = ctrl.getBoard();
        Coord ctrlCoord = ctrl.getDependency().getCoord();
        Set<String> keys = Game.directions.keySet();
        for (String key : keys) {
            Coord dir = Game.directions.get(key);
            if (Rules.canMove(ctrl, Game.directions.get(key)) && !board.getCell(Coord.add(ctrlCoord, dir)).hasPawn()) {
                //no pawn, no wall, free to go
                list.add(Coord.add(ctrlCoord, dir));
            } else if (Rules.canMove(ctrl, Game.directions.get(key)) && board.getCell(Coord.add(ctrlCoord, dir)).hasPawn()) {
                //there is a pawn, we need to check if there is a wall or a pawn behind it
                Coord encounteredPawnCoord = Coord.add(ctrlCoord, dir);
                Coord behindCoord = Coord.add(encounteredPawnCoord, dir);
                if (Rules.canMove(ctrl, dir, encounteredPawnCoord) && !board.getCell(behindCoord).hasPawn()) {
                    //no pawn, no wall, free to go !
                    list.add(behindCoord);
                } else if (!Rules.canMove(ctrl, dir, encounteredPawnCoord) || board.getCell(Coord.add(encounteredPawnCoord, dir)).hasPawn() ){
                    //there is a wall or a pawn behind the encountered pawn. We need to check on
                    //encountered pawn's sides.
                    //there is 2 sides direction, if the current dir is UP or DOWN --> side dir will be LEFT and RIGHT
                    //if the current dir is LEFT or RIGHT --> side dir will be UP and DOWN
                    if (dir.compareTo(Game.directions.get("UP")) == 0 || dir.compareTo(Game.directions.get("DOWN")) == 0) {
                        //side dir are LEFT and RIGHT
                        if (Rules.canMove(ctrl, Game.directions.get("LEFT"), encounteredPawnCoord)
                                && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("LEFT"))).hasPawn() ) {
                            list.add(Coord.add(encounteredPawnCoord, Game.directions.get("LEFT")));
                        }
                        if (Rules.canMove(ctrl, Game.directions.get("RIGHT"), encounteredPawnCoord)
                                && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("RIGHT"))).hasPawn()) {
                            list.add(Coord.add(encounteredPawnCoord, Game.directions.get("RIGHT")));
                        }
                    } else {
                        //side dir are UP and DOWN
                        if (Rules.canMove(ctrl, Game.directions.get("UP"), encounteredPawnCoord)
                                && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("UP"))).hasPawn()) {
                            list.add(Coord.add(encounteredPawnCoord, Game.directions.get("UP")));
                        }
                        if (Rules.canMove(ctrl, Game.directions.get("DOWN"), encounteredPawnCoord)
                                && !board.getCell(Coord.add(encounteredPawnCoord, Game.directions.get("DOWN"))).hasPawn()) {
                            list.add(Coord.add(encounteredPawnCoord, Game.directions.get("DOWN")));
                        }
                    }
                }
            }
        }
        Coord[] array = list.toArray(Coord[]::new); //convert to an array to make sure it won't change
        return  array;
    }

}
