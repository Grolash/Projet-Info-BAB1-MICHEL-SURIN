package tools;

/**
 * class that represents a coord within a board with (ordinate, absciss) format.
 */
public class Coord {

    private int y;
    private int x;

    /**
     * represents something on a 2D cartesian coordinate system with (y,x) format
     *
     * @param y the ordinate
     * @param x the absciss
     */
    public Coord(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "(" + y + ", " + x + ")";
    }

    /**
     * checks if two coordinates are equals or not
     *
     * @param other the coordinate that will be compared with the current object
     * @return      true if the two objects have the same y AND the same X, false otherwise
     */
    public boolean equals(Coord other) {
        return this.getY() == other.getY() &&
                this.getX() == other.getX();
    }

    /**
     * Increments y and x value of the instance the method is applied
     *
     * @param other is a Coord object whom will be added to the instance.
     */
    public void add(Coord other) { //TODO override "+" symbol
        this.y += other.getY();
        this.x += other.getX();
    }


}

