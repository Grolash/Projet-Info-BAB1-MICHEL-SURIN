import controller.PawnController;
import engine.Game;
import items.Pawn;
import tools.Coord;
import world.Board;

public class HelloWorld {

    public static void main(String[] args) {
        PawnController ctrl = new PawnController(false, new Pawn(new Coord(0,4),5),new Board(9),"John");
        System.out.println("Hello World!");
        Board board = ctrl.getBoard();
        Coord c1 = new Coord(2,5);
        Coord c2 = new Coord(5,2);
        ctrl.placeWall(c1, Game.directions.get("UP"));
        ctrl.placeWall(c2, Game.directions.get("RIGHT"));

        for (int i=0; i<board.getWallList().size(); i++) {
            for (Coord c : board.getWallList().get(i)) {
                System.out.print(c);
            }
            System.out.println();
        }

    }
}
