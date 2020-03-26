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
        time.sleep(0,2)

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
        time.sleep(0,5)

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
            dir = random.randint(1, 2)
            if (absciss == self.controller.dependency.coord[1]) and (dir == 2):
                absciss += 1
            wallOrigin = ordinate, absciss
            self.controller.placeWall(wallOrigin, directions[dir])
        
        self.action += 1
        if self.action == 3:
            self.action = 1

import Rules

class Smart(AI):
    """
    "Smart" AI based on pathfinding to be more efficient than any random resolution algorythm.
    It uses "action codes" to tell itself what he has to do; action codes are single-digit intergers translating a concept into simple code naming.
    Not all action codes might have a direct interrest in being different, but it permits to know why the AI has done a given move.
    
    Action codes:
    -----------    -----------    -----------
    0: no action given
        occurs at the start of the game or if the AI has won; it's a "do nothing" order
    1: priority move
        occurs when the AI is behind other player(s) in the "race" to the goal; the purpose is not to be left behind
    2: responsive wall placing 
        occurs when not in situation <action code 1> and was in situation <action code 1> before; the purpose of this choice is to capitalize on taken advance to keep it
    3: non-priority move
        occurs when the AI moves for another reason than <action code 1>; typically when it has placed a wall before
    4: random action 
        occurs when the AI's previous action code was <action code 3>; it's not a real strategy in itself but reduces the previsibility of the AI for human players
    66: victory code / definitive stop code
         typically occurs if the AI has won and the game is not over (for exemple in a 1V1V1V1 or 2v2 games); tell the AI to execute Order 66 (blocks itself in <action code 0>)
    
    /!\ Wall placing actions only go place a wall if the AI's controller still has strictly more than 0 walls. /!\ 
    -----------    -----------    -----------
    """
    def __init__(self, board, controller, game):
        super().__init__(controller, board)
        self.game = game
        self.actionCode = 0
        self.lastActionCode = 0
        self.travelDic = {}

    def travelDic(self):
        for i in range(len(self.game.playerList)):
            self.travelDic[str(self.game.playerList[i].playerName)] = travelled(self.game.playerList[i])

    def travelled(self, PawnController):
        travelled = abs(PawnController.dependency.start[0] - PawnController.dependency.coord[0])

    def AIMove(self):
        pass #calculate a move based on pathfinding

    def AIPlaceWall(self):
        """
        Place a wall in front of the most advanced player in the game if it can, else does it randomly.
        """
        max = ""
        for i in self.travelDic:
            if self.travelDic[i] > max:
                max = self.game.playerList[i]
                if self.game.playerList[i].lastMovedDirection != None:
                    if Rules.canPlaceWall(self.game.playerList[i].dependency.coord, self.game.playerList[i].lastMovedDirection, self.game):
                        self.controller.placeWall(self.game.playerList[i].dependency.coord, self.game.playerList[i].lastMovedDirection)
                    else:
                        directions = {1 : self.board.UP, 2 : self.board.RIGHT, 3 : self.board.DOWN, 4 : self.board.LEFT}
                        ordinate = random.randint(0, self.controller.dependency.coord[0])
                        absciss = random.randint(0, self.board.size)
                        dir = random.randint(1, 2)
                        if (absciss == self.controller.dependency.coord[1]) and (dir == 2):
                            absciss += 1
                        wallOrigin = ordinate, absciss
                        self.controller.placeWall(wallOrigin, directions[dir])
                else:
                    if Rules.canPlaceWall(self.game.playerList[i].dependency.coord, self.controller.lastMovedDirection, self.game):
                        self.controller.placeWall(self.game.playerList[i].dependency.coord, self.controller.lastMovedDirection)
                    else:
                        directions = {1 : self.board.UP, 2 : self.board.RIGHT, 3 : self.board.DOWN, 4 : self.board.LEFT}
                        ordinate = random.randint(0, self.controller.dependency.coord[0])
                        absciss = random.randint(0, self.board.size)
                        dir = random.randint(1, 2)
                        if (absciss == self.controller.dependency.coord[1]) and (dir == 2):
                            absciss += 1
                        wallOrigin = ordinate, absciss
                        self.controller.placeWall(wallOrigin, directions[dir])

    def decideAction(self):
        """
        Decides of the action code number.
        """
        selfTravelled = travelled(self.controller) #technically, selfTravelled is already in travelDic but it's FAR easier to find it this way and doesn't cost much
        for i in self.travelDic:

            if self.travelDic[i] > selfTravelled:
                return 1 

            else:
                if self.lastActionCode == 1:
                    return 2

                elif self.lastActionCode == 0 or self.lastActionCode == 2:
                    return 3

                elif self.lastActionCode == 3:
                    return 4 #the resulting action must change the lastActionCode in consequence if does an <action code 3>

                elif self.lastActionCode == 66:
                    return 0 #Ah... Victory!
    
    def executeOrder(self):
        """
        Executes an action code order after deciding it
        """
        if not(self.controller.hasWon()):
            self.actionCode = decideAction()
            self.lastActionCode = self.actionCode

            if self.actionCode == 1:
                AIMove()
                time.sleep(1)

            elif self.actionCode == 2:
                if self.controller.stock > 0:
                    AIPlaceWall()
                else: 
                    AIMove()
                time.sleep(1)
            
            elif self.actionCode == 3:
                AIMove()
                time.sleep(1)

            elif self.actionCode == 4:
                self.actionCode = random.randint(3, 4)
                if self.actionCode == 3:
                    self.lastActionCode = 3
                    AIMove()
                    time.sleep(1)
                else:
                    if self.controller.stock > 0:
                        AIPlaceWall()
                    else: 
                        AIMove()
                    time.sleep(1)
            
            elif self.actionCode == 0:
                time.sleep(1)


        else:
            self.lastActionCode = 66 #Executes Order 66





        