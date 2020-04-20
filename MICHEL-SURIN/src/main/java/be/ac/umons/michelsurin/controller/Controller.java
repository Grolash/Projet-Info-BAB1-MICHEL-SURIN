package be.ac.umons.michelsurin.controller;

import be.ac.umons.michelsurin.items.MOAI;
import be.ac.umons.michelsurin.world.Board;

/**
 * the base of all be.ac.umons.michelsurin.controller type with the minimum requirement.
 */
public abstract class Controller {

    private final String type;
    private MOAI dependency;
    private Board board;

    /**
     *  @param type define the type of the be.ac.umons.michelsurin.controller. Used to determined it's action.
     * @param dependency refers to the be.ac.umons.michelsurin.items the be.ac.umons.michelsurin.controller is controlling.
     * @param board the board in which the be.ac.umons.michelsurin.controller is evolving.
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
