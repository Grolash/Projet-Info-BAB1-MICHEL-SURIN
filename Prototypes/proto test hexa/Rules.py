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

def findAPath(board, pawnCoord, objective):
    """
    board : the board in wich the algorithm will try to find a path
    pawnCoord : the starting point of the algorithm 
    objective : the targeted line. Basically the first line or the last line of the board depending
    on the player
    """
    currentCoord = pawnCoord
    while currentCoord[0] != objective : #currentCoord[0] is the line
        pass



