


class PlayerCell :
    """
    represent a cell on the board.
    """
    def __init__(self, coord) :
        self.coord = coord
        self.hasPawn = False
        self.hasWallUP = False
        self.hasWallDOWN = False
        self.hasWallLEFT = False
        self.hasWallRIGHT = False
    
    def __str__(self) :
        
        if self.hasPawn :
            return "X"
        else :
            return "O"

    def isConnected(self, direction) :
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
    represent the board. It contains a double array of PlayerCell
    - controllerList : is a list that contain i element. i is the number of player in the game given by Init
    - size = the size of the Board (it's a square) MUST BE A ODD NUMBER
    - direction = represents the possible direction (UP, DOWN, LEFT, RIGHT)
    """
    def __init__(self, controllerList, size) :
        self.pawnList = controllerList
        self.size = size
        self.PlayerCellList = []
        self.UP = (0,1)
        self.DOWN = (0,-1)
        self.LEFT = (-1,0)
        self.RIGHT = (1,0)
        for i in range(size) :
            self.PlayerCellList.append([])
            for j in range(size) :
                Cell = PlayerCell((i,j))
                self.PlayerCellList[i].append(Cell)
    
    def __str__(self):
        res = ""
        for i in range(size) :
            for j in range(size) :
                res += self.PlayerCellList[i][j] + " "
            res += "\n"
        return res