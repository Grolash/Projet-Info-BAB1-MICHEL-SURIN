import World
#import Controller


class MOAI():
    """ 
    This class is the mother of all items (M.O.A.I.).
    (In fact walls do not depend from this class as they are not "played" by a controller of any type.)

    It exist for adaptability purpose, so we can add playable items other than pawns.
    
    Attributes:
    -----------    -----------    -----------
    direction: The direction of the movement
        changed by controller when making a move
    -----------    -----------    -----------
    """

    def __init__(self, game, controller, coordinates, direction = None):
        self.coord = coordinates #is a tuple
        self.direction = direction
        super().__init__(game, controller)
    
    def move(self):
        if self.direction is not None:
            self.coord = self.coord + self.direction
    
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

    def __init__(self):
        self.name = "Pawn"
        self.representation = "!"


class Wall(MOAI):
    """
    This is a Wall child class of items.
    #WIP : origin --> tuple
           direction --> Board.UP/DOWN/...
    """
    def __init__(self):
        self.name = "Wall"
        self.representation = None #TODO
        self.controller = None
