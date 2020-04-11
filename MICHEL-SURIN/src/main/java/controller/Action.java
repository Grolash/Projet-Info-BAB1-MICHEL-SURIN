package controller;

public class Action {


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

            case "Smart" :
                smartActionHandler(playerArray, ctrl);
                break;

            case "Smarted" :
                smartedActionHandler(playerArray, ctrl);
                break;

            default :
                throw new IllegalArgumentException("the controller has an incorrect type");
        }

    }

    private static void smartedActionHandler(PawnController[] playerArray, PawnController ctrl) {
    }

    private static void smartActionHandler(PawnController[] playerArray, PawnController ctrl) {
    }

    private static void debilusActionHandler(PawnController[] playerArray, PawnController ctrl) {
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
}
