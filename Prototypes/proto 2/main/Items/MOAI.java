/**
 * Mother Of All Items. Set-up the base parameter for an item
 */
class MOAI {

    private Coord coord;
    private boolean movable;

    public MOAI(Coord coord, boolean movable) {
        this.coord = coord;
        this.movable = movable;
    }

    public Coord getCoord() {
        return coord;
    }

    public boolean isMovable() {
        return this.movable;
    }

}