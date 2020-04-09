import controller.PawnController;
import engine.Game;
import items.Pawn;
import tools.Coord;
import world.Board;

import java.util.ArrayList;

public class HelloWorld {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        Board board = new Board(9);
        PawnController ctrl = new PawnController(false, new Pawn(new Coord(0,4), 8), board, "TEST");
        ctrl.placeWall(new Coord(2,3), Game.directions.get("UP"));
        System.out.println(board.getWallList());
        ctrl.placeWall(new Coord(5,5), Game.directions.get("RIGHT"));
        System.out.println(board.getWallList());
        System.out.println("------------");
        for (Coord[] wall: board.getWallList()) {
            System.out.println(wall[0] + " " + wall[1]);
            System.out.print(board.getCell(wall[0]).wallTo(Game.directions.get("UP")));
            System.out.print(board.getCell(wall[0]).wallTo(Game.directions.get("RIGHT")));
            System.out.print(board.getCell(wall[0]).wallTo(Game.directions.get("DOWN")));
            System.out.print(board.getCell(wall[0]).wallTo(Game.directions.get("LEFT")));
            System.out.println("------------");


        }
        }
}

