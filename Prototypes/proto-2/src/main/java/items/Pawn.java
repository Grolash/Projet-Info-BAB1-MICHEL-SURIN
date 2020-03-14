package items;

import global.Coord;

class Pawn extends MOAI {
    /**
     * the starting position of the pawn. Must be a (odd, odd) coord
     */
    private Coord start;

    public Pawn(Coord coord, boolean movable, Coord start) {
        super(coord, movable);
        this.start = start;
    }

}