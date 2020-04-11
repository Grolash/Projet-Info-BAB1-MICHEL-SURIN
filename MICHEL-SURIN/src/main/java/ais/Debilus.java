package ais;

import controller.PawnController;
import engine.Game;
import engine.Rules;
import tools.Coord;
import world.Board;

import java.util.Random;


public class Debilus extends AI{

    public Debilus(Game game, Board board, PawnController pawnController) {
        super(game, board, pawnController);
    }

    /**
     * Most basic AI, mostly random due to it's purpose.
     */


    @Override
    public void act() {

        int action = getRandom().nextInt(1); //Choose randomly between move and placing a wall.

        if (action == 1){ //Tries and moves.
            Coord direction;
            int randint;

            do {
                randint = randomizeDirection();
                direction = getDirection(randint); //Choose a random direction
            }
            while (!(Rules.canMove(getPawnController(), direction))); //Does so until it can move.

            Coord forwardCell = Coord.add(getCurrentCoord(), direction); //Coordinates of the intended move cell

            if(getCurrentCell().hasPawn()){
                //If there is a pawn in "front" of itself, tries to bypass it.

                if(!(Rules.canMove(getPawnController(), direction, forwardCell))){
                    //If it can not go behind, tries to move diagonally (actually it makes moves forward then on the chosen side).
                    int tries = 0; //The number of tried moves.
                    Coord forwardForwardCell = Coord.add(forwardCell, direction);
                    //Coordinates of the would-be cell it will use to move "diagonally".
                    int choice = getRandom().nextInt(1); //choose random diagonal option.
                    Coord directionBis;

                    if (choice == 0){
                        //Choose the non-clockwise option.
                        int randintBis;
                        //Changes its direction accordingly taking in account the bounds of the array.
                        if (randint == 0)
                            randintBis = 3;
                        else
                            randintBis = randint - 1;
                        directionBis = getDirection(randintBis);
                        if (Rules.canMove(getPawnController(), directionBis, forwardForwardCell)){
                            getPawnController().move(direction); //moves on the same cell as the other pawn
                            getPawnController().move(direction); //moves on the cell behind the other pawn
                            getPawnController().move(directionBis); //moves to the side not to end in the wall
                        }
                        else{
                            if (tries == 0) {
                                //The chosen option (non-clockwise) was not possible, changes choice.
                                choice = 1;
                                tries += 1;
                            }
                            else action = 2;
                        }
                        //CASE IN WHICH THERE IS A PAWN IN FRONT AND A WALL BEHIND IT HALF-HANDLED.
                    }
                    else if (choice == 1){
                        //Choose the clockwise option
                        int randintBis;
                        //Changes its direction accordingly taking in account the bounds of the array.
                        randintBis = randint + 1;
                        if (randintBis == 4)
                            randintBis = 0;
                        directionBis = getDirection(randintBis);
                        if (Rules.canMove(getPawnController(), directionBis, forwardForwardCell)){
                            getPawnController().move(direction); //moves on the same cell as the other pawn
                            getPawnController().move(direction); //moves on the cell behind the other pawn
                            getPawnController().move(directionBis); //moves to the side not to end in the wall
                        }
                        else{
                            if (tries == 0) {
                                //The chosen option (non-clockwise) was not possible, changes choice.
                                choice = 0;
                                tries += 1;
                            }
                            else action = 2;
                        }
                        //CASE IN WHICH THERE IS A PAWN IN FRONT AND A WALL BEHIND HANDLED.
                    }
                }
                else{
                    //If it can go behind.
                    getPawnController().move(direction); //moves on the same cell as the other pawn
                    getPawnController().move(direction); //moves on the cell behind the other pawn
                }
            }
            else{
                //If there is no special condition like a pawn in front of itself
                getPawnController().move(direction);
            }

        }

        else if (action == 2){ //Tries and place a wall
            if (getPawnController().getNumbWall() > 0) {
                Coord placeCoord;
                Coord placeDir;
                do {
                    int ordinate = getRandom().nextInt(getBoard().getSize());
                    int absciss = getRandom().nextInt(getBoard().getSize());
                    placeCoord = new Coord(ordinate, absciss);

                    int intDir = getRandom().nextInt(1);
                    if (intDir == 1)
                        intDir = 3;
                    placeDir = getDirection(intDir);
                }
                while (!(Rules.canPlaceWall(getGame().getPlayerArray(), getPawnController(), placeCoord, placeDir)));

                getPawnController().placeWall(placeCoord, placeDir);
            }

            else
                act();

        }


    }
}