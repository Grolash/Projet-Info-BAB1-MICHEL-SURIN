import World
#import Controller


class MOAI():
    """ 
    This class is the mother of all items (M.O.A.I.).
    (In fact walls do not depend from this class as they are not "played" by a controller of any type.)

    It exist for adaptability purpose, so we can add playable items other than pawns.
    
    Attributes
    ----------
    name: Type of item
        exemple: if the item is a Pawn, self.name = "Pawn"
    
    #TODO
    """

    def __init__(self, game, controller, coordinates, direction, TODO):
        self.coord = coordinates
        self.direction = direction
        self.name = ""
        super().__init__(game, controller)
    
    def move(self):
        if self.direction is not None:
            self.coord = self.coord + self.direction
    
    def __str__(self):
        return self.representation
    
    def typeOfObject(self):
        string = "This is an item of type %s currently under the control of %s" % (self.name, self.controller)
        return string

class Pawn(MOAI):
    """ 
    This is an Pawn item.

    Attributes
    ----------
    """

    def __init__(self):
        self.name = "Pawn"
        self.representation = "!"
