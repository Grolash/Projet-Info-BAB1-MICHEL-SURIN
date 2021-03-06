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
import java.util.Random;

/**
 * a type of controller designed to control a Pawn instance. It is a player.
 */
public class PawnController extends Controller {

    public static final long serialVersionUID = 3234993869872838826L;
    private int playerNumber;
    private int numbWall;

    private Random random = new Random();
    private int smartedActionChangelog = random.nextInt(2);

    /**
     * @param type define the type of the controller. Used to determined it's action.
     * @param dependency refers to the items the controller is controlling.
     * @param board the board in which the controller is evolving.
     * @param playerNumber the number of the player.
     * @param numbWall the number of wall of the player. Default is 10.
     */
    public PawnController(String type, Pawn dependency, Board board, int playerNumber, int numbWall) {
        super(type, dependency, board);
        this.playerNumber = playerNumber;
        this.numbWall = numbWall;
    }

    /**
     * @param type define the type of the controller. Used to determined it's action.
     * @param dependency refers to the items the controller is controlling.
     * @param board the board in which the controller is evolving.
     * @param playerNumber the number of the player.
     */
    public PawnController(String type, Pawn dependency, Board board, int playerNumber) {
        super(type, dependency, board);
        this.playerNumber = playerNumber;
        this.numbWall = 10;
    }

    public int getNumbWall() {
        return numbWall;
    }

    public void addToNumbWall(int x) {
        numbWall += x;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }


    //NOTE ABOUT ACTIONS : THESE METHODS DO NOT TEST THE VALIDATION OF THE ACTION !
    //validation is handled by the Rules class and nothing else

    /**
     * Will move the controller's pawn into the given direction.
     * Does not care if there is a wall or not. It does it.
     * This kind of verification must be called before calling this method.
     *
     * @param direction the direction the controller is moving
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
     * Tells if the controller's dependency (typically a Pawn) has reached its objective (his goal row in a 2P game)
     *
     * @return true if the pawn is at is objective, false otherwise.
     */
    public boolean hasWon() {
        Pawn dep = (Pawn) getDependency(); //Downcast
        if (dep.getCoord().getY() == dep.getGoal() && dep.doesGoalIsARow()) {
            return true;
        } else if (dep.getCoord().getX() == dep.getGoal() && !dep.doesGoalIsARow()) {
            return true;
        } else {
            return false;
        }
    }

    public int getSmartedActionChangelog() {
        return smartedActionChangelog;
    }

    public void setSmartedActionChangelog(int smartedActionChangelog) {
        this.smartedActionChangelog = smartedActionChangelog;
    }
}
