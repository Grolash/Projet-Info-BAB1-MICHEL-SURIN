

class MOAI():
    """ 
    This class is the mother of all items (M.O.A.I.).
    
    Attributes:
    -----------    -----------    -----------
    direction: The direction of the movement
        changed by controller when making a move
    -----------    -----------    -----------
    """

    def __init__(self, coordinates):
        self.coord = coordinates #is a tuple

    def typeOfItem(self):
        string = "This is an item of type %s currently under the control of %s" % (self.name, self.controller) #self.name depend from child class
        return string

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

    def __init__(self, name, coord=(0,4), start=(0,4)):
        self.name = name
        super().__init__(coord)
        self.start = start
        self.representation = "!"


class Wall(MOAI):
    """
    This is a Wall child class of items.
    #WIP : origin --> tuple
           direction --> Board.UP/DOWN/...
    """
    def __init__(self, coord, direction=None):
        super().__init__(coord)
        self.direction = direction
        self.name = "Wall"
        if self.coord[1] == 0 :
            self.representation = "["
        if self.coord[1] == 8 :
            self.representation = "]"
        if self.coord[0] == 8 :
            self.representation = "\\"
        if self.coord[0] == 0 :
            self.representation = "/"
        self.secondPart()

    def secondPart(self):
        new_coordinates = (self.coord + self.direction)
        secondPartOfWAll = Wall(new_coordinates)
