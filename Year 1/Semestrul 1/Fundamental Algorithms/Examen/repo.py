from domain import *

class PaintingRepository:
    def __init__(self):
        self.__list_of_paintings=[]
    def get_all(self):
        for painting in self.__list_of_paintings:
            print(painting)
    def add_painting(self, name, title, start, end):
        '''
        Adds a painting to the repository
        input: name, title - str
               start, end - int
        '''
        if len(name)==0:
            while len(name)==0:
                name=input("Please add a name: ")
        if len(title)==0:
            while len(title)==0:
                title=input("Please add a title: ")
        if start<0:
            while start<0:
                start=int(input("Please add a valid starting price: "))
        if end<start:
            while end<start:
                end=int(input("Please add a valid ending price: "))
        self.__list_of_paintings.append(Painting(name, title, start, end))
    def get_average(self, name):
        '''
        Gets the average between prices
        Output: tuple
        '''
        a=0
        start_avg=0
        end_avg=0
        for painting in self.__list_of_paintings:
            if painting.get_name()==name:
                start_avg=painting.get_start()+start_avg
                end_avg=painting.get_end()+end_avg
                a=a+1
        return (start_avg/a, end_avg/a)
    def update_price(self, name, new_price):
        '''
        Updates the end price
        '''
        for painting in self.__list_of_paintings:
            if painting.get_title()==name:
                if new_price<painting.get_start():
                    return False
                else:
                    painting.set_end(new_price)
                    return True