package be.ac.umons.michelsurin.world;

import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.tools.Coord;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * represents a board.
 */
public class Board implements Serializable {

    public static final long serialVersionUID = 2546573719101753L;

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
    private Coord[] pawnCoord;


    /**
     * initiate the board. Creating all the cell needed, an empty wall list and adding all the pawns.
     *
     * @param size the size of the board. Note that it's a square.
     */
    public Board(int size, Coord[] pawnCoord) {

        this.pawnCoord = pawnCoord;
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
     * @return the list containing all the wall that have been placed on the board
     */
    public ArrayList<Coord[]> getWallList() {
        return wallList;
    }

    public Coord[] getPawnCoord() {
        return pawnCoord;
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
        //System.out.println("y: "+ y + " | " + "x: "+ x);
        return cellArray[y][x];
    }

    /**
     * add the wall to the wall list AND update cells that are around the wall.
     *
     * @param coordOfWall an array containing the coordinates of the first part of the wall
     *                   and the coordinates of the following parts (length of 2 in a standard game)
     */
    public void addToWallList(Coord[] coordOfWall) {
        wallList.add(coordOfWall);
        Coord wallOrigin = coordOfWall[0];
        Coord wallSecondPart = coordOfWall[1];
        if (wallOrigin.getY() == wallSecondPart.getY() && wallOrigin.getX() == wallSecondPart.getX()-1) {
            //wall is on the UP side of the cells
            getCell(wallOrigin).setWallOnSide("UP");
            getCell(wallSecondPart).setWallOnSide("UP");
            //wall on the DOWN side of the cells
            Coord UpOriginCell = Coord.add(wallOrigin, Game.directions.get("UP"));
            Coord UpSecondCell = Coord.add(wallSecondPart, Game.directions.get("UP"));
            getCell(UpOriginCell).setWallOnSide("DOWN");
            getCell(UpSecondCell).setWallOnSide("DOWN");

        } else if (wallOrigin.getX() == wallSecondPart.getX() && wallOrigin.getY() == wallSecondPart.getY()+1) {
            //wall is on the RIGHT side of the cells
            getCell(wallOrigin).setWallOnSide("RIGHT");
            getCell(wallSecondPart).setWallOnSide("RIGHT");
            //wall is on the LEFT side of the cells
            Coord UpOriginCell = Coord.add(wallOrigin, Game.directions.get("RIGHT"));
            Coord UpSecondCell = Coord.add(wallSecondPart, Game.directions.get("RIGHT"));
            getCell(UpOriginCell).setWallOnSide("LEFT");
            getCell(UpSecondCell).setWallOnSide("LEFT");

        }

    }

    /**
     * Moves the pawn on the board and actualizes pawn states of cells.
     *
     * @param playerNumber the number of the player, used to get the correct pawn
     *                     (if there are 4 player, player numbers are 0,1,2,3)
     * @param newCoord the new coordinates of the pawn
     */
    public void movePawnCoord(int playerNumber, Coord newCoord) {
        Coord oldCoord = pawnCoord[playerNumber];
        getCell(oldCoord).setPawn(false);
        pawnCoord[playerNumber] = newCoord;
        getCell(newCoord).setPawn(true);
    }

    /**
     * Checks if a wall is already in the wall list or not.
     *
     * @param wall the wall we are looking for in the list
     * @return true if the wall is already in the list, false otherwise.
     */
    public boolean InWallList(Coord[] wall) {
        for (Coord[] c : getWallList()) {
            if (c[0].compareTo(wall[0]) == 0 && c[1].compareTo(wall[1]) == 0) {
                return true;
            }
        }
        return false;
    }

    public Cell[][] getCellArray() {
        return cellArray;
    }
}
