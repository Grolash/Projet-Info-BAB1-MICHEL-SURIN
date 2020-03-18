from World import *
from engine import *

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


def findAPath(board, pawnCoord, objective):
    """
    board : the board in wich the algorithm will try to find a path
    pawnCoord : the starting point of the algorithm 
    objective : the targeted line. Basically the first line or the last line of the board depending
    on the player

    @return return the shortest path.
    """
    pass

def path(start, goal, controller, board) :
    """
    start : a tuple representing the starting coord
    goal : the targetted line.
    controller : the current player whom we need to find a path :)
    board : refers to the actual board where a path is needed to be find
    """
    toBeExplored = []
    explored = []
    toBeExplored.append(start)
    while len(toBeExplored) != 0 :
        current = toBeExplored.pop()
        explored.append(current)
        explore(current, toBeExplored, explored, goal, controller, board)
        if toBeExplored[-1] == None :
            return None
    return explored
    
def explore(start, toBeExplored, explored, goal, controller, board) :
    """
    the goal is to explore every cell around the start cell except
    if a surrounding cell is already in explored list, it's not considered as
    a need-to-explore.
    """
    startUP = add(start, board.UP)
    startLEFT = add(start, board.LEFT)
    startDOWN = add(start, board.DOWN)
    startRIGHT = add(start, board.RIGHT)
    blockedCounter = 0
    if canMove(controller, board.UP, start) and (startUP not in explored) and not (checkGoal(startUP, goal)) :
        toBeExplored.append(startUP)
    else :
        blockedCounter += 1
    if canMove(controller, board.LEFT, start) and (startLEFT not in explored) and not (checkGoal(startLEFT, goal)) :
        toBeExplored.append(startLEFT)
    else :
        blockedCounter += 1
    if canMove(controller, board.DOWN, start) and (startDOWN not in explored) and not (checkGoal(startDOWN, goal)) :
        toBeExplored.append(startDOWN)
    else :
        blockedCounter += 1
    if canMove(controller, board.RIGHT, start) and (startRIGHT not in explored) and not (checkGoal(startRIGHT, goal)) :
        toBeExplored.append(startRIGHT)
    else :
        blockedCounter += 1
    if blockedCounter == 4 :
        toBeExplored.append(None)
    

def checkGoal(current, goal) :
    """
    return True if the current coord tuple's is on the goal line
    """
    if current[0] == goal :
        return True
    else :
        return False



