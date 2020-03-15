import Node_double

class LC_double:
    def __init__(self):
        # Initialise la liste doublement chainée vide.
        self.head = None
        self.tail = None
        self.size = 0

    def __str__(self):
        #Traduit la liste doublement chainée lors du print.
        if self.size == 0:
            return "None"
        else:
            res = "None <-- "
            tmp = self.head
            while tmp.next != None:
                res += str(tmp.data) + " <--> "
                tmp = tmp.next
            res += str(tmp.data) + " --> None"
            return res

    def isEmpty(self):
        """
        Retourne True si la liste chainée est vide.
        Retourne Faux sinon.
        """
        if self.size == 0:
            return True
        return False

    def length_LC_double(self):
        # Retourne la taille de la liste chainée.
        return self.size

    def min_LC_double(self):
        # Retourne la valeur minimum de la liste chainée.
        if self.size == 0:
            return None
        minimum = float("inf")
        tmp = self.head
        while tmp != None:
            if minimum > tmp.data:
                minimum = tmp.data
            tmp = tmp.next
        return minimum

    def max_LC_double(self):
        # Retourne la valeur maximum de la liste chainée.
        if self.size == 0:
            return None
        maximum = -float("inf")
        tmp = self.head
        while tmp != None:
            if maximum < tmp.data:
                maximum = tmp.data
            tmp = tmp.next
        return maximum

    def isIn(self, obj):
        """
        Retourne True si l'objet se trouve dans la liste chainée.
        Retourne False si l'objet ne se trouve pas dans la liste chainée.
        """
        if self.size == 0:
            return False
        tmp = self.head
        while tmp != None:
            if tmp.data == obj:
                return True
            tmp = tmp.next
        return False

    def index_value(self, i):
        # Retourne la valeur (data) de la liste chainée à l'indice i.
        if i >= self.size:
            print("Pas possible")
        else:
            tmp = self.head
            compteur = 0
            while compteur != i:
                tmp = tmp.next
                compteur += 1
            return tmp.data

    def somme_LC_double(self):
        # Calcule la somme totale de la liste chainée.
        if self.size == 0:
            return 0
        res = 0
        tmp = self.head
        while tmp != None:
            res += tmp.data
            tmp = tmp.next
        return res

    def moyenne_LC_double(self):
        # Calcule la moyenne totale de la liste chainée.
        if self.size == 0:
            return 0
        res_tot = LC_double.somme_LC_double(self)
        moy = res_tot / self.size
        return moy

    def sqr_LC_double(self):
        # Calcule le carré de chaque maillon d'une liste chainée.
        tmp = self.head
        while tmp != None:
            res = tmp.data * tmp.data
            tmp.data = res
            tmp = tmp.next

    def add_LC_double(self, other):
        # Additionne chaque maillon de 2 listes chainées entre elle et en retourne une nouvelle.
        if LC_double.isEmpty(self):
            return other
        elif LC_double.isEmpty(other):
            return self
        else:
            tmp1 = self.head
            tmp2 = other.head
            new_LC = LC_double()
            while tmp1 != None and tmp2 != None:
                data_maillon = tmp1.data + tmp2.data
                new_LC.end_insert(data_maillon)
                tmp1 = tmp1.next
                tmp2 = tmp2.next
            if tmp1 == None and tmp2 == None:
                return new_LC
            elif tmp1 == None:
                while tmp2 != None:
                    data_maillon = tmp2.data
                    new_LC.end_insert(data_maillon)
                    tmp2 = tmp2.next
            else:
                while tmp1 != None:
                    data_maillon = tmp1.data
                    new_LC.end_insert(data_maillon)
                    tmp1 = tmp1.next
            return new_LC

    def sub_LC_double(self, other):
        # Soustrait chaque maillon de 2 listes chainées entre elle et en retourne une nouvelle.
        if LC_double.isEmpty(self):
            return other
        elif LC_double.isEmpty(other):
            return self
        else:
            tmp1 = self.head
            tmp2 = other.head
            new_LC = LC_double()
            while tmp1 != None and tmp2 != None:
                data_maillon = tmp1.data - tmp2.data
                new_LC.end_insert(data_maillon)
                tmp1 = tmp1.next
                tmp2 = tmp2.next
            if tmp1 == None and tmp2 == None:
                return new_LC
            elif tmp1 == None:
                while tmp2 != None:
                    data_maillon = -tmp2.data
                    new_LC.end_insert(data_maillon)
                    tmp2 = tmp2.next
            else:
                while tmp1 != None:
                    data_maillon = tmp1.data
                    new_LC.end_insert(data_maillon)
                    tmp1 = tmp1.next
            return new_LC

    def mult_LC_double(self, other):
        # Multiplie chaque maillon de 2 listes chainées entre elle et en retourne une nouvelle.
        if LC_double.isEmpty(self):
            return other
        elif LC_double.isEmpty(other):
            return self
        else:
            tmp1 = self.head
            tmp2 = other.head
            new_LC = LC_double()
            while tmp1 != None and tmp2 != None:
                data_maillon = tmp1.data * tmp2.data
                new_LC.end_insert(data_maillon)
                tmp1 = tmp1.next
                tmp2 = tmp2.next
            if tmp1 == None and tmp2 == None:
                return new_LC
            elif tmp1 == None:
                while tmp2 != None:
                    data_maillon = tmp2.data
                    new_LC.end_insert(data_maillon)
                    tmp2 = tmp2.next
            else:
                while tmp1 != None:
                    data_maillon = tmp1.data
                    new_LC.end_insert(data_maillon)
                    tmp1 = tmp1.next
            return new_LC

    def div_LC_double(self, other):
        # Divise chaque maillon de 2 listes chainées entre elle et en retourne une nouvelle.
        if LC_double.isEmpty(self):
            return other
        elif LC_double.isEmpty(other):
            return self
        else:
            tmp1 = self.head
            tmp2 = other.head
            new_LC = LC_double()
            while tmp1 != None and tmp2 != None:
                if tmp1.data == 0 and tmp2.data == 0:
                    data_maillon = "null"
                elif tmp1.data == 0 and tmp2.data != 0:
                    data_maillon = 0
                elif tmp2.data == 0:
                    if tmp1.data > 0:
                        data_maillon = float("inf")
                    else:
                        data_maillon = -float("inf")
                else:
                    data_maillon = tmp1.data / tmp2.data
                new_LC.end_insert(data_maillon)
                tmp1 = tmp1.next
                tmp2 = tmp2.next
            if tmp1 == None and tmp2 == None:
                return new_LC
            elif tmp1 == None:
                while tmp2 != None:
                    if tmp2.data == 0:
                        data_maillon = float("inf")
                    else:
                        data_maillon = 1/tmp2.data
                    new_LC.end_insert(data_maillon)
                    tmp2 = tmp2.next
            else:
                while tmp1 != None:
                    data_maillon = tmp1.data
                    new_LC.end_insert(data_maillon)
                    tmp1 = tmp1.next
            return new_LC

    def begin_insert(self, obj):
        if self.size == 0:
            out_list = Node_double.Node_double(obj)
            self.head = out_list
            self.tail = out_list
            self.size = 1
        else:
            out_list = Node_double.Node_double(obj)
            out_list.next = self.head
            self.head = out_list
            self.head.prev = None
            self.head.next.prev = self.head
            self.size += 1

    def end_insert(self, obj):
        if self.size == 0:
            out_list = Node_double.Node_double(obj)
            self.head = out_list
            self.tail = out_list
            self.size = 1
        else:
            out_list = Node_double.Node_double(obj)
            self.tail.next = out_list
            out_list.prev = self.tail
            self.tail = out_list
            self.tail.next = None
            self.size += 1

    def begin_remove(self):
        if self.size == 0:
            print("Votre liste chainée est vide ! Elle est de la forme : ")
            print(self)
        elif self.size == 1:
            self.head = None
            self.tail = None
            self.size = 0
        else:
            tmp = self.head.next
            self.head = None
            self.head = tmp
            self.head.prev = None
            self.size -= 1

    def end_remove(self):
        if self.size == 0:
            print("Votre liste chainée est vide ! Elle est de la forme : ")
            print(self)
        elif self.size == 1:
            self.head = None
            self.tail = None
            self.size = 0
        else:
            tmp = self.head
            while tmp.next.next != None:
                tmp = tmp.next
            self.tail = tmp
            self.tail.next = None
            self.size -= 1

    def kill(self):
        # Reset votre liste chainée.
        self.head = None
        self.size = 0

    def insert_index(self, obj, i):
        """
        !!! Utilisation des fonctions begin_insert(self, obj) et end_insert(self, obj) !!!
        Permet d'ajouter le maillon obj à l'index i.
        """
        if i == 0:
            LC_double.begin_insert(self, obj)
        elif i == self.size - 1:
            LC_double.end_insert(self, obj)
        elif i > self.size:
            print("L'index est trop grand par rapport à la liste, votre liste chainée reste : ")
            print(self)
        else:
            compteur = 0
            tmp = self.head
            while compteur != i-1:
                tmp = tmp.next
                compteur += 1
            elem = Node_double.Node_double(obj)
            elem.next = tmp.next
            tmp.next = elem
            elem.prev = tmp
            self.size += 1

    def remove_index(self, i):
        """
        !!! Utilisation des fonctions begin_remove(self) et end_remove(self) !!!
        Permet de retirer le maillon à l'index i.
        """
        if self.size == 0:
            return self
        if i == 0:
            LC_double.begin_remove(self)
        elif i == self.size - 1:
            LC_double.end_remove(self)
        elif i >= self.size:
            print("L'index est trop grand par rapport à la liste, votre liste chainée reste : ")
        else:
            compteur = 0
            tmp = self.head
            while compteur != i - 1:
                tmp = tmp.next
                compteur += 1
            tmp.next = tmp.next.next
            elem = tmp.next
            elem.prev = tmp
            self.size -= 1

    def echanger(self, other1, other2, order=True):
        # Permet de permuter la valeur de 2 maillons dans la liste chainée.
        if other1.data >= other2.data and order:
            tmp = other1.data
            other1.data = other2.data
            other2.data = tmp
        elif other1.data < other2.data and order:
            tmp = other1.data
            other1.data = other2.data
            other2.data = tmp
        elif other2.data >= other1.data and not order:
            tmp = other1.data
            other1.data = other2.data
            other2.data = tmp
        elif other2.data < other1.data and not order:
            tmp = other1.data
            other1.data = other2.data
            other2.data = tmp

    def tri_LC_double(self, order=True):
        """
        Le paramètre order permet d'avoir une liste chainée croissante (order=True)
                            ou d'avoir une liste chainée décroissante (order=False)
        """
        if self.size == 0:
            return self
        tmp = self.head
        if order:
            while tmp != None:
                change = tmp
                mini = float("inf")
                while change != None:
                    if change.data < mini:
                        mini = change.data
                        pointeur = change
                    change = change.next
                LC_double.echanger(self, tmp, pointeur, order)
                tmp = tmp.next
        if not order:
            while tmp != None:
                change = tmp
                mini = -float("inf")
                while change != None:
                    if change.data > mini:
                        mini = change.data
                        pointeur = change
                    change = change.next
                LC_double.echanger(self, tmp, pointeur, order)
                tmp = tmp.next

    def best_LC_double(self, other):
        """
        Permet de comparer 2 listes entre elles (comparaison de chaque maillon) et de prendre le plus élevé.
        Crée une nouvelle liste chainée.
        """
        if self.size == 0:
            return other
        elif other.size == 0:
            return self
        else:
            tmp1 = self.head
            tmp2 = other.head
            new_LC = LC_double()
            while tmp1 != None and tmp2 != None:
                if tmp1.data >= tmp2.data:
                    LC_double.end_insert(new_LC, tmp1.data)
                else:
                    LC_double.end_insert(new_LC, tmp2.data)
                tmp1 = tmp1.next
                tmp2 = tmp2.next
            if tmp1 == None and tmp2 == None:
                return new_LC
            elif tmp1 == None:
                while tmp2 != None:
                    LC_double.end_insert(new_LC, tmp2.data)
                    tmp2 = tmp2.next
                return new_LC
            elif tmp2 == None:
                while tmp1 != None:
                    LC_double.end_insert(new_LC, tmp1.data)
                    tmp1 = tmp1.next
                return new_LC

    def worst_LC_double(self, other):
        """
        Permet de comparer 2 listes entre elles (comparaison de chaque maillon) et de prendre le moins élevé.
        Crée une nouvelle liste chainée.
        """
        if self.size == 0:
            return other
        elif other.size == 0:
            return self
        else:
            tmp1 = self.head
            tmp2 = other.head
            new_LC = LC_double()
            while tmp1 != None and tmp2 != None:
                if tmp1.data <= tmp2.data:
                    LC_double.end_insert(new_LC, tmp1.data)
                else:
                    LC_double.end_insert(new_LC, tmp2.data)
                tmp1 = tmp1.next
                tmp2 = tmp2.next
            if tmp1 == None and tmp2 == None:
                return new_LC
            elif tmp1 == None:
                while tmp2 != None:
                    LC_double.end_insert(new_LC, tmp2.data)
                    tmp2 = tmp2.next
                return new_LC
            elif tmp2 == None:
                while tmp1 != None:
                    LC_double.end_insert(new_LC, tmp1.data)
                    tmp1 = tmp1.next
                return new_LC

a = 21
b = 637
c = 1024
d = 2
e = 69
liste1 = LC_double()
liste2 = LC_double()
LC_double.end_insert(liste1, d)
LC_double.end_insert(liste1, a)
LC_double.end_insert(liste1, e)
LC_double.end_insert(liste1, c)
LC_double.end_insert(liste1, b)
LC_double.end_insert(liste2, b)
LC_double.end_insert(liste2, e)
LC_double.end_insert(liste2, a)
LC_double.end_insert(liste2, c)
LC_double.end_insert(liste2, e)

print(liste1)
print(liste2)