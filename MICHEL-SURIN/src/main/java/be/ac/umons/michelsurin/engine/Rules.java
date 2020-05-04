package be.ac.umons.michelsurin.engine;

import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.items.Pawn;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;
import be.ac.umons.michelsurin.world.Cell;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * this class contains the main logic of the game as well as all the rules.
 *
 * @author Virgil SURIN
 */

public class Rules {

    /**
     * it's a coord code that marks the start.
     */
    private static final Coord startMark = new Coord(-42,-42);
    /**
     * it's a coord code that indicates that there are no path.
     */
    private static final Coord noPathMark = new Coord(-666,-666);
    /**
     * it's a coord code that marks the goal
     */
    private static final Coord goalMark = new Coord(-66,-66);

    /**
     * from the controller position, tells if it can move into the given direction.
     *
     * @param ctrl the controller, take is current position to check.
     * @param direction the direction we are checking with.
     * @return true if there is no obstacle from the controller coordinates to the given direction, false otherwise.
     */
    public static boolean canMove(PawnController ctrl, Coord direction) {
        Coord pawnCoord = ctrl.getDependency().getCoord();
        return !(ctrl.getBoard().getCell(pawnCoord).wallTo(direction)); //wallTo return true if there is a wall
    }

    /**
     * does the same as the 2 parameters canMove but
     * it checks from a given coordinates instead of the controller's coordinates.
     *
     * @param ctrl the controller, used to get the board in this case.
     * @param direction the direction we are checking with.
     * @param currentCoord the coordinates from which we are looking.
     * @return true if there is no obstacle from the currentCoord in the given direction.
     */
    public static boolean canMove(PawnController ctrl, Coord direction, Coord currentCoord) {
        return !(ctrl.getBoard().getCell(currentCoord).wallTo(direction));
    }


    /**
     * is a global verification combining {@link #validPlacement(Coord, Coord, Board)} and {@link #pathOrNot(PawnController)}.
     * It checks if the wall the controller wants to place does not get Out Of Bounds (OOB), does not cut or stacks up to
     * an existing wall or if it does not prevent a controller from finding a path to it's objective.
     *
     * @param playerArray array containing all the PawnController playing in the game.
     * @param ctrl the controller who wants to place a wall.
     * @param origin the coordinates where a controller wants to place a wall
     * @param direction the direction the controller wants to give to the wall.
     * @return true if the wall that the controller wants to place does not break any rules, false otherwise.
     */
    public static boolean canPlaceWall(PawnController[] playerArray, PawnController ctrl, Coord origin, Coord direction) {
        //if the wall stacks up on an other wall or is OOB, return false
        if ( !( validPlacement(origin, direction, ctrl.getBoard()) ) ) {
            return false;

        //the wall does not stacks up, cut or is OOB, checks if there is still a path for each player(controller)
        } else {
            // we need to simulate the placement of the wall and then check if it blocks the game.

            //first we create a copy of the board that and we add the supposed wall to it we already know it its spot is empty
            Board tempBoard = new Board(ctrl.getBoard().getSize(), ctrl.getBoard().getPawnCoord());
            for (Coord[] wall : ctrl.getBoard().getWallList()) {
                tempBoard.addToWallList(wall);
            }
            Coord[] testedWall = new Coord[2];
            testedWall[0] = origin;
            testedWall[1] = Coord.add(origin, direction);
            tempBoard.addToWallList(testedWall);

            //now that the board is copied and the wall is added, we check for every controller
            for (PawnController controller : playerArray) {
                //we create a copy of the actual be.ac.umons.michelsurin.controller (will be done for each controller in the game)
                //with the tempBoard instead of the real board
                PawnController tempCtrl = new PawnController(controller.getType(), (Pawn) controller.getDependency(), tempBoard, 55);

                //and now we can proceed to the path check
                if ( !(pathOrNot(tempCtrl)) ) {
                    return false;
                }
            }
            //if we get there, it means that the wall does not stacks up, get OOB or cut an existing wall
            //and that it does not block the way of any controller to its own objective
            //if we managed to get there, then the wall is valid.
            return true;
        }
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
                Coord nextCoord = Coord.add(coord, direction); //go to the cell coordinates above the originCell's coordinates

                //possible wall will be used to check if the wall we want to place is "cutting" an existing wall
                Cell secondCell = board.getCell(nextCoord);
                Coord[] possibleWall = new Coord[2];
                possibleWall[0] = coord; //the origin's coordinates
                Coord nextToOrigin = Coord.add(coord, Game.directions.get("RIGHT")); //the right cell's coordinates
                possibleWall[1] = nextToOrigin;
                Cell nextToOriginCell = board.getCell(nextToOrigin);

                //now the check
                if (originCell.wallTo(Game.directions.get("RIGHT"))
                        || secondCell.wallTo(Game.directions.get("RIGHT"))
                        || board.InWallList(possibleWall)) {
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
                Coord nextCoord = Coord.add(coord, direction); //go to the cell coordinates above the originCell's coordinates

                //possible wall will be used to check if the wall we want to place is "cutting" an existing wall
                Cell secondCell = board.getCell(nextCoord);
                Coord[] possibleWall = new Coord[2];
                possibleWall[0] = coord; //the origin's coordinates
                Coord tempCoord = new Coord(coord.getY()-1,coord.getX()); //the top cell's coordinates
                possibleWall[1] = tempCoord;

                Coord aboveOrigin = Coord.add(coord, Game.directions.get("UP"));
                Cell aboveOriginCell = board.getCell(aboveOrigin);
                //now the check
                if (originCell.wallTo(Game.directions.get("UP"))
                        || secondCell.wallTo(Game.directions.get("UP"))
                        || (board.InWallList(possibleWall))) {
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
        if (coord.getY() == ((Pawn) ctrl.getDependency()).getGoal()) { //downcast dependency from MOAI to Pawn then compare
            return true;
        } else {
            return false;
        }
    }

    /**
     * It searches a path to the goal row of the controller's dependency starting from it's current coordinates.
     * It starts to explore the surrounding of the controller's position in the board then it does the same for each
     * newly discovered position. The idea behind this algorithm is the breadth-first search
     *

     * @param ctrl the controller for which we seek a path to victory if there is one.
     * @return an Hashtable, if there is a path, it contains the startMark and the goalMark
     * and all the coordinates that have been explored. It needs to be translated into a path.
     * If there is no path, it's a one key one value Hashtable where the key is the noPathMark.
     * @version 1.3
     */
    public static Hashtable<Coord, Coord> findAPath(PawnController ctrl) {
        Coord startCoord = ctrl.getDependency().getCoord();

        Hashtable<Coord, Coord> exploredTable = new Hashtable<Coord, Coord>();
        exploredTable.put(startCoord, startMark); //used to know when we are at the start

        ArrayList<Coord> exploredCoord = new ArrayList<Coord>(); //contains all the coordinates we have already explored
        ArrayList<Coord> toBeExplored = new ArrayList<Coord>(); //contains all the coordinates we will explore
        toBeExplored.add(startCoord);
        int flag = 0;

        while(flag != 1) {
            flag = explore(exploredTable, exploredCoord, toBeExplored, ctrl);
            if (flag == 2) {
                Hashtable<Coord, Coord> noPathTable = new Hashtable<Coord, Coord>();
                noPathTable.put(noPathMark, startCoord);
                return noPathTable;
            }
        }
        return exploredTable;
    }

    /**
     * part of the breadth-first search, will explore all the surrounding positions and then repeat
     * Each newly discovered position is added at the beginning of the toBeExplored list. Exploration begins by the end
     * of this list.
     *
     * @param exploredTable the Hashtable containing all the explored positions.
     * @param exploredCoord contains all the coordinates that have been marked as explored.
     * @param toBeExplored the queue. All the positions that needs to be explored.
     * @param ctrl the controller.
     * @return 1 if a path to the objective has been found. 0 if a path to the objective hasn't been found yet.
     * 2 if every possible positions have been explored but none of the is the objective, in other words, there is no path.s
     */
    public static int explore(Hashtable<Coord, Coord> exploredTable,
                              ArrayList<Coord> exploredCoord,
                              ArrayList<Coord> toBeExplored,
                              PawnController ctrl) {

        Pawn pawnDependency = (Pawn) ctrl.getDependency();
        while(toBeExplored.size() > 0) {
            int lastObjectIndex = toBeExplored.size()-1;
            Coord current = toBeExplored.remove(lastObjectIndex);
            exploredCoord.add(current); //mark the coord as explored

            //initialize an array containing the four direction
            Coord[] directions = new Coord[4];
            directions[0] = new Coord(-1,0); //UP
            directions[1] = new Coord(0,-1); //LEFT
            directions[2] = new Coord(1,0); //DOWN
            directions[3] = new Coord(0,1); //RIGHT

            //now we explore its surrounding
            for (Coord dir : directions) {
                Coord nextCell = Coord.add(current, dir);
                if (canMove(ctrl, dir, current) && !contains(exploredCoord, nextCell)) {
                    if ( ( pawnDependency.doesGoalIsARow() && nextCell.getY() == pawnDependency.getGoal() ) ||
                            ( !pawnDependency.doesGoalIsARow() && nextCell.getX() == pawnDependency.getGoal())) {
                        //if the next cell is the goal we mark it
                        exploredTable.put(nextCell, current);
                        exploredTable.put(goalMark, nextCell);
                    } else {
                        exploredTable.put(nextCell, current);
                        toBeExplored.add(0, nextCell); //shift everything into the list.
                    }
                }
            }

            if (exploredTable.containsKey(goalMark)) {
                return 1; //there is a path to the goal and we found it.
            }
            /*
            else {
                return 0; //path not found yet.
            } */
        }
        return 2; //no more cells to explore and no path found. There is no path to victory.
    }

    //TODO change pathOrNot and path so we don't use findAPath twice

    /**
     * Interprets the Hashtable given by findAPath into a boolean result depending if there is a path from the
     * controller's position to its objective by checking the presence of goalMark or noPathMark.
     *
     * @param ctrl the controller.
     * @return true if there is a path from the controller's position to its objective, false otherwise.
     */
    public static boolean pathOrNot(PawnController ctrl) {
        Hashtable<Coord, Coord> exploredTable = findAPath(ctrl);
        if (exploredTable.containsKey(goalMark)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * this method translate the exploredTab of findAPath into a list containing the path to follow.
     * It starts at the goalMark and go from key to value until it gets at the startMark.
     *
     * @param ctrl the controller
     * @return a list containing the path to follow.
     */
    public static ArrayList<Coord> path(PawnController ctrl) {
        Hashtable<Coord, Coord> exploredTable = findAPath(ctrl);
        Coord key = goalMark; //at the beginning key = goalCoordMark
        ArrayList<Coord> path = new ArrayList<Coord>();

        while ( key.compareTo(startMark) != 0 ) {
            //System.out.println(key.toString());
            Coord value = exploredTable.get(key);
            path.add(value);
            key = value;
        }

        path.remove(path.size()-1); //remove the last Coord that is the startMark, we don't need it.
        path.remove(path.size()-1); //remove the last coord where the controller is, we don't need it.
        return path;
    }

    /**
     * method used to check if a Coord object is already inside an ArrayList or not.
     *
     * @param exploredCoord the ArrayList we are looking in.
     * @param c the Coord object whe are looking for in the list.
     * @return true if it is in the list.
     */
    public static boolean contains(ArrayList<Coord> exploredCoord, Coord c) {
        for (int i=0; i<exploredCoord.size(); i++) {
            if (exploredCoord.get(i).compareTo(c) == 0) {
                return true;
            }
        }
        return false;
    }

}
