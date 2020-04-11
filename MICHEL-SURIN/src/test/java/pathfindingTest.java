import controller.PawnController;
import engine.Rules;
import items.Pawn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.Coord;
import world.Board;

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


}
