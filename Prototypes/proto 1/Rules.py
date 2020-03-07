from World import *
from engine import *

def findAPath():
    pass

def canMove(PawnController, direction, coordinates = None):
    '''
    Boolean, return true if can move
    '''
    y, x = PawnController.dependency.coord
    if coordinates != None:
        y, x = coordinates
    if PawnController.board.playerCellList[y][x].wallTo(direction):
        return False
    return True

