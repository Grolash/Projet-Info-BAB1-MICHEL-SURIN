package controller;

import engine.Game;
import items.Wall;
import tools.Coord;
import world.Board;

import java.security.InvalidParameterException;

public class Action {

    /**
     * Will move the controller's pawn into the given direction.
     * Does not care if there is a wall or not. It does it.
     * This kind of verification must be called before calling this method.
     *
     * @param ctrl the controller
     * @param direction the direction the controller is moving
     */
    public static void move(PawnController ctrl, Coord direction) {
        Coord depCoord = ctrl.getDependency().getCoord();
        depCoord = Coord.add(depCoord, direction);
        ctrl.getDependency().setCoord(depCoord);
        ctrl.getBoard().movePawnCoord(ctrl.getPlayerNumber(), depCoord);
    }

    /**
     * given an coordinate (origin) and a direction (UP or RIGHT), place a wall and register it in the board.
     *
     * @param ctrl the controller
     * @param origin the origin of the wall
     * @param direction the direction of the wall used to create it's second part (UP or RIGHT)
     * @throws InvalidParameterException is thrown if the direction is invalid (not UP or RIGHT)
     */
    public static void placeWall(PawnController ctrl, Coord origin, Coord direction) throws InvalidParameterException {
        ctrl.addToNumbWall(-1);
        Coord[] fullWallCoord = new Coord[2];
        if (direction == Game.directions.get("UP")) {
            fullWallCoord[0] = origin;
            fullWallCoord[1] = Coord.add(origin, direction);

            Board board = ctrl.getBoard();
            board.addToWallList(fullWallCoord);
            Wall wall = new Wall(origin, direction);

        } else if (direction == Game.directions.get("RIGHT")) {
            fullWallCoord[0] = origin;
            fullWallCoord[1] = Coord.add(origin, direction);

            Board board = ctrl.getBoard();
            board.addToWallList(fullWallCoord);
            Wall wall = new Wall(origin, direction);

        } else
            throw new InvalidParameterException("Misuse direction, should ba UP or RIGHT");
    }


    public static void getAction(PawnController ctrl) throws IllegalArgumentException {
        if (ctrl.getType() == "Human") {
            //call HumanActionHandler
        }
        switch (ctrl.getType()) {
            case "Human" :
                humanActionHandler(ctrl);
                break;

            case "Debilus" :
                debilusActionHandler(ctrl);
                break;

            case "Smart" :
                smartActionHandler(ctrl);
                break;

            case "Smarted" :
                smartedActionHandler(ctrl);
                break;

            default :
                throw new IllegalArgumentException("the controller has an incorrect type");
        }

    }

    private static void smartedActionHandler(PawnController ctrl) {
    }

    private static void smartActionHandler(PawnController ctrl) {
    }

    private static void debilusActionHandler(PawnController ctrl) {
    }

    /**
     * The method where a human player will be able to choose his next action.
     *
     * @param ctrl the controller for which we are picking an action
     * @return an action
     */
    private static void humanActionHandler(PawnController ctrl) {
        //TODO implement it
    }
}
