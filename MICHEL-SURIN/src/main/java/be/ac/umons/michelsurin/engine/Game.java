package be.ac.umons.michelsurin.engine;

import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.items.Pawn;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.tools.CpuTime;
import be.ac.umons.michelsurin.world.Board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * @version v1.0
 *
 */
public class Game implements Serializable {

    public static final long serialVersionUID = -7739854031988438014L;

    /**
     * number of player in the current game, is 2 or 4
     */
    private int playerNumber;
    /**
     * the index of the player that needs to take action
     */
    private int currentPlayer;
    /**
     * Array containing all the PawnController in the game, one per pawn
     */
    private PawnController[] playerArray;
    /**
     * Array containing all the Pawn in the game, one per player
     */
    private Pawn[] pawnArray;
    /**
     * the board of the actual game
     */
    private Board board;

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
     * @param playerTypeArray an array of string containing the types of the player (Human, Smart...).
     * @param numbOfWall the number of wall of each player.
     * @param currentPlayer the index of the player that needs to take action.
     * @throws IllegalArgumentException raise exception if an invalid number of player is used.
     */
    public Game(int size, String[] playerTypeArray, int numbOfWall, int currentPlayer) throws IllegalArgumentException {
            this.currentPlayer = currentPlayer;
            this.playerNumber = playerTypeArray.length;
            if ( (playerNumber != 2) && (playerNumber != 4) ) {
                throw new IllegalArgumentException("Invalid number of player. Should be 2 or 4");
            }

            this.pawnArray = new Pawn[playerNumber];
            this.playerArray = new PawnController[playerNumber];

            Coord[] startCoordArray = new Coord[playerNumber];
            int[] goalArray = new int[playerNumber];

            int half = size/2;

            if (playerNumber == 2) {
                //create the two basics starting coordinates for a 2 player game
                //and the associated goalRow
                startCoordArray[0] = new Coord(0, half);
                goalArray[0] = size-1;
                startCoordArray[1] = new Coord(size-1, half);
                goalArray[1] = 0;
            }
            else {
                if (playerNumber == 4) {
                    //set up for 4 player
                    //player 0
                    startCoordArray[0] = new Coord(0, half);
                    goalArray[0] = size-1;
                    //player 1
                    startCoordArray[1] = new Coord(size-1, half);
                    goalArray[1] = 0;
                    //player 2
                    startCoordArray[2] = new Coord(half,0);
                    goalArray[2] = size-1;
                    //player 3
                    startCoordArray[3] = new Coord(half,size-1);
                    goalArray[3] = 0;

                }
            }

            this.board = new Board(size, startCoordArray);

            for (int i = 0; i < playerNumber; i++) {
                //this for will add the player and their pawn to the game
                boolean row = (i==2 || i==3) ? false : true;
                pawnArray[i] = new Pawn(startCoordArray[i], goalArray[i], row);
                playerArray[i] = new PawnController(playerTypeArray[i], pawnArray[i], this.board, i, numbOfWall);
            }
            //run the GUI ?
        }

    /**
     * Game constructor used by the StatRunner. It can only create 1v1 on a 9x9 board with 10 walls per player.
     * It is used to simulate games.
     * @param type1 type of the first AI
     * @param type2 type of the second AI
     */
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
            pawnArray[i] = new Pawn(startCoordArray[i], goalRowArray[i], true);


            playerArray[i] = new PawnController(typeArray[i], pawnArray[i], this.board, i);

        }

        //everything is set-up
        //we run one game


    }

    /**
     * It is a loop that simulate a single game. The wintable is given by StatRunner to keep track of
     * win statistics.
     *
     * @param winTable an Hashtable containing each type with their number of won game.
     */
    public void statLoop(Hashtable<String, Integer> winTable, Hashtable<String, Long> timeTable) {
        int i = 0;
        do {
            CpuTime time = new CpuTime();
            time.start();
            Action.getAction(playerArray, playerArray[i]);
            time.stop();
            //add time to the AI
            timeTable.put(playerArray[i].getType(), timeTable.get(playerArray[i].getType())+ time.getMilliSeconds());
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

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard() {
        return board;
    }



}
