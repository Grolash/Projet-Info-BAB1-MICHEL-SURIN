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
