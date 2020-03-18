class AI():
    """
    Will be abstract.
    """

    def __init__(self, board, controller):
        self.controller = controller
        self.board = board


import random
import time
class Debilus(AI):
    """
    Dumb AI, mostly random because dumb.
    """

    def __init__(self, board, controller):
        super().__init__(controller, board)
    
    def act(self):
        directions = {1 : self.board.UP, 2 : self.board.RIGHT, 3 : self.board.DOWN, 4 : self.board.LEFT}

        action = random.randint(1, 2)

        if action == 1:
            dir = random.randint(1, 4)
            while ((self.controller.move(directions[dir])) == 0):
                dir = random.randint(1, 4)

        elif action == 2:
            ordinate = random.randint(0, self.board.size)
            absciss = random.randint(0, self.board.size)
            wallOrigin = ordinate, absciss
            dir = random.randint(1, 2)
            self.controller.placeWall(wallOrigin, directions[dir])





        