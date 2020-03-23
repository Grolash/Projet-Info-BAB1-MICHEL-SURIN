from World import *
from engine import *
import copy

def canMove(PawnController, direction, coordinates = None):
    '''
    Boolean, return true if can move
    '''
    y, x = PawnController.dependency.coord
    if coordinates != None: #adopts the point of view of the <coordinates> cell
        y, x = coordinates
    if PawnController.board.playerCellList[y][x].wallTo(direction):
        return False
    return True

def canPlaceWall(coord, direction, game) :
    """
    this function checks if a wall can ba placed (not on a wall or second part out of the board etc)
    and checks if the placement of the wall does not make the game unsolvable for one of the player.

    coord = origin of the wall
    direction = direction for the second part (Horizontal/Vertical)
    game = the game actually playing. Used to access pawns and board
    """
    if validPlacement(coord, direction, game.board) :
        return True #if the wall is at a valid position (not out of border, or second part not into another wall) AND there is a path return True
    else :
        return False


def validPlacement(coord, direction, board) :
    """
    check if the placement of a wall enters in conflict with another wall
    """
    y, x = coord
    origin = board.playerCellList[y][x]
    if direction == board.RIGHT :
        secondCell = board.playerCellList[y][x+1] #get the cell on the right of the wall's origin. 

        if origin.hasWallUP or secondCell.hasWallUP :
            return False
        else :
            return True

    elif direction == board.UP :
        secondCell = board.playerCellList[y-1][x] #get the cell on the top of the wall's origin. 

        if origin.hasWallRIGHT or secondCell.hasWallRIGHT :
            return False
        else :
            return True

def isPath(coord, direction, game) :
    return True

def findAPath(game, playerController):
    """
    game : the game
    playerController : the controller which for we need to find a path
    """
    startingCoord = playerController.dependency.coord #the coord where the pawn is at the beginning of the algorithm
    linked_explored = {} # will link the cell between them. They key is the cell and it's value is the "previous" cell
    explored_cell = [] # list containing all the explored cell
    toBeExplored = [] #the list containing all the cells who's surrounding needs to be explored
    toBeExplored.append(startingCoord)
    flag = False
    while not flag :
        flag = explore(linked_explored, explored_cell, toBeExplored, playerController)
        print(linked_explored)
    return linked_explored

def explore(linked_explored, explored_cell, toBeExplored, playerController) : 
    """
    from a cell, will explore all the cell around a cell from toBeExplored (for each cell of this list), makes a check to see if the surrounding cells are not yet explored.
    at the end, put the newly explored cell in a list of explored cell and linked them to the cell they were explored from.
    """
    currentList = copy.deepcopy(toBeExplored)
    directions = {"UP" : (-1,0), "LEFT":(0,-1), "DOWN":(1,0), "RIGHT":(0,1)}
    while len(currentList) > 0 : #will do for all the cells who's surrounding needs to be explored, stop if the list is empty. :)
        """
        IDEA : why it won't stop ? Maybe because it wants to check cells trought walls !
        """
        current = currentList.pop() #withdrawn the last element of the list.
        toBeExplored.pop() #simply pop the last cell. Does not interfer with the while. New cells will be append later
        explored_cell.append(current) #mark the cell has explored
        #then we explore it
        block_counter = 0
        for j in directions : #will do for each direction
            nextCell = add(current, directions[j])
            if canMove(playerController, directions[j], current) and nextCell not in explored_cell :
                if checkGoal(nextCell, playerController) :
                    linked_explored[nextCell] = current
                    linked_explored["GOAL"] = nextCell #will allow to find the path
                else :
                    linked_explored[nextCell] = current
                    toBeExplored.append(nextCell) #next time we call this function, this list is updated
                    
        if "GOAL" not in linked_explored :
            return False
        else :
            return True
                


                    

def checkGoal(current, playerController) :
    """
    return True if the current coord tuple's is on the goal line
    """
    controller2 = copy.deepcopy(playerController)
    controller2.dependency.coord = current
    return controller2.hasWon()



