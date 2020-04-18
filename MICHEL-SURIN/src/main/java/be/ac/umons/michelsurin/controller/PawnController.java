/**
 * package containing the classes of objects that are able to interact in the game.
 */
package be.ac.umons.michelsurin.controller;

import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.items.Pawn;
import be.ac.umons.michelsurin.items.Wall;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;

import java.security.InvalidParameterException;

/**
 * a type of be.ac.umons.michelsurin.controller designed to control a Pawn instance. It is a player.
 */
public class PawnController extends Controller {

    private int playerNumber;
    private int numbWall;

    /**
     * @param type define the type of the be.ac.umons.michelsurin.controller. Used to determined it's action.
     * @param dependency refers to the be.ac.umons.michelsurin.items the be.ac.umons.michelsurin.controller is controlling.
     * @param board the board in which the be.ac.umons.michelsurin.controller is evolving.
     * @param playerNumber the number of the player.
     * @param numbWall the number of wall of the player. Default is 10.
     */
    public PawnController(String type, Pawn dependency, Board board, int playerNumber, int numbWall) {
        super(type, dependency, board);
        this.playerNumber = playerNumber;
        this.numbWall = numbWall;
    }

    /**
     * @param type define the type of the be.ac.umons.michelsurin.controller. Used to determined it's action.
     * @param dependency refers to the be.ac.umons.michelsurin.items the be.ac.umons.michelsurin.controller is controlling.
     * @param board the board in which the be.ac.umons.michelsurin.controller is evolving.
     * @param playerNumber the number of the player.
     */
    public PawnController(String type, Pawn dependency, Board board, int playerNumber) {
        super(type, dependency, board);
        this.playerNumber = playerNumber;
        this.numbWall = 10;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getNumbWall() {
        return numbWall;
    }

    public void addToNumbWall(int x) {
        numbWall += x;
    }


    //NOTE ABOUT ACTIONS : THESE METHODS DO NOT TEST THE VALIDATION OF THE ACTION !
    //validation is handled by the Rules class and nothing else

    /**
     * Will move the be.ac.umons.michelsurin.controller's pawn into the given direction.
     * Does not care if there is a wall or not. It does it.
     * This kind of verification must be called before calling this method.
     *
     * @param direction the direction the be.ac.umons.michelsurin.controller is moving
     */
    public void move(Coord direction) {
        Coord depCoord = getDependency().getCoord();
        depCoord = Coord.add(depCoord, direction);
        getDependency().setCoord(depCoord);
        getBoard().movePawnCoord(playerNumber, depCoord);
    }

    /**
     * given an coordinate (origin) and a direction (UP or RIGHT), place a wall and register it in the board.
     *
     * @param origin the origin of the wall
     * @param direction the direction of the wall used to create it's second part (UP or RIGHT)
     * @throws InvalidParameterException is thrown if the direction is invalid (not UP or RIGHT)
     */
    public void placeWall(Coord origin, Coord direction) throws InvalidParameterException {
        numbWall -= 1;
        if (direction == Game.directions.get("UP")) {
            Coord[] fullWallCoord = new Coord[2];
            fullWallCoord[0] = origin;
            fullWallCoord[1] = Coord.add(origin, direction);

            Board board = getBoard();
            board.addToWallList(fullWallCoord);
            Wall wall = new Wall(origin, direction);

        } else if (direction == Game.directions.get("RIGHT")) {
            Coord[] fullWallCoord = new Coord[2];
            fullWallCoord[0] = origin;
            fullWallCoord[1] = Coord.add(origin, direction);

            Board board = getBoard();
            board.addToWallList(fullWallCoord);
            Wall wall = new Wall(origin, direction);

        } else
            throw new InvalidParameterException("Misuse direction, should be UP or RIGHT");
    }

    /**
     * Tells if the be.ac.umons.michelsurin.controller's dependency (typically a Pawn) has reached its objective (his goal row in a 2P game)
     *
     * @return true if the pawn is at is objective, false otherwise.
     */
    public boolean hasWon() {
        Pawn dep = (Pawn) getDependency(); //Downcast
        if (dep.getCoord().getY() == dep.getGoal()) {
            return true;
        } else {
            return false;
        }
    }



}