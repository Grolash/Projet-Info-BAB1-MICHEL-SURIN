package controller;


import engine.Game;
import engine.Rules;
import items.Pawn;
import tools.Coord;

import java.util.Random;


public class Action {


    private static Random random = new Random();

    /**
     * Given a controller, it will, depending on its type (Human, AI, etc)
     * call the correct method that will do an action.
     *
     *
     * @param playerArray
     * @param ctrl the controller
     * @throws IllegalArgumentException is thrown when a controller as an invalid type.
     */
    public static void getAction(PawnController[] playerArray, PawnController ctrl) throws IllegalArgumentException {
        if (ctrl.getType() == "Human") {
            //call HumanActionHandler
        }
        switch (ctrl.getType()) {
            case "Human" :
                humanActionHandler(playerArray, ctrl);
                break;

            case "Debilus" :
                debilusActionHandler(playerArray, ctrl);
                break;
            case "Dabilus" :
                debilusActionHandler(playerArray, ctrl);
                break;

            case "Smart" :
                smartActionHandler(playerArray, ctrl);
                break;

            case "Smarted" :
                smartedActionHandler(playerArray, ctrl);
                break;
            case "DebugDOWN" :
                debugDOWNActionHandler(playerArray, ctrl);
                break;
            case "DebugUP" :
                debugUPActionHandler(playerArray, ctrl);
                break;

            default :
                throw new IllegalArgumentException("the controller has an incorrect type");
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
     * Smarted does not go the opposite way of the gaol (should noticeably enhance it's ability to find it).
     * Also, we figured out placing a wall between one's start and one's current
     * position (included) leads to better situations, so Smarted place walls accordingly.
     * Nevertheless it does never place a wall between itself and the goal, it moves it one cell further right.
     * @param playerArray
     * @param ctrl
     */
    private static void smartedActionHandler(PawnController[] playerArray, PawnController ctrl) {
        if (smartedActionChangelog > 1) //See above the method.
            smartedActionChangelog = 0; //Just a reinitialisation.

        if (smartedActionChangelog == 0){ //Tries and move.
            // Almost same as Debilus but does not go the opposite direction of the goal
            //(except to do diagonal moves which might be necessary).
            Coord direction;
            int randint;
            Pawn pawned = (Pawn) ctrl.getDependency();

            do {
                randint = randomizeDirection();
                if(pawned.getStart().getY() == 0){ //If the pawn comes from the top...
                    if (randint == 0) //...does not go up...
                        randint = 2; //...but down instead!
                }
                else if(pawned.getStart().getY() == ctrl.getBoard().getSize()){ //If the pawn comes from the bottom...
                    if (randint == 2) //...does not go down...
                        randint = 0; //...but up instead!
                }
                else if(pawned.getStart().getX() == 0){ //If the pawn comes from the left...
                    if (randint == 1) //...does not go left...
                        randint = 3; //...but right instead!
                }
                else if(pawned.getStart().getX() == ctrl.getBoard().getSize()){ //If the pawn comes from the right...
                    if (randint == 3) //...does not go right...
                        randint = 1; //...but left instead!
                }
                //REDIRECTION HANDLED
                direction = getDirection(randint); //Choose a random direction
            }
            while (!(Rules.canMove(ctrl, direction))); //Does so until it can move.

            Coord forwardCell = Coord.add(ctrl.getDependency().getCoord(), direction);
            //Coordinates of the intended move cell

            if (ctrl.getBoard().getCell(forwardCell).hasPawn()) {
                //If there is a pawn in "front" of itself, tries to bypass it.

                if (!(Rules.canMove(ctrl, direction, forwardCell))) {
                    //If it can not go behind, tries to move diagonally
                    // (actually it makes moves forward then on the chosen side).
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
                            } else smartedActionChangelog = 1;
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
                            } else smartedActionChangelog = 1;
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
                do {
                    int ordinate;
                    if (ctrl.getDependency().getCoord().getY() == 0)
                        ordinate = random.nextInt(1);
                    else
                        ordinate = random.nextInt(ctrl.getDependency().getCoord().getY());
                    //Does not place a wall further than itself.
                    int absciss = random.nextInt(ctrl.getBoard().getSize());

                    int intDir = random.nextInt(1);
                    if (intDir == 1)
                        intDir = 3;

                    if ((absciss == ctrl.getDependency().getCoord().getX()) & (intDir == 3))
                        absciss += 1;
                    //If the wall is in the way of the goal, displace it.

                    placeCoord = new Coord(ordinate, absciss);
                    placeDir = getDirection(intDir);
                }
                while (!(Rules.canPlaceWall(playerArray, ctrl, placeCoord, placeDir)));

                ctrl.placeWall(placeCoord, placeDir);
            }
            else
                Action.smartedActionHandler(playerArray, ctrl);
                smartedActionChangelog += 1;

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

        int action = random.nextInt(1); //Choose randomly between moving and placing a wall.

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


        }

        else if (action == 1){ //Tries and place a wall
            if (ctrl.getNumbWall() > 0) {
                Coord placeCoord;
                Coord placeDir;
                do {
                    int ordinate = random.nextInt(ctrl.getBoard().getSize());
                    int absciss = random.nextInt(ctrl.getBoard().getSize());
                    placeCoord = new Coord(ordinate, absciss);

                    int intDir = random.nextInt(1);
                    if (intDir == 1)
                        intDir = 3;
                    placeDir = getDirection(intDir);
                }
                while (!(Rules.canPlaceWall(playerArray, ctrl, placeCoord, placeDir)));

                ctrl.placeWall(placeCoord, placeDir);
            }

            else
                Action.debilusActionHandler(playerArray, ctrl);

        }


    }


    /**
     * The method where a human player will be able to choose his next action.
     *
     * @param ctrl the controller for which we are picking an action
     * @return an action
     */
    private static void humanActionHandler(PawnController[] playerArray, PawnController ctrl) {
        //TODO implement it
    }


    public Random getRandom() {
        return random;
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

}
