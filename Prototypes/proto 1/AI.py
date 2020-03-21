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

    time.sleep(0,2)

class Smarted(AI):
    """
    Less dumb AI, because I could. Does not move backward and do not place a wall in front of self.
    For "strategy" purpose, does not place wall further than it's own cell (ordinate speaking).
    For same reason, does not do twice in a row the same action.
    """
    def __init__(self, board, controller):
        super().__init__(controller, board)
        self.action = 1
    
    def act(self):
        directions = {1 : self.board.UP, 2 : self.board.RIGHT, 3 : self.board.DOWN, 4 : self.board.LEFT}

        if self.action == 1:
            dir = random.randint(1, 4)
            if self.dependency.start[0] == self.board.playerCellList[0] :
                if dir == 1:
                    dir = 3
            elif self.dependency.start[0] == self.board.playerCellList[-1] :
                if dir == 3:
                    dir = 1
            while ((self.controller.move(directions[dir])) == 0):
                dir = random.randint(1, 4)
                if self.dependency.start[0] == self.board.playerCellList[0] :
                    if dir == 1:
                        dir = 3
                elif self.dependency.start[0] == self.board.playerCellList[-1] :
                    if dir == 3:
                        dir = 1
        
        elif self.action == 2:
            ordinate = random.randint(0, self.controller.dependency.coord[0])
            absciss = random.randint(0, self.board.size)
            wallOrigin = ordinate, absciss
            dir = random.randint(1, 2)
            if (absciss == self.controller.dependency.coord[1]) and (dir == 2):
                absciss += 1
            self.controller.placeWall(wallOrigin, directions[dir])
        
        self.action += 1
        if self.action == 3:
            self.action = 1
        time.sleep(0,5)





        