from Items import *
from World import *
from Rules import *

"""
This file will contain objects related to the game mechanics and the playable-side of the game.
"""

class Controller() :
    """
    Represent an entity of the game that is able to act

    Attributes:
    -----------    -----------    -----------
    controllerType :
         refers to what is controlling the controller (ex : Human, AI, ...).
    dependency :
         refers to what is the controller actually controlling (typically a MOAI subclass).
    board :
         refers to the board to controller is interacting to
    -----------    -----------    -----------
    """

    def __init__(self, controllerType, dependency, board) :
        self.controller = controllerType
        self.dependency = dependency
        self.board = board

    def whatItem(self):
        string = "This controller has for dependency an item of type: %s" % (self.dependency.name)
        return string

class PawnController(Controller) :
    """
    A controller designed to controll a Pawn.
    
    "dependency" should be a Pawn object.
    """
    def __init__(self, controllerType, dependency, board, playerName, numbWall) :
        super().__init__(controllerType, dependency, board)
        self.playerName = playerName
        self.stock = numbWall

    def move(self, direction) :
        if direction is not None :
            y = self.dependency.coord[0]
            x = self.dependency.coord[1]
            if canMove(self, direction):
                self.dependency.coord += direction

    def placeWall(self, coord, direction) :
        if self.stock >= 0:
            self.stock -= 1
            Wall(coord, direction)

    def hasWon(self) :
        """
        first check the initial spawn of the player
        then return true if the player has reached to opposite of the board
        """
        if self.dependency.start[0] == self.board.playerCellList[0] : #if the player starts at the top
            if self.dependency.coord[0] == self.board.playerCellList[-1] : #if he reaches the bottom
                return True
            else :
                return False
        elif self.dependency.start[0] == self.board.playerCellList[-1] : #if the player starts at the bottom
            if self.dependency.coord[0] == self.board.playerCellList[0] :  #if he reaches the top
                return True
            else :
                return False