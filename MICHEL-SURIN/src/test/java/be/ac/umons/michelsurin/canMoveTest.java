package be.ac.umons.michelsurin;

import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.engine.Rules;
import be.ac.umons.michelsurin.items.Pawn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;

public class canMoveTest {

    @Test
    public void canMoveCornerLU() {

        //set-up
        Pawn p1 = new Pawn(new Coord(0,0),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        //tests
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("UP")));
        Assertions.assertTrue(Rules.canMove(pc1, Game.directions.get("RIGHT")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("LEFT")));
        Assertions.assertTrue(Rules.canMove(pc1, Game.directions.get("DOWN")));

        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(1,1);
        wall1[1] = new Coord(1,2);
    }

    @Test
    public void canMoveCornerRU() {

        //set-up
        Pawn p1 = new Pawn(new Coord(0,8),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        //tests
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("UP")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("RIGHT")));
        Assertions.assertTrue(Rules.canMove(pc1, Game.directions.get("LEFT")));
        Assertions.assertTrue(Rules.canMove(pc1, Game.directions.get("DOWN")));

    }

    @Test
    public void canMoveCornerRD() {

        //set-up
        Pawn p1 = new Pawn(new Coord(8,8),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        //tests
        Assertions.assertTrue(Rules.canMove(pc1, Game.directions.get("UP")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("RIGHT")));
        Assertions.assertTrue(Rules.canMove(pc1, Game.directions.get("LEFT")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("DOWN")));

    }

    @Test
    public void canMoveCornerLD() {

        //set-up
        Pawn p1 = new Pawn(new Coord(8,0),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController pc1 = new PawnController("Human", p1, board, 0,10);
        //tests
        Assertions.assertTrue(Rules.canMove(pc1, Game.directions.get("UP")));
        Assertions.assertTrue(Rules.canMove(pc1, Game.directions.get("RIGHT")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("LEFT")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("DOWN")));

    }

    @Test
    public void cantMove() {

        //set-up
        Pawn p1 = new Pawn(new Coord(4,4),8);
        Coord[] pawnCoord = new Coord[1];
        pawnCoord[0] = p1.getStart();
        Board board = new Board(9, pawnCoord);
        PawnController pc1 = new PawnController("Human", p1, board, 0,10);

        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(5,3);
        wall1[1] = new Coord(5,4);

        Coord[] wall2 = new Coord[2];
        wall2[0] = new Coord(5,4);
        wall2[1] = new Coord(4,4);

        Coord[] wall3 = new Coord[2];
        wall3[0] = new Coord(4,3);
        wall3[1] = new Coord(3,3);

        Coord[] wall4 = new Coord[2];
        wall4[0] = new Coord(4,4);
        wall4[1] = new Coord(4,5);

        board.addToWallList(wall1);
        board.addToWallList(wall2);
        board.addToWallList(wall3);
        board.addToWallList(wall4);

        //tests
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("UP")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("RIGHT")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("LEFT")));
        Assertions.assertFalse(Rules.canMove(pc1, Game.directions.get("DOWN")));
    }
}
