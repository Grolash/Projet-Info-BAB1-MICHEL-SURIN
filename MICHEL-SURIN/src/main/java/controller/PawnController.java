package controller;

import items.Pawn;
import world.Board;

public class PawnController extends Controller {

    private String playerName;
    private int numbWall;

    public PawnController(boolean AI, Pawn dependency, Board board, String playerName, int numbWall) {
        super(AI, dependency, board);
        this.playerName = playerName;
        this.numbWall = numbWall;
    }

    public PawnController(boolean AI, Pawn dependency, Board board, String playerName) {
        super(AI, dependency, board);
        this.playerName = playerName;
        this.numbWall = 10;
    }

}
