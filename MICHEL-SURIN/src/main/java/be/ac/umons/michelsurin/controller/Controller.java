package be.ac.umons.michelsurin.controller;

import be.ac.umons.michelsurin.items.MOAI;
import be.ac.umons.michelsurin.world.Board;

import java.io.Serializable;

/**
 * the base of all controller type with the minimum requirement.
 * A controller is something bound to a MOAI object. The controller knows the situation of the game through the board
 * and give access to method that allow to input action.
 */
public abstract class Controller implements Serializable {

    public static final long serialVersionUID = 9113414371385892131L;
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
