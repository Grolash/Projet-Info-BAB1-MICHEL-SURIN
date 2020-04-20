package be.ac.umons.michelsurin.items;

import be.ac.umons.michelsurin.tools.Coord;

/**
 * represents a pawn that may be be.ac.umons.michelsurin.controller later by a be.ac.umons.michelsurin.controller.
 *
 * @author Simon MICHEL
 */
public class Pawn extends MOAI {

    //TODO implements 4 player support --> goalRow / goalColumn
    private Coord start;
    private int goalRow;
    /**
     * @param start the starting coordinates of the pawn on the board.
     * @param goalRow the index of the row the pawn must get to win.
     */
    public Pawn(Coord start, int goalRow) {
        super(start); //at the beginning, the coordinates of the pawn == starting coordinates
        this.start = start;
        this.goalRow = goalRow;
    }

    public Coord getStart() {
        return start;
    }

    public int getGoalRow() {
        return goalRow;
    }
}
