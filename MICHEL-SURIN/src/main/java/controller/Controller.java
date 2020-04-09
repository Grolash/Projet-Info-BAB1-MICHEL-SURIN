package controller;

import items.MOAI;
import world.Board;

/**
 * tha base of all controller type with the minimum requirement.
 */
public abstract class Controller {

    private boolean AI;
    private MOAI dependency;
    private Board board;

    /**
     *
     * @param AI true : controlled by an AI, false for a human
     * @param dependency refers to the items the controller is controlling
     * @param board the board in which the controller is evolving
     */
    public Controller(boolean AI, MOAI dependency, Board board) {
        this.AI = AI;
        this.dependency = dependency;
        this.board = board;
    }

    /**
     *
     * @return true if the controller is handled by an AI, false otherwise.
     */
    public boolean isAI() {
        return AI;
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
