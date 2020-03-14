
class Pawn extends MOAI {
    /**
     * the starting position of the pawn. Must be a (odd, odd) coord
     */
    private Coord start;

    public Pawn(Coord coord, true) {
        super(coord, true);
        this.start = coord;
    }

}