import World
#import Controller


class MOAI():
    """ 
    This class is the mother of all items (M.O.A.I.).
    
    Attributes:
    -----------    -----------    -----------
    direction: The direction of the movement
        changed by controller when making a move
    -----------    -----------    -----------
    """

    def __init__(self, board, controller, coordinates, direction = None):
        self.coord = coordinates #is a tuple
        self.direction = direction
        super().__init__(board, controller)
    
    def __str__(self):
        return self.representation #is dependent from child class
    
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

    def __init__(self, name, coord=(0,4)): #coord is hardcoded to be default 9 sized board spawn
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
        self.name = "Wall"
        if self.coord[1] == 0 :
            self.representation = "["
        if self.coord[1] == 8 :
            self.representation = "]"
        if self.coord[0] == 8 :
            self.representation = "\\"
        if self.coord[0] == 0 :
            self.representation = "/"
        self.controller = None
        SecondPart()

    def SecondPart(self):
        new_coordinates = (self.coordinates + self.direction)
        SecondPartOfWAll = Wall(self.board, self.controller, new_coordinates, direction = None)
