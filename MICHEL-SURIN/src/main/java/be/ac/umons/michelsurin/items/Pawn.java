package be.ac.umons.michelsurin.items;

import be.ac.umons.michelsurin.tools.Coord;

/**
 * represents a pawn that may be controlled later by a controller.
 *
 * @author Simon MICHEL
 */
public class Pawn extends MOAI {

    private Coord start;
    private int goal;
    private boolean goalIsARow;
    /**
     * @param start the starting coordinates of the pawn on the board.
     * @param goal the index of the row the pawn must get to win.
     * @param goalIsARow true : the goal is a row line, false : the goal is a column.
     */
    public Pawn(Coord start, int goal, boolean goalIsARow) {
        super(start); //at the beginning, the coordinates of the pawn == starting coordinates
        this.start = start;
        this.goal = goal;
        this.goalIsARow = goalIsARow;
    }

    public Coord getStart() {
        return start;
    }

    public int getGoal() {
        return goal;
    }

    /**
     *
     * @return true if the goal is a row, false if it is a column.
     */
    public boolean doesGoalIsARow() {
        return goalIsARow;
    }
}
