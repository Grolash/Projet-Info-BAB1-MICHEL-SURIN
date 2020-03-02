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
            newController = PawnController("Human", pawnIndex, self.board, playerName, 10)
            self.playerList.append(newController)
            self.refresh()
    
    def __str__(self) :
        return self.board.__str__()

    def refresh(self) :
        """
        Takes a game as argument and refresh all pawns positions
        """
        self.board.reset()
        for player in self.playerList :
            x = player.depedency.coord[1]
            y = player.depedency.coord[0]
            self.board.playerCellList[y][x].hasPawn = True

    def checkWin(self) :
        """
        check if a pawn has reached the opposite of the board
        """
        for player in self.playerList :
            return player.hasWon()
        
    def gameLoop(self) :
        i = 0
        while not checkWin() :
            if i >= 2 :
                i = 0
            print("player " + str(i+1) + " pick-up an action :")
            print("1) move")
            print("2) place a Wall")
            choice = int(input("your choice : "))
            if choice == 1 :
                print("choose a direction :")
                print("1) UP")
                print("2) RIGHT")
                print("3) DOWN")
                print("4) LEFT")
                direction = int(input("direction : "))
                if direction == 1 :
                    self.playerList[i].move(self.board.UP)
                elif direction == 2 :
                    self.playerList[i].move(self.board.RIGHT)
                elif direction == 3 :
                    self.playerList[i].move(self.board.DOWN)
                else :
                    self.playerList[i].move(self.board.LEFT)
            elif choice == 2 :
                pass
            i += 1
        return True
        

if __name__ == '__main__':
    newGame = Game(1,9)
    print(newGame, end="") 
    print("pawn coord ", end="")
    print(newGame.pawnList[0].coord)
