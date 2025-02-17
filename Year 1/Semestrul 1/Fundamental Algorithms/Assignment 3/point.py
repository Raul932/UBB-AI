

class MyPoint:
    colors=["red","green","blue","yellow","magenta"]
    def __init__(self, x, y, color):
        self.__x=x
        self.__y=y
        self.__color=color
        if color not in self.colors:
            raise ValueError("Wrong color")

    def get_x(self):
        return self.__x
    
    def get_y(self):
        return self.__y
    
    def get_color(self):
        return self.__color
    
    def set_x(self, x):
        self.__x=x
    
    def set_y(self, y):
        self.__y=y
    
    def set_color(self, color):
        self.__color=color
        if color not in self.colors:
            raise ValueError("Wrong color")

    def __str__(self) -> str:
        return f'Point({self.__x}, {self.__y}) of color {self.__color}'
