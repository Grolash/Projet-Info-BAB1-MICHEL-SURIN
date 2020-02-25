from Items import *
from World import *

"""
this file will contain object related to the game mechanics
and the playable-side of the game
"""

class Controller() :
    """
    represent an entity of the game that is able to act
    -controllerType = refers to what is controlling the controller (ex : Human, AI, ...) 
    -dependency = refers to what is the controller actually controlling (typically a MOAI subclass)
    """

    def __init__(self, controllerType, dependency) :
        self.controller = controllerType
        self.dependency = dependency

    class PawnController(Controller) :
        """
        A controller designed to controll a Pawn
        dependency should ba a Pawn object.
        """

        def __init__(self, playerName) :
            self.playerName = playerName


        def move(self, direction) :
            if direction is not None :
                if not Board.PlayerCellList[dependency.coord[0],dependency.coord[1]].wallto(direction) :
                    self.dependency.coord += direction
		
		def placeWall(self, coord) :
			pass
