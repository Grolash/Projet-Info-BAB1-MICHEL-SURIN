from World import Board


class MOAI():
    """ 
    This class is the mother of all items (M.O.A.I.).
    
    Attributes:
    -----------    -----------    -----------
    direction: The direction of the movement
        changed by controller when making a move
    -----------    -----------    -----------
    """

    def __init__(self, board, coordinates, direction = None):
        self.coord = coordinates #is a tuple
        self.direction = direction
    
    def __str__(self):
        return self.representation #is dependent from child class

class Pawn(MOAI):
    """ 
    This is a Pawn child class of items.

    Attributes:
    -----------    -----------    -----------
    name: Type of item
        exemple: if the item is a Pawn, self.name = "Pawn"
    representation: Graphical representation
        an ascii symbol used in consol representation.
    -----------    -----------    -----------
    """

    def __init__(self, name, coord=(0,4)): #coord is hardcoded to be default 9 sized board spawn
        super().__init__()
        self.name = name
        self.representation = "!"
        self.coord = coord


class Wall(MOAI):
    """
    This is a Wall child class of items.
    #WIP : origin --> tuple
           direction --> Board.UP/DOWN/...
    """
    def __init__(self):
        super().__init__()
        self.name = "Wall"
        if self.coord[1] == 0 :
            self.representation = "["
        if self.coord[1] == 8 :
            self.representation = "]"
        if self.coord[0] == 8 :
            self.representation = "\\"
        if self.coord[0] == 0 :
            self.representation = "/"
        SecondPart()

    def SecondPart(self):
        new_coordinates = (self.coordinates + self.direction)
        SecondPartOfWAll = Wall(self.board, new_coordinates, direction = None)
