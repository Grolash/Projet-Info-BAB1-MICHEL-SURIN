package controller;

import engine.Game;
import items.MOAI;
import items.Pawn;
import items.Wall;
import tools.Coord;
import world.Board;

import java.security.InvalidParameterException;

public class PawnController extends Controller {

    private String playerName;
    private int numbWall;

    /**
     * @param AI true : controlled by an AI, false for a human.
     * @param dependency refers to the items the controller is controlling.
     * @param board the board in which the controller is evolving.
     * @param playerName the name of the player.
     * @param numbWall the number of wall of the player. Default is 10.
     */
    public PawnController(boolean AI, Pawn dependency, Board board, String playerName, int numbWall) {
        super(AI, dependency, board);
        this.playerName = playerName;
        this.numbWall = numbWall;
    }

    /**
     * @param AI true : controlled by an AI, false for a human.
     * @param dependency refers to the items the controller is controlling.
     * @param board the board in which the controller is evolving.
     * @param playerName the name of the player.
     */
    public PawnController(boolean AI, Pawn dependency, Board board, String playerName) {
        super(AI, dependency, board);
        this.playerName = playerName;
        this.numbWall = 10;
    }

    //NOTE ABOUT ACTIONS : THESE METHODS DO NOT TEST THE VALIDATION OF THE ACTION !
    //validation is handled by the Rules class and nothing else

    public void move(Coord direction) {
        Coord depCoord = getDependency().getCoord();
        depCoord.add(direction);
    }

    public void placeWall(Coord origin, Coord direction) throws InvalidParameterException {

        if (direction == Game.directions.get("UP")) {
            numbWall -= 1;
            Coord[] fullWallCoord = new Coord[2];
            fullWallCoord[1] = origin;

            int y = origin.getY();
            int x = origin.getX();
            Coord secondPart = new Coord(y, x) ;
            fullWallCoord[0] = secondPart;

            Board board = getBoard();
            board.addToWallList(fullWallCoord);
            Wall wall = new Wall(origin, direction);

        } else if (direction == Game.directions.get("RIGHT")) {
            numbWall -= 1;
            Coord[] fullWallCoord = new Coord[2];
            fullWallCoord[1] = origin;

            int y = origin.getY();
            int x = origin.getX();
            Coord secondPart = new Coord(y, x) ;
            fullWallCoord[0] = secondPart;

            Board board = getBoard();
            board.addToWallList(fullWallCoord);
            Wall wall = new Wall(origin, direction);

        } else
            throw new InvalidParameterException("Misuse direction, should ba UP or RIGHT");


    }

}
