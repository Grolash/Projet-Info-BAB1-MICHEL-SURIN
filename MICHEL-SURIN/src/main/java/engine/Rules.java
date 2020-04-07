package engine;

import controller.PawnController;
import items.Pawn;
import tools.Coord;
import world.Board;
import world.Cell;

import java.security.InvalidParameterException;

public class Rules {

    public static boolean canMove(PawnController ctrl, Coord direction) {
        Coord pawnCoord = ctrl.getDependency().getCoord();
        return !(ctrl.getBoard().getCell(pawnCoord).wallTo(direction)); //wallTo return true if there is a wall
    }


    public static boolean canPlaceWall(Coord origin, Coord direction, Board board) {
        return true; //TODO bind validPlacement AND IsaPath to get the value
    }

    /**
     * This method will check if the wall a controller wants to place does not get Out Of Bounds (OOB), if it does not
     * cut an already placed wall and if it does not stack up on an existing wall.
     *
     * @param coord the coordinates of the cell at which we want to place a wall.
     * @param direction the direction of the wall, used to build its second part.
     * @param board the board in which the game takes place.
     * @return true if the wall does not enter in collision with an existing wall, false otherwise.
     */
    public static boolean validPlacement(Coord coord, Coord direction, Board board) {
        Cell originCell = board.getCell(coord);
        if (direction == Game.directions.get("UP") ) {
            //checks if the wall will be Out Of Bounds (OOB)
            if (coord.getY() == 0) {
                return false;
            //checks potential collisions with existing walls
            } else {
                //nextCoord will be used to check if there is already a wall at the place we want to place a wall
                Coord nextCoord = new Coord(coord.getY(),coord.getX()); //copy the origin coordinates
                nextCoord.add(direction); //go to the cell coordinates above the originCell's coordinates

                //possible wall will be used to check if the wall we want to place is "cutting" an existing wall
                Cell secondCell = board.getCell(nextCoord);
                Coord[] possibleWall = new Coord[2];
                possibleWall[0] = coord; //the origin's coordinates
                Coord tempCoord = new Coord(coord.getY(),coord.getX()+1); //the right cell's coordinates
                possibleWall[1] = tempCoord;

                //now the check
                if (originCell.wallTo(Game.directions.get("RIGHT")) ||
                        secondCell.wallTo(Game.directions.get("RIGHT")) ||
                        (board.getWallList().contains(possibleWall))) {
                        return false;
                } else {
                    return true;
                }
            }
        } else if (direction == Game.directions.get("RIGHT")) {
            //checks OOB
            if (coord.getX() == board.getSize()-1) {
                return false;
            } else {
                //nextCoord will be used to check if there is already a wall at the place we want to place a wall
                Coord nextCoord = new Coord(coord.getY(),coord.getX()); //copy the origin coordinates
                nextCoord.add(direction); //go to the cell coordinates next to the originCell's coordinates

                //possible wall will be used to check if the wall we want to place is "cutting" an existing wall
                Cell secondCell = board.getCell(nextCoord);
                Coord[] possibleWall = new Coord[2];
                possibleWall[0] = coord; //the origin's coordinates
                Coord tempCoord = new Coord(coord.getY()-1,coord.getX()); //the top cell's coordinates
                possibleWall[1] = tempCoord;

                //now the check
                if (originCell.wallTo(Game.directions.get("UP")) ||
                        secondCell.wallTo(Game.directions.get("UP")) ||
                        (board.getWallList().contains(possibleWall))) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false; //will never happen

    }

    /**
     * checks if the coord full-fill the winning condition.
     *
     * @param coord the coordinates we're comparing to.
     * @param ctrl the controller we're looking at.
     * @return true if the coordinates correspond to the objective of the controller, false otherwise.
     */
    public static boolean hasWon(Coord coord, PawnController ctrl) {
        if (coord.getY() == ((Pawn) ctrl.getDependency()).getGoalRow()) { //downcast dependency from MOAI to Pawn then compare
            return true;
        } else {
            return false;
        }
    }
}
