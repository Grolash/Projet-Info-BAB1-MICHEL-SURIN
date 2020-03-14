package items;

import global.Coord;

class Wall extends MOAI {

    /**
     *
     * @param coord the coord of the wall
     * @param orientation integer representing the orientation of the wall,
     *                    0 is for horizontal,
     *                    1 is for vertical,
     *                    -1 is uses to mark the "end" of the wall.
     */
    public Wall(Coord coord, int orientation) {
        super(coord, false);
        if (orientation == 0) {
            Coord dir1 = new Coord(0, -1); //LEFT
            Coord dir2 = new Coord(0, 1); //RIGHT
            coord.add(dir1);
            coord.add(dir2);
            new Wall(dir1, -1); // create a wall on the left of the actual wall
            new Wall(dir2, -1); // create a wall on the right of the actual wall
        }
        else if (orientation == 1) {
            Coord dir1 = new Coord(1, 0); //DOWN
            Coord dir2 = new Coord(-1, 0); //UP
            coord.add(dir1);
            coord.add(dir2);
            new Wall(dir1, -1); // create a wall under the actual wall
            new Wall(dir2, -1); // create a wall on top of the actual wall
        }
    }


}