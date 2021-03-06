from add import *





class Board :
    """
    represent the board.
    - size = the size of the Board (it's a square) MUST BE A ODD NUMBER
    - direction = represents the possible direction (UP, DOWN, LEFT, RIGHT) --> should be a dico
    - PlayerCellList = contains a double array of PlayerCell 
    - wallList = list containing each wall placed in the board in tuple form : (ORIGIN, SECONDPART) with ORIGIN and SECONDPART being coordinates
    """
    def __init__(self, size) :
        #self.pawnList = controllerList
        self.size = size
        self.playerCellList = []
        self.wallList = []
        self.UP = (-1,0)
        self.DOWN = (1,0)
        self.LEFT = (0,-1)
        self.RIGHT = (0,1)
        for i in range(size) :
            self.playerCellList.append([])
            for j in range(size) :
                coord = i, j
                Cell = PlayerCell(coord, self)
                self.playerCellList[i].append(Cell)
    
    def __str__(self):
        res = ""
        for i in range(self.size) :
            for j in range(self.size) :
                res += str(self.playerCellList[i][j]) + " "
            res += "\n"
        return res

    def reset(self) :
        """
        reset every PlayerCell.hasPawn to False
        --> will be called by refresh function
        """
        for i in range(len(self.playerCellList)) :
            for j in range(len(self.playerCellList[i])) :
                self.playerCellList[i][j].hasPawn = False

class PlayerCell(Board) :
    """
    represent a cell on the board.
    - hasPawn = True if a Pawn is on the PlayerCell
    - hasWall(UP/DOWN/LEFT/RIGHT) = True if there is a wall on that side of the PlayerCell
    """
    def __init__(self, coord, board) :
        self.board = board
        self.coord = coord #is a tuple (y,x)
        self.hasPawn = False
        self.hasWallUP = False
        self.hasWallDOWN = False
        self.hasWallLEFT = False
        self.hasWallRIGHT = False
        self.UP = (-1,0)
        self.DOWN = (1,0)
        self.LEFT = (0,-1)
        self.RIGHT = (0,1)
        #the following lines create a "Wall" if the PlayerCell is at a border of the Board
        if self.coord[1] == 0 : 
            self.hasWallLEFT = True
        if self.coord[1] == self.board.size-1 :
            self.hasWallRIGHT = True
        if self.coord[0] == self.board.size-1 :
            self.hasWallDOWN = True
        if self.coord[0] == 0 : 
            self.hasWallUP = True


    def __str__(self) :
        res = ""
        if self.hasPawn :
            res += "!" #will be MOAI.__str__ (<item>.__str__)
        if self.hasWallLEFT :
            res += "["
        if self.hasWallUP :
            res += "/"

        if self.hasWallDOWN :
            res += "\\"
        if self.hasWallRIGHT :
            res += "]" 
        else :
            res += "O"
        return res

    def hasWall(self) :
        """
        Returns "True" if the PlayerCell has at least 1 wall (or Board border).
        """
        if self.hasWallDOWN or self.hasWallLEFT or self.hasWallRIGHT or self.hasWallUP :
            return True
        else :
            return False

    def wallTo(self, direction) :
        """
        Returns "True" if there is a wall on the /Direction/ side of the PlayerCell.
        """
        if direction == self.UP :
            return self.hasWallUP
        elif direction == self.DOWN :
            return self.hasWallDOWN
        elif direction == self.LEFT :
            return self.hasWallLEFT
        elif direction == self.RIGHT :
            return self.hasWallRIGHT


    def pawnTo(self, direction) :
        """
        Returns "True" if the PlayerCell in the selected direction has a pawn on it.
        """
        dirCoord = add(self.coord, direction)
        return self.board.playerCellList[dirCoord[0]][dirCoord[1]].hasPawn