package global;

/**
 * class that represents a coord within a board with (heigth, absciss) format.
 */
public class Coord {

    private int y;
    private int x;

    public Coord(int y, int x) {
        /** /!\ It is important to note that first component is ordinate and second is absciss, contrary to conventions.
         * This has been done so because of practicallity in working with arrays.
         */
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String toString() {
        return "(" + y + ", " + x + ")";
    }

    /**
     * return the equality of two global.Coord object.
     *
     * @param other the global.Coord that will be compared with the current object
     * @return      true if the two objects have the same y AND the same X, false otherwise
     */
    public boolean equals(Coord other) {
        return this.getY() == other.getY() &&
                this.getX() == other.getX();
    }

    /**
     * Increments y and x value of the instance the method is applied
     * <br> ex : given c1 = (4,5), c2 = (7,-2)
     * <br> c1.add(c2)
     * <br> c1 = (11, 3)
     *
     * @param other is a global. Coord object whom will be added to the instance.
     */
    public void add(Coord other) { //TODO override "+"
        this.setY(this.getY() + other.getY());
        this.setX(this.getX() + other.getX());
    }

}

