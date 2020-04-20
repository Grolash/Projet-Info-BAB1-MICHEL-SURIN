package be.ac.umons.michelsurin.engine;

import be.ac.umons.michelsurin.controller.Action;
import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.items.Pawn;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;

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
     * @param playerTypeArray an array of string containing the types of the player (Human, Smart...).
     * @param numbOfWall the number of wall of each player.
     * @throws IllegalArgumentException raise exception if an invalid number of player is used.
     */
    public Game(int size, String[] playerTypeArray, int numbOfWall) throws IllegalArgumentException {

            this.playerNumber = playerTypeArray.length;
            if ( (playerNumber != 2) && (playerNumber != 4) ) {
                throw new IllegalArgumentException("Invalid number of player. Should be 2 or 4");
            }

            this.pawnArray = new Pawn[playerNumber];
            this.playerArray = new PawnController[playerNumber];

            Coord[] startCoordArray = new Coord[playerNumber];
            int[] goalArray = new int[playerNumber];

            if (playerNumber == 2) {
                //create the two basics starting coordinates for a 2 player game
                //and the associated goalRow
                startCoordArray[0] = new Coord(0,4);
                goalArray[0] = size-1;
                startCoordArray[1] = new Coord(size-1,4);
                goalArray[1] = 0;
            }
            else {
                if (playerNumber == 4) {
                    //set up for 4 player
                    //player 0
                    startCoordArray[0] = new Coord(0,4);
                    goalArray[0] = size-1;
                    //player 1
                    startCoordArray[1] = new Coord(size-1,4);
                    goalArray[1] = 0;
                    //player 2
                    startCoordArray[2] = new Coord(4,0);
                    goalArray[2] = size-1;
                    //player 3
                    startCoordArray[2] = new Coord(4,size-1);
                    goalArray[2] = 0;

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

    public void statLoop(Hashtable<String, Integer> winTable) {
        int i = 0;
        do {
            //System.out.println("---ACTION WILL BEGIN---" + playerArray[i].getType());
            Action.getAction(playerArray, playerArray[i]);
            //System.out.println("-----ACTION RESOLVED----"+ playerArray[i].getType());
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

    public Board getBoard() {
        return board;
    }
}
