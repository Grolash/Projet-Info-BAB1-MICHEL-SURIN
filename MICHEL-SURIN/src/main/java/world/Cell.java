package world;

import tools.Coord;
import java.util.Hashtable;

/**
 * represents one cell of the board.
 */
public class Cell {

    /**
     * the coordinates of the cell in the board.
     */
    private Coord coord;
    /**
     * true if there is a pawn, false otherwise.
     */
    private boolean pawn;
    /**
     * contains all the statement about the wall on side of the cell.
     */
    private Hashtable<String, Boolean> wallOnSide;

    /**
     * initiate a cell with his coordinates on the board and a wall if the cell is at board's boarder.
     *
     * @param coord the coordinates of the cell in the board.
     * @param sizeOfBoard the total size of the board. Used to determines if a cell is at the border of the board.
     */
    public Cell(Coord coord, int sizeOfBoard) {
        this.coord = coord;
        pawn = false;

        //initiate the border side of the cell to true (has wall), false in every other case
        wallOnSide = new Hashtable<String, Boolean>();

        if (this.coord.getY() == 0)
            wallOnSide.put("UP", true);
        else
            wallOnSide.put("UP", false);

        if (this.coord.getY() == sizeOfBoard-1)
            wallOnSide.put("DOWN", true);
        else
            wallOnSide.put("DOWN", false);

        if (this.coord.getX() == 0)
            wallOnSide.put("LEFT", true);
        else
            wallOnSide.put("LEFT", false);

        if (this.coord.getX() == sizeOfBoard-1)
            wallOnSide.put("RIGHT", true);
        else
            wallOnSide.put("RIGHT", false);
    }

    /**
     * checks if the cell contains a pawn or not
     *
     * @return return true if there is a pawn on the cell, false otherwise.
     */
    public boolean hasPawn() {
        return pawn;
    }

    /**
     * given a direction, checks if there is a wall to this direction
     *
     * @param direction determines which side of the cell will be checked.
     * @return true if there is a wall in the given direction, false otherwise.
     */
    public boolean wallTo(Coord direction) {
        if (direction.getY() == -1) //direction is UP
            return wallOnSide.get("UP");
        if (direction.getY() == 1) //direction is DOWN
            return wallOnSide.get("DOWN");
        if (direction.getX() == 1) //direction is RIGHT
            return wallOnSide.get("RIGHT");
        else //direction is LEFT
            return wallOnSide.get("LEFT");
    }
    public void setWallOnSide(String side) {
        wallOnSide.put(side, true);
    }

    public void setPawn(boolean pawn) {
        this.pawn = pawn;
    }
}
