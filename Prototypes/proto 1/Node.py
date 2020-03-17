class Node:
    def __init__(self, data, next=None, next2=None, next3=None, next4=None):
        self.data = data
        self.next = next
        self.next2 = next2
        self.next3 = next3
        self.next4 = next4


    def __str__(self):
        return str(self.data)