"""
the goal of this file is simply to implement a function that will take
2 tuples of coordinates (y, x) and will add the y together and th x together.
will return a new tuple.
"""

def add(tuple1, tuple2) :
    """
    tuples len MUST be 2
    """
    return tuple1[0] + tuple2[0], tuple1[1] + tuple2[1]


    def move(self, direction) :
        """
        The move method test if there is a pawn in the desired direction.
        If there is no pawn, it does a normal move if possible.
        If there is one, it makes (if possible) an automatic move to a legal position, diagonally, with a priority to the right of the pawn.
        """
        y, x = self.dependency.coord 
        if self.board.playerCellList[y][x].pawnTo(direction):
            nextCoord = add(self.dependency.coord, direction)

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
        else :
            if direction is not None :
                if canMove(self, direction):
                    self.dependency.coord = add(self.dependency.coord, direction)