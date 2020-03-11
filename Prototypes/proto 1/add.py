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

