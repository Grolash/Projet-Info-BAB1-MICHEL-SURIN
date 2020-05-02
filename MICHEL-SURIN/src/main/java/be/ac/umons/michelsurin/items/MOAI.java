package be.ac.umons.michelsurin.items;

import be.ac.umons.michelsurin.tools.Coord;

/**
 * defines the minimum requirements of an item.
 * An item is something in the game, like a wall, a pawn.
 * It does not control itself.
 *
 * @author Simon MICHEL
 */
public abstract class MOAI {

    private Coord coord;

    /**
     * @param coord initiates the item at this coordinates.
     */
    public MOAI(Coord coord) {
        this.coord = coord;
    }

    public Coord getCoord() {
        return coord;
    }
    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
