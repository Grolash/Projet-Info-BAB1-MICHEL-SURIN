from Items import *
from World import *
from Rules import *
from add import *

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
        """
        The move method test if there is a pawn in the desired direction.
        If there is no pawn, it does a normal move if possible.
        If there is one, it makes (if possible) an automatic move to a legal position, diagonally, with a priority to the right of the pawn.
        """
        y, x = self.dependency.coord 
        if direction is not None:
            if canMove(self, direction): #checks if there is no wall between the pawn and the next cell
                nextCoord = add(self.dependency.coord, direction) #coordinates of forward cell to move, used to check canMove and make a move if there is no pawn

                if self.board.playerCellList[y][x].pawnTo(direction):

                    if canMove(self, direction, nextCoord): #checks if the played pawn can bypass the other pawn
                        superMoveCoord = add(nextCoord, direction) #coorinates of cell after the pawn, used to make a "super" move one more cell than normal
                        self.dependency.coord = superMoveCoord

                    else: #checks if the played pawn can otherwise move to a diagonal direction (through the other pawn cell), one direction at a time
                        if direction is self.board.UP :
                            if canMove(self, self.board.RIGHT, nextCoord):
                                self.dependency.coord = add(self.dependency.coord, direction)
                                self.dependency.coord = add(self.dependency.coord, self.board.RIGHT)
                            elif canMove(self, self.board.LEFT, nextCoord):
                                self.dependency.coord = add(self.dependency.coord, direction)
                                self.dependency.coord = add(self.dependency.coord, self.board.LEFT)

                        elif direction is self.board.RIGHT :
                            if canMove(self, self.board.UP, nextCoord):
                                self.dependency.coord = add(self.dependency.coord, direction)
                                self.dependency.coord = add(self.dependency.coord, self.board.DOWN)
                            elif canMove(self, self.board.DOWN, nextCoord):
                                self.dependency.coord = add(self.dependency.coord, direction)
                                self.dependency.coord = add(self.dependency.coord, self.board.UP)

                        elif direction is self.board.DOWN :
                            if canMove(self, self.board.LEFT, nextCoord):
                                self.dependency.coord = add(self.dependency.coord, direction)
                                self.dependency.coord = add(self.dependency.coord, self.board.LEFT)
                            elif canMove(self, self.board.RIGHT, nextCoord):
                                self.dependency.coord = add(self.dependency.coord, direction)
                                self.dependency.coord = add(self.dependency.coord, self.board.RIGHT)

                        elif direction is self.board.LEFT :
                            if canMove(self, self.board.UP, nextCoord):
                                self.dependency.coord = add(self.dependency.coord, direction)
                                self.dependency.coord = add(self.dependency.coord, self.board.UP)
                            elif canMove(self, self.board.DOWN, nextCoord):
                                self.dependency.coord = add(self.dependency.coord, direction)
                                self.dependency.coord = add(self.dependency.coord, self.board.DOWN)
                    
                else: #if there is no pawn, just make a normal move ;3
                    self.dependency.coord = nextCoord

    def placeWall(self, coord, direction) :
        if self.stock >= 0:
            self.stock -= 1
            newWall = Wall(coord, direction)

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