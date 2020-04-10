package controller;

import engine.Game;
import items.Wall;
import tools.Coord;
import world.Board;

import java.security.InvalidParameterException;

public class Action {


    /**
     * Given a controller, it will, depending on its type (Humain, AI, etc)
     * call the correct method that will do an action.
     *
     * @param ctrl the controller
     * @throws IllegalArgumentException is thrown when a controller as an invalid type.
     */
    public static void getAction(Game game, PawnController ctrl) throws IllegalArgumentException {
        if (ctrl.getType() == "Human") {
            //call HumanActionHandler
        }
        switch (ctrl.getType()) {
            case "Human" :
                humanActionHandler(game, ctrl);
                break;

            case "Debilus" :
                debilusActionHandler(game, ctrl);
                break;

            case "Smart" :
                smartActionHandler(game, ctrl);
                break;

            case "Smarted" :
                smartedActionHandler(game, ctrl);
                break;

            default :
                throw new IllegalArgumentException("the controller has an incorrect type");
        }

    }

    private static void smartedActionHandler(Game game, PawnController ctrl) {
    }

    private static void smartActionHandler(Game game, PawnController ctrl) {
    }

    private static void debilusActionHandler(Game game, PawnController ctrl) {
    }

    /**
     * The method where a human player will be able to choose his next action.
     *
     * @param ctrl the controller for which we are picking an action
     * @return an action
     */
    private static void humanActionHandler(Game game, PawnController ctrl) {
        //TODO implement it
    }
}
