package be.ac.umons.michelsurin.items;

import be.ac.umons.michelsurin.tools.Coord;

import java.io.Serializable;

/**
 * defines the minimum requirements of an item.
 * An item is something in the game, like a wall, a pawn.
 * It does not control itself.
 *
 * @author Simon MICHEL
 */
public abstract class MOAI implements Serializable {

    public static final long serialVersionUID = -4696500500864755130L;

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
