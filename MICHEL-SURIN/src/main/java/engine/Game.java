package engine;

import controller.PawnController;
import items.Pawn;
import tools.Coord;
import world.Board;

import java.util.Hashtable;

public class Game {

    int playerNumber;
    PawnController[] playerArray;
    Pawn[] pawnArray;
    Board board;
    /**
     * contains all the possible directions (basically UP, DOWN, LEFT and RIGHT). Won't change.
     * Needs to be accessible for everyone
     */
    public static final Hashtable<String, Coord> directions = new Hashtable<>();

    /**
     * @param size the size of the board, should be 9
     * @param playerNumber the number of player, should be 2
     */
    public Game(int size, int playerNumber) {
            this.board = new Board(size);
            this.playerNumber = playerNumber;
            this.pawnArray = new Pawn[playerNumber];
            this.playerArray = new PawnController[playerNumber];

            Coord[] startCoordArray = new Coord[playerNumber];
            int[] goalRowArray = new int[playerNumber];
            if (playerNumber == 2) {
                //create the two basics starting coordinates for a 2 player game
                //and the associated goalRow
                startCoordArray[0] = new Coord(0,4);
                goalRowArray[0] = size-1;
                startCoordArray[1] = new Coord(size-1,4);
                goalRowArray[1] = 0;
            }
            else {
                if (playerNumber == 4) { //TODO add support for 4 player
                    //set up for 4 player
                    //not yet supported
                }
            }

            for (int i = 0; i < playerNumber; i++) {
                //TODO add support for AI vs AI/ Human vs AI / Human vs Human
                //TODO add parameters : can change numbWall
                //this for will add the player and their pawn to the game
                pawnArray[i] = new Pawn(startCoordArray[i], goalRowArray[i]);
                playerArray[i] = new PawnController(false,
                                                        pawnArray[i],
                                                        this.board,
                                            "player "+i); //default wall numb set = 10
            }

            directions.put("UP", new Coord(-1,0));
            directions.put("LEFT", new Coord(0,-1));
            directions.put("DOWN", new Coord(1,0));
            directions.put("RIGHT", new Coord(0,1));
        }

}
