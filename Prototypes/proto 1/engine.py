from Items import *
from World import *

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
    -----------    -----------    -----------
    """

    def __init__(self, controllerType, dependency) :
        self.controller = controllerType
        self.dependency = dependency

    def whatItem(self):
        string = "This controller has for dependency an item of type: %s" % (self.dependency.name)
        return string

class PawnController(Controller) :
    """
    A controller designed to controll a Pawn.
    
    "dependency" should be a Pawn object.
    """
    def __init__(self, playerName, numbWall) :
        super().__init__()
        self.playerName = playerName
        self.stock = numbWall

    def move(self, direction) :
        if direction is not None :
            if not Board.playerCellList[self.dependency.coord[0], self.dependency.coord[1]].wallto(direction) :
                self.dependency.coord += direction

    def placeWall(self, coord) :
        if self.stock >= 0:
            self.stock -=1
            Wall(Board, coord, direction = None) #Board will be a specific board instead of a class.
