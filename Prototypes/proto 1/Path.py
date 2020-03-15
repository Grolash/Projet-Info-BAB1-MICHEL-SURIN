"""
the goal of this file is to find a path from a starting position
to the goal line.

"""
import World
from Rules import canMove

def path(start, goal, controller, board) :
    """
    start : a tuple representing the starting coord
    goal : the targetted line.
    controller : the current player whom we need to find a path :)
    board : refers to the actual board where a path is needed to be find
    """
    toBeExplored = []
    explored = []
    toBeExplored.append(start)
    while len(toBeExplored) != 0 :
        current = toBeExplored.pop()
        explored.append(current)
        explore(current, explored, goal, controller, board)
        if toBeExplored[-1] == None :
            return None
        


def explore(start, toBeExplored, explored, goal, controller, board) :
    """
    the goal is to explore every cell around the start cell except
    if a surrounding cell is already in explored list, it's not considered as
    a need-to-explore.
    """
    startUP = add(start, board.UP)
    startLEFT = add(start, board.LEFT)
    startDOWN = add(start, board.DOWN)
    startRIGHT = add(start, board.RIGHT)
    blockedCounter = 0
    if canMove(controller, board.UP, start) and (startUP not in explored) and not (checkGoal(startUP, goal)) :
        toBeExplored.append(startUP)
    else :
        blockedCounter += 1
    if canMove(controller, board.LEFT, start) and (startLEFT not in explored) and not (checkGoal(startLEFT, goal)) :
        toBeExplored.append(startLEFT)
    else :
        blockedCounter += 1
    if canMove(controller, board.DOWN, start) and (startDOWN not in explored) and not (checkGoal(startDOWN, goal)) :
        toBeExplored.append(startDOWN)
    else :
        blockedCounter += 1
    if canMove(controller, board.RIGHT, start) and (startRIGHT not in explored) and not (checkGoal(startRIGHT, goal)) :
        toBeExplored.append(startRIGHT)
    else :
        blockedCounter += 1
    if blockedCounter == 4 :
        toBeExplored.append(None)
    

def checkGoal(current, goal) :
    """
    return True if the current coord tuple's is on the goal line
    """
    if current[0] == goal :
        return True
    else :
        return False






















































