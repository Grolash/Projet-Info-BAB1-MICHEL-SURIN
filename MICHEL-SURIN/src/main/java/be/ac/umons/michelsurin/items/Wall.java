package be.ac.umons.michelsurin.items;

import be.ac.umons.michelsurin.tools.Coord;

/**
 * represents a wall
 * It has an origin and a second part. The second part is always whether at right or above the origin
 * If the second part is above, it is a vertical wall (Vwall) if the second part is on the right,
 * it is an horizontal wall (Hwall).
 *
 * @author Simon MICHEL
 */
public class Wall extends MOAI {

    /**
     * @param origin the origin of the wall
     * @param direction the direction used to determinate the second part of the wall
     */
    public Wall(Coord origin, Coord direction) {
        super(origin);
        Coord secondPartCoord = Coord.add(origin, direction);
        new Wall(secondPartCoord); //has no direction because it's the last part of the wall.
    }

    /**
     * Should never be called by something else than the public Wall constructor !
     *
     * @param secondPartCoord the coordinates of the second part of the wall.
     */
    private Wall(Coord secondPartCoord) {
        super(secondPartCoord);
    }
}
