package items;

import global.Coord;

/**
 * Mother Of All Items. Set-up the base parameter for an item
 */
public class MOAI {

    Coord coord;
    boolean movable;

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