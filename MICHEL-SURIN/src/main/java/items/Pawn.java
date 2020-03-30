package items;

import tools.Coord;

/**
 * represents a pawn that may be controller later by a controller.
 */
public class Pawn extends MOAI {

    private Coord start;

    /**
     * @param start the starting coordinates of the pawn on the board.
     */
    public Pawn(Coord start) {
        super(start); //at the beginning, the coordinates of the pawn == starting coordinates
        this.start = start;
    }

    public Coord getStart() {
        return start;
    }
}
