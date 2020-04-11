package controller;

import items.MOAI;
import world.Board;

/**
 * the base of all controller type with the minimum requirement.
 */
public abstract class Controller {

    private final String type;
    private MOAI dependency;
    private Board board;

    /**
     *  @param type define the type of the controller. Used to determined it's action.
     * @param dependency refers to the items the controller is controlling.
     * @param board the board in which the controller is evolving.
     */
    public Controller(String type, MOAI dependency, Board board) {
        this.type = type;
        this.dependency = dependency;
        this.board = board;
    }

    public String getType() {
        return type;
    }

    public MOAI getDependency() {
        return dependency;
    }

    public void setDependency(MOAI dependency) {
        this.dependency = dependency;
    }

    public Board getBoard() {
        return board;
    }
}
