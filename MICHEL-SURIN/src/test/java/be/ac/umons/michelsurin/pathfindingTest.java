package be.ac.umons.michelsurin;

import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Rules;
import be.ac.umons.michelsurin.items.Pawn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;

import java.util.ArrayList;

public class pathfindingTest {


    @Test
    public void directPath() {

        Pawn p1 = new Pawn(new Coord(0,4),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        ArrayList<Coord> pathToGoal = Rules.path(pc1);
        int pathLength = pathToGoal.size();
        Assertions.assertEquals(8, pathLength);
    }

    @Test
    public void indirectPath1() {

        //init
        Pawn p1 = new Pawn(new Coord(0,4),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);


        //set-up
        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(1,1);
        wall1[1] = new Coord(1,2);

        Coord[] wall2 = new Coord[2];
        wall2[0] = new Coord(1,3);
        wall2[1] = new Coord(1,4);

        Coord[] wall3 = new Coord[2];
        wall3[0] = new Coord(1,4);
        wall3[1] = new Coord(0,4);

        Coord[] wall4 = new Coord[2];
        wall4[0] = new Coord(7,0);
        wall4[1] = new Coord(7,1);

        board.addToWallList(wall1);
        board.addToWallList(wall2);
        board.addToWallList(wall3);
        board.addToWallList(wall4);

        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        ArrayList<Coord> pathToGoal = Rules.path(pc1);
        int pathLength = pathToGoal.size();
        Assertions.assertEquals(14, pathLength);
    }

    @Test
    public void twoSamSizePath() {
        //init
        Pawn p1 = new Pawn(new Coord(0,4),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);

        //set-up
        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(1,3);
        wall1[1] = new Coord(1,4);

        Coord[] wall2 = new Coord[2];
        wall2[0] = new Coord(2,4);
        wall2[1] = new Coord(2,5);

        board.addToWallList(wall1);
        board.addToWallList(wall2);

        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        ArrayList<Coord> pathToGoal = Rules.path(pc1);
        int pathLength = pathToGoal.size();
        Assertions.assertEquals(10, pathLength);
    }

    @Test
    public void oneStepToVictory() {
        //init
        Pawn p1 = new Pawn(new Coord(7,4),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);

        //test
        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        ArrayList<Coord> pathToGoal = Rules.path(pc1);
        int pathLength = pathToGoal.size();
        Assertions.assertEquals(1, pathLength);
    }

    @Test
    public void alreadyWon() {
        //init
        Pawn p1 = new Pawn(new Coord(8,4),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);

        //test
        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        ArrayList<Coord> pathToGoal = Rules.path(pc1); //should only contain a cell on left or right of the pawn.
        int pathLength = pathToGoal.size();
        Assertions.assertEquals(1, pathLength);
    }


}
