from World import *
from Items import *
from engine import *
from Rules import *
import AI

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
            pawn = self.pawnList[i-1]
            playerName = "Player" + str(i)
            newController = PawnController("Human", pawn, self.board, playerName, 10)
            print(newController.dependency.coord)
            self.playerList.append(newController)
            self.refresh()
            self.gameLoop()
    
    def __str__(self) :
        return self.board.__str__()

    def refresh(self, wall=False, origin=None, direction=None) :
        """
        if wall == False, the function will refresh the pawn positions
        if wall == True, the function will add the new wall to the board
        """
        if wall and ( origin is not None ) and ( direction is not None ) :
            y = origin[0]
            x = origin[1]
            if direction == self.board.UP :
                self.board.playerCellList[y][x].hasWallRIGHT = True
                self.board.playerCellList[y][x+1].hasWallLEFT = True
                self.board.playerCellList[y-1][x].hasWallRIGHT = True
                self.board.playerCellList[y-1][x+1].hasWallLEFT = True
            elif direction == self.board.RIGHT :
                self.board.playerCellList[y][x].hasWallUP = True
                self.board.playerCellList[y][x+1].hasWallUP = True
                self.board.playerCellList[y-1][x].hasWallDOWN = True
                self.board.playerCellList[y-1][x+1].hasWallDOWN = True
        else :
            self.board.reset()
            for player in self.playerList :
                x = player.dependency.coord[1]
                y = player.dependency.coord[0]
                self.board.playerCellList[y][x].hasPawn = True



    def checkWin(self, player) :
        """
        check if the player has reached the opposite of the board
        """
        return player.hasWon()
        
    def gameLoop(self) :
        """
        create the turn-per-turn system.
        Allow each player to take choose an action while no-one has won
        i is reseted to 0 if i >= 2 so there is no out of bounds error
        after each player's turn, the game loop checks if the player has won or not
        """
        i = 0
        while not self.checkWin(self.playerList[i]) :
            playerCtrl = self.playerList[i]
            if playerCtrl.controllerType == "Human":
                coord = playerCtrl.dependency.coord
                print(self.board)
                print("player " + str(i+1) + " pick-up an action :")
                print("1) move")
                print("2) place a Wall")
                choice = int(input("your choice : "))
                #following lines can be improved with a do...while (80 to 110)
                if choice == 1 :
                    print("choose a direction :")
                    print("1) UP")
                    print("2) RIGHT")
                    print("3) DOWN")
                    print("4) LEFT")
                    intDir = int(input("direction : "))
                    if intDir == 1 :
                        direction = self.board.UP
                    elif intDir == 2 :
                        direction = self.board.RIGHT
                    elif intDir == 3 :
                        direction = self.board.DOWN
                    elif intDir == 4 :
                        direction = self.board.LEFT
                    while (playerCtrl.move(direction) == 0) : #if the player can move, "move()" does so, else it returns 0 and the loop demands again
                        print("choose a direction :")
                        print("1) UP")
                        print("2) RIGHT")
                        print("3) DOWN")
                        print("4) LEFT")
                        intDir = int(input("direction : "))
                        if intDir == 1 :
                            direction = self.board.UP
                        elif intDir == 2 :
                            direction = self.board.RIGHT
                        elif intDir == 3 :
                            direction = self.board.DOWN
                        elif intDir == 4 :
                            direction = self.board.LEFT
                
                
                    
                    self.refresh()
                elif choice == 2 :
                    print("choose a valid case (format : 'x y')")
                    wallOriginInput = input().split()
                    wallOrigin_Y = int(wallOriginInput[0])
                    wallOrigin_X = int(wallOriginInput[1])
                    wallOrigin = wallOrigin_Y, wallOrigin_X
                    print("choose the direction : ")
                    print("1) horizontal")
                    print("2) vertical")
                    wallDirectionInput = int(input())
                    if wallDirectionInput == 1 :
                        wallDirection = self.board.RIGHT
                    else :
                        wallDirection = self.board.UP 
                    self.playerList[i].placeWall(wallOrigin, wallDirection)
                    self.refresh(True, wallOrigin, wallDirection)
                print(self.playerList[i].dependency.coord)
                findAPath(self, playerCtrl)
            elif playerCtrl.controllerType == "Debilus":
                AI.Debilus(playerCtrl.board, playerCtrl).act()
            i += 1
            if i >= len(self.playerList) :
                i = 0
        print("WON")
      
    
    

if __name__ == '__main__':
    newGame = Game(1,9)




























