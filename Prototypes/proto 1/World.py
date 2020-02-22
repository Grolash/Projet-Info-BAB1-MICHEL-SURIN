


class PlayerCell :
    """
    represent a cell on the board.
    - hasPawn = True if a Pawn is on the PlayerCell
    - hasWall(UP/DOWN/LEFT/RIGHT) = True if there is a wall on that side of the PlayerCell
    """
    def __init__(self, coord) :
        self.coord = coord #is a tuple (y,x)
        self.hasPawn = False
        self.hasWallUP = False
        self.hasWallDOWN = False
        self.hasWallLEFT = False
        self.hasWallRIGHT = False
        #the followong lines create a "Wall" if the PlayerCell is at a border of the Board
        if self.coord[1] == 0 : 
            self.hasWallLEFT = True
        elif self.coord[1] == 8 : #8 should be Board.size but it doesn't work
            self.hasWallRIGHT = True
        if self.coord[0] == 8 : #8 should be Board.size but it doesn't work
            self.hasWallDOWN = True
        elif self.coord[0] == 0 : 
            self.hasWallUP = True


    def __str__(self) :
        
        if self.hasWallDOWN :
            return "-"
        elif self.hasWallLEFT :
            return "["
        elif self.hasWallRIGHT :
            return "]"
        elif self.hasWallUP :
            return "^"
        else :
            return "O"

    def hasWall(self, direction) :
        """
        return True if there is a wall on the /Direction/ side of the PlayerCell 
        """
        if direction == Board.UP :
            return self.hasWallUP
        elif direction == Board.DOWN :
            return self.hasWallDOWN
        elif direction == Board.LEFT :
            return self.hasWallLEFT
        elif direction == Board.RIGHT :
            return self.hasWallRIGHT


class Board :
    """
    represent the board.
    - controllerList : is a list that contain i element. i is the number of player in the game given by Init
    - size = the size of the Board (it's a square) MUST BE A ODD NUMBER
    - direction = represents the possible direction (UP, DOWN, LEFT, RIGHT)
    - PlayerCellList = contains a double array of PlayerCell 
    """
    def __init__(self, size) :
        #self.pawnList = controllerList
        self.size = size
        self.PlayerCellList = []
        self.UP = (0,1)
        self.DOWN = (0,-1)
        self.LEFT = (-1,0)
        self.RIGHT = (1,0)
        for i in range(size) :
            self.PlayerCellList.append([])
            for j in range(size) :
                coord = i, j
                Cell = PlayerCell(coord)
                self.PlayerCellList[i].append(Cell)
    
    def __str__(self):
        res = ""
        for i in range(self.size) :
            for j in range(self.size) :
                res += str(self.PlayerCellList[i][j]) + " "
            res += "\n"
        return res

if __name__ == '__main__':
    board = Board(9)
    print(board)