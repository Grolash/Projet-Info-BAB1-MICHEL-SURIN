package be.ac.umons.michelsurin.tools;

import java.io.Serializable;

/**
 * class that represents a coordinate within a board with (ordinate, absciss) format.
 */
public class Coord implements Comparable<Coord>, Serializable {

    public static final long serialVersionUID = -5745303179256398207L;

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
     * create a new Coord instance with y and x value
     * being the result of the addition between y and x value of the two terms.
     *
     * @param first the first term of the addition
     * @param second the second term of the addition
     * @return a new Coord instance that is the result of the addition
     */
    public static Coord add(Coord first, Coord second) {
        int y = first.getY() + second.getY();
        int x = first.getX() + second.getX();
        return new Coord(y,x);
    }


    @Override
    public int compareTo(Coord o) {
        if ( this.getY() == o.getY() && this.getX() == o.getX() ) {
            return 0;
        } else if (this.getY() > o.getY()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * checks whether if an equals Coord is in a given array or not.
     *
     * @param array the Coord array we are looking in.
     * @return true if it is in, false otherwise.
     */
    public boolean isIn(Coord[] array) {
        for (Coord c : array) {
            if (this.compareTo(c) == 0) {
                return true;
            }
        }
        return false;
    }
}

