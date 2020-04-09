package items;

import tools.Coord;

/**
 * defines the minimum requirements of an item
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
