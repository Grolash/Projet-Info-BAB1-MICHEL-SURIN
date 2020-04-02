package world;

import tools.Coord;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * represents a board.
 */
public class Board {

    /**
     * the size of the board. Note that it's a squared board
     */
    private int size;
    /**
     * 2 dimensional array representing the cells of the board with associate coordinates
     */
    private Cell[][] cellArray;
    /**
     * list containing all the walls that have been placed
     * (each wall is an array of size 2 containing the origin and the second part of the wall)
     */
    private ArrayList<Coord[]> wallList;


    /**
     * initiate the board. Creating all the cell needed, all the directions, etc...
     *
     * @param size the size of the board. Note that it's a square.
     */
    public Board(int size) {

        this.size = size;
        wallList = new ArrayList<Coord[]>();
        cellArray = new Cell[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                Coord tempCoord = new Coord(i,j);
                cellArray[i][j] = new Cell(tempCoord, size);
            }
        }
    }

    /**
     * @return the 2D array containing all the cell of the board
     */
    public Cell[][] getCellArray() {
        return cellArray;
    }

    /**
     * @return the list containing all the wall that have been placed on the board
     */
    public ArrayList<Coord[]> getWallList() {
        return wallList;
    }

    /**
     *
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @param coord the coordinates of the cell we want
     * @return the cell at the given coordinates
     */
    public Cell getCell(Coord coord) {
        int y = coord.getY();
        int x = coord.getX();
        return cellArray[y][x];
    }
}
