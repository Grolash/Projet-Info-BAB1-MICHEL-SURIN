from World import *
from Items import *
from engine import *

class Game() :

    def __init__(self, playerNumber, boardSize) :
        self.board = Board(boardSize)
        self.pawnList = []
        self.playerList = []
        for i in range(1,playerNumber+1) : # 1, 2, 3, etc...
            # create a Pawn named "Pawn + i"
            pawnName = "Pawn" + str(i)
            self.pawnList.append(Pawn(pawnName))
            # create a Controller named "Player + i" and bind the Controller to the Pawn
            pawnIndex = self.pawnList[i-1]
            playerName = "Player" + str(i)
            newController = Controller.PawnController("Human", pawnIndex, playerName, 10)
            self.playerList.append(newController)
            self.refresh()
    
    def __str__(self) :
        return self.board.__str__()

    def refresh(self) :
        """
        Takes a game as argument and refresh all pawns positions
        """
        self.board.reset()
        for pawn in self.pawnList :
            x = pawn.coord[1]
            y = pawn.coord[0]
            self.board.playerCellList[y][x].hasPawn = True
        

if __name__ == '__main__':
    newGame = Game(1,9)
    print(newGame, end="") 
    print("pawn coord ", end="")
    print(newGame.pawnList[0].coord)
