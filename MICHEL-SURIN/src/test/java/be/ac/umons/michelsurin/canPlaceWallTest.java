package be.ac.umons.michelsurin;

import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.engine.Rules;
import be.ac.umons.michelsurin.items.Pawn;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;

public class canPlaceWallTest {

    @Test
    public void wallOverlap1() {
        //init
        Pawn p1 = new Pawn(new Coord(0,4),8, true);
        Pawn p2 = new Pawn(new Coord(8,4),0, true);
        Coord[] pawnCoord = new Coord[2];
        pawnCoord[0] = p1.getStart();
        pawnCoord[1] = p2.getStart();
        Board board = new Board(9, pawnCoord);

        Coord[] testingWall = new Coord[2];
        testingWall[0] = new Coord(2,2);
        testingWall[1] = new Coord(1,2);
        board.addToWallList(testingWall);

        //tests
        Assertions.assertTrue(Rules.validPlacement(new Coord(2,1), Game.directions.get("RIGHT"), board)); //a wall next to
        Assertions.assertTrue(Rules.validPlacement(new Coord(2,3), Game.directions.get("RIGHT"), board)); //a wall next to

        Assertions.assertTrue(Rules.validPlacement(new Coord(3,2), Game.directions.get("RIGHT"), board)); //a wall under
        Assertions.assertTrue(Rules.validPlacement(new Coord(1,2), Game.directions.get("RIGHT"), board)); //a wall above

        Assertions.assertFalse(Rules.validPlacement(new Coord(2,2), Game.directions.get("RIGHT"), board)); //cutting the existing wall

        Assertions.assertTrue(Rules.validPlacement(new Coord(2,1), Game.directions.get("UP"), board)); //left parallel
        Assertions.assertTrue(Rules.validPlacement(new Coord(2,3), Game.directions.get("UP"), board)); //right parallel
        Assertions.assertTrue(Rules.validPlacement(new Coord(4,2), Game.directions.get("UP"), board)); //same but under

        Assertions.assertFalse(Rules.validPlacement(new Coord(3,2), Game.directions.get("UP"), board)); //overlap 1
        Assertions.assertFalse(Rules.validPlacement(new Coord(2,2), Game.directions.get("UP"), board)); //overlap 2
        Assertions.assertFalse(Rules.validPlacement(new Coord(1,2), Game.directions.get("UP"), board)); //overlap 3
    }

    @Test
    public void isThereAPath() {
        //init
        Pawn p1 = new Pawn(new Coord(0,4),8, true);
        Coord[] pawnCoord = new Coord[2];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController ctrl = new PawnController("Human", p1, board, 0);
        PawnController[] playerArray = new PawnController[1];
        playerArray[0] = ctrl;

        Assertions.assertTrue(Rules.pathOrNot(ctrl)); //no wall, there is a path

        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(1,3);
        wall1[1] = new Coord(1,4);
        board.addToWallList(wall1);
        Assertions.assertTrue(Rules.pathOrNot(ctrl));

        Coord[] wall2 = new Coord[2];
        wall2[0] = new Coord(1,2);
        wall2[1] = new Coord(0,2);
        board.addToWallList(wall2);
        Assertions.assertTrue(Rules.pathOrNot(ctrl));

        Coord[] wall3 = new Coord[2];
        wall3[0] = new Coord(1,4);
        wall3[1] = new Coord(0,4);
        board.addToWallList(wall3);
        Assertions.assertFalse(Rules.pathOrNot(ctrl)); //no path

    }

    @Test
    public void isThereAPath2() {

        //init
        Pawn p1 = new Pawn(new Coord(0,4),8, true);
        Coord[] pawnCoord = new Coord[2];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController ctrl = new PawnController("Human", p1, board, 0);
        PawnController[] playerArray = new PawnController[1];
        playerArray[0] = ctrl;

        Assertions.assertTrue(Rules.pathOrNot(ctrl)); //no wall, there is a path

        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(8,7);
        wall1[1] = new Coord(8,8);
        board.addToWallList(wall1);
        Assertions.assertTrue(Rules.pathOrNot(ctrl));

        Coord[] wall2 = new Coord[2];
        wall2[0] = new Coord(8,5);
        wall2[1] = new Coord(8,6);
        board.addToWallList(wall2);
        Assertions.assertTrue(Rules.pathOrNot(ctrl));

        Coord[] wall3 = new Coord[2];
        wall3[0] = new Coord(8,3);
        wall3[1] = new Coord(8,4);
        board.addToWallList(wall3);
        Assertions.assertTrue(Rules.pathOrNot(ctrl));

        Coord[] wall4 = new Coord[2];
        wall4[0] = new Coord(8,1);
        wall4[1] = new Coord(8,2);
        board.addToWallList(wall4);
        Assertions.assertTrue(Rules.pathOrNot(ctrl));

        Coord[] wall5 = new Coord[2];
        wall5[0] = new Coord(8,0);
        wall5[1] = new Coord(7,0);
        board.addToWallList(wall5);
        Assertions.assertTrue(Rules.pathOrNot(ctrl));

        Coord[] wall6 = new Coord[2];
        wall6[0] = new Coord(7,0);
        wall6[1] = new Coord(7,1);
        board.addToWallList(wall6);
        Assertions.assertFalse(Rules.pathOrNot(ctrl));
    }

    @Test
    public void isThereAPath3() {

        //init
        Pawn p1 = new Pawn(new Coord(8,4),0, true);
        Coord[] pawnCoord = new Coord[2];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController ctrl = new PawnController("Human", p1, board, 0);
        PawnController[] playerArray = new PawnController[1];
        playerArray[0] = ctrl;

        Assertions.assertTrue(Rules.pathOrNot(ctrl)); //no wall, there is a path

        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(8,4);
        wall1[1] = new Coord(8,5);
        Coord dir1 = Game.directions.get("RIGHT");
        Assertions.assertTrue(Rules.canPlaceWall(playerArray, ctrl, wall1[0], dir1));
        board.addToWallList(wall1);

        Coord[] wall2 = new Coord[2];
        wall2[0] = new Coord(8,5);
        wall2[1] = new Coord(7,5);
        Coord dir2 = Game.directions.get("UP");
        Assertions.assertTrue(Rules.canPlaceWall(playerArray, ctrl, wall2[0], dir2));
        board.addToWallList(wall2);

        Coord[] wall3 = new Coord[2];
        wall3[0] = new Coord(8,3);
        wall3[1] = new Coord(7,3);
        Coord dir3 = Game.directions.get("UP");
        Assertions.assertFalse(Rules.canPlaceWall(playerArray, ctrl, wall3[0], dir3));
    }



    @Test
    public void canPlaceWall2P() {
        //init
        Pawn p1 = new Pawn(new Coord(0,4),8, true);
        Pawn p2 = new Pawn(new Coord(8,4),0, true);
        Coord[] pawnCoord = new Coord[2];
        pawnCoord[0] = p1.getStart();
        pawnCoord[1] = p2.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController ctrl1 = new PawnController("Human", p1, board, 0);
        PawnController ctrl2 = new PawnController("Human", p2, board, 1);
        PawnController[] playerArray = new PawnController[2];
        playerArray[0] = ctrl1;
        playerArray[1] = ctrl2;

        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(8,4);
        wall1[1] = new Coord(8,5);
        Coord dir1 = Game.directions.get("RIGHT");
        Assertions.assertTrue(Rules.canPlaceWall(playerArray, ctrl1, wall1[0], dir1));
        board.addToWallList(wall1);

        Coord[] wall2 = new Coord[2];
        wall2[0] = new Coord(8,5);
        wall2[1] = new Coord(7,5);
        Coord dir2 = Game.directions.get("UP");
        Assertions.assertTrue(Rules.canPlaceWall(playerArray, ctrl1, wall2[0], dir2));
        board.addToWallList(wall2);

        Coord[] wall3 = new Coord[2];
        wall3[0] = new Coord(8,3);
        wall3[1] = new Coord(7,3);
        Coord dir3 = Game.directions.get("UP");
        Assertions.assertFalse(Rules.canPlaceWall(playerArray, ctrl1, wall3[0], dir3));

        Coord[] wall4 = new Coord[2];
        wall4[0] = new Coord(8,2);
        wall4[1] = new Coord(8,3);
        Coord dir4 = Game.directions.get("RIGHT");
        Assertions.assertTrue(Rules.canPlaceWall(playerArray, ctrl1, wall4[0], dir4));
        board.addToWallList(wall4);

        Coord[] wall5 = new Coord[2];
        wall5[0] = new Coord(8,0);
        wall5[1] = new Coord(8,1);
        Coord dir5 = Game.directions.get("RIGHT");
        Assertions.assertFalse(Rules.canPlaceWall(playerArray, ctrl1, wall5[0], dir5));
    }

}
