package ais;

import controller.PawnController;
import engine.Game;
import tools.Coord;
import world.Board;
import world.Cell;

import java.util.Random;

public abstract class AI {
    /**
     * Basic template for an AI.
     */

    private Game game;
    private Board board;
    private PawnController pawnController;
    private Coord currentCoord = pawnController.getDependency().getCoord();
    private Cell currentCell = board.getCell(currentCoord);

    private Random random = new Random();

    public AI(Game game, Board board, PawnController pawnController){
        this.board = board;
        this.pawnController = pawnController;
        this.game = game;

    }

    public Game getGame() {
        return game;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PawnController getPawnController() {
        return pawnController;
    }

    public void setPawnController(PawnController pawnController) {
        this.pawnController = pawnController;
    }

    public Coord getCurrentCoord() {
        return currentCoord;
    }

    public void setCurrentCoord(Coord currentCoord) {
        this.currentCoord = currentCoord;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Use a integer between 0 and 3 (both included) to permit a random choice via a random integer.
     * @param i the integer mentioned above.
     * @return a direction from Game.direction
     * @throws IllegalArgumentException in case the method does not receive an integer between 0 and 3 both included.
     */
    protected Coord getDirection(int i) throws IllegalArgumentException {
        switch (i){
            case 0:
                return Game.directions.get("UP");

            case 1:
                return Game.directions.get("LEFT");

            case 2:
                return Game.directions.get("DOWN");

            case 3:
                return Game.directions.get("RIGHT");

            default:
                throw new IllegalArgumentException("Did not receive 0, 1, 2 or 3 as integer.");

        }
    }

    /**
     * Use the Random module to generate a random integer (see usage in getDirection() method).
     * @return an integer between 0 and 3 both included.
     */
    protected int randomizeDirection(){
        float randfloat = random.nextInt(4);
        return Math.round(randfloat);

    }

    public void act(){}

}
