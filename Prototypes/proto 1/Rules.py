from World import *
from engine import *

def findAPath():
    

def canMove(PawnController, direction):
    '''
    Boolean, return true if can move
    '''
    y, x = PawnController.dependency.coord
    if PawnController.board.playerCellList[y][x].wallTo(direction):
        return False
    return True

