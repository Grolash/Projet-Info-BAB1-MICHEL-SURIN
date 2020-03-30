package items;

import tools.Coord;

/**
 * represents a wall
 */
public class Wall extends MOAI {

    /**
     * @param origin the origin of the wall
     * @param direction the direction used to determinate the second part of the wall
     */
    public Wall(Coord origin, Coord direction) {
        super(origin);
        origin.add(direction); //the coordinates of the second part of the wall.
        new Wall(origin); //has no direction because it's the last part of the wall.
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
