package engine;

import controller.Action;
import controller.PawnController;
import items.Pawn;
import tools.Coord;
import world.Board;

import java.util.Hashtable;

/**
 * @version v0.1
 *
 */
public class Game {

    private int playerNumber;
    private PawnController[] playerArray;
    private Pawn[] pawnArray;
    private Board board;
    private Hashtable<String, Integer> winTable;


    /**
     * contains all the possible directions (basically UP, DOWN, LEFT and RIGHT). Won't change.
     * Needs to be accessible for everyone
     */
    public static final Hashtable<String, Coord> directions = new Hashtable<String, Coord>();
    //the following lines initialize the 4 directions
    static {
        directions.put("UP", new Coord(-1,0));
        directions.put("LEFT", new Coord(0,-1));
        directions.put("DOWN", new Coord(1,0));
        directions.put("RIGHT", new Coord(0,1));
    }


    /**
     *
     * @param size the size of the board, should be 9.
     * @param playerNumber the number of player playing, must be 2 (or 4).
     * @throws IllegalArgumentException raise exception if an invalid number of player is used.
     */
    public Game(int size, int playerNumber) throws IllegalArgumentException {
            if (playerNumber != 2 /*|| playerNumber != 4*/) {
                throw new IllegalArgumentException("Invalid number of player. Should be 2");
            }

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

            this.board = new Board(size, startCoordArray);

            for (int i = 0; i < playerNumber; i++) {
                //TODO add support for AI vs AI/ Human vs AI / Human vs Human
                //TODO add parameters : can change numbWall
                //this for will add the player and their pawn to the game
                pawnArray[i] = new Pawn(startCoordArray[i], goalRowArray[i]);
                playerArray[i] = new PawnController("Human", pawnArray[i], this.board, i); //default wall numb set = 10
            }
            //run the GUI ?
        }

    public Game(String type1, String type2) {

        String[] typeArray = new String[2];
        typeArray[0] = type1;
        typeArray[1] = type2;



        this.playerNumber = 2;
        this.pawnArray = new Pawn[playerNumber];

        this.playerArray = new PawnController[playerNumber];


        Coord[] startCoordArray = new Coord[playerNumber];
        int[] goalRowArray = new int[playerNumber];

        //create the two basics starting coordinates for a 2 player game
        //and the associated goalRow
        startCoordArray[0] = new Coord(0,4);
        goalRowArray[0] = 8;
        startCoordArray[1] = new Coord(8,4);
        goalRowArray[1] = 0;

        this.board = new Board(9, startCoordArray);

        for (int i=0; i<playerNumber; i++) {
            pawnArray[i] = new Pawn(startCoordArray[i], goalRowArray[i]);


            playerArray[i] = new PawnController(typeArray[i], pawnArray[i], this.board, i);

        }

        //everything is set-up
        //we run one game


    }

    public void statLoop(Hashtable<String, Integer> winTable) {
        int i = 0;
        do {
            Action.getAction(playerArray, playerArray[i]);
            i++;
            i %= playerNumber;

        } while ( !(playerArray[i].hasWon()) );
        Integer numbOfVictory = winTable.get(playerArray[i].getType());
        numbOfVictory += 1;
        winTable.put(playerArray[i].getType(), numbOfVictory);
    }


    public PawnController[] getPlayerArray() {
        return playerArray;
    }
}
