import numpy as np

class MyVector:
    colors = ['r', 'g', 'b', 'y', 'm']

    def __init__(self, name_id, color, type, values):
        self.__name_id = name_id
        self.__color = color
        self.__type = type
        self.__values = np.array(values) 
        if color not in self.colors:
            raise ValueError("Wrong color")

    def get_name_id(self):
        return self.__name_id

    def set_name_id(self, name_id):
        self.__name_id = int(name_id)

    def get_color(self):
        return self.__color

    def set_color(self, color):
        self.__color = color

    def get_type(self):
        return self.__type

    def set_type(self, type):
        self.__type = type

    def get_values(self):
        return self.__values.tolist() 

    def set_values(self, values):
        self.__values = np.array(values)

    def add_scalar(self, scalar):
        self.__values += scalar 

    def add_vectors(self, vectors):
        for vector in vectors:
            if self.get_name_id() == vector.get_name_id():
                raise ValueError(f"Vector with name_id {self.get_name_id()} already exists.")
        self.__values += np.array(vector.get_values())

    def subtract_vectors(self, vec2):
        if len(self.__values) == len(vec2.get_values()):
            return (self.__values - np.array(vec2.get_values())).tolist()  #convert back to a list
        else:
            print("The vectors have different dimensions")

    def multiply_vectors(self, vec2):
        if len(self.__values) == len(vec2.get_values()):
            return np.dot(self.__values, np.array(vec2.get_values()))
        else:
            print("The vectors have different dimensions")

    def sum_elements(self):
        return np.sum(self.__values)

    def product_elements(self):
        return np.prod(self.__values)

    def calculate_average(self):
        if not self.__values.size:
            return 0  #avoid division by zero if the vector is empty
        return np.mean(self.__values)

    def find_min_element(self):
        if not self.__values.size:
            return None  #return None for an empty vector
        return np.min(self.__values)

    def find_max_element(self):
        if not self.__values.size:
            return None  #return None for an empty vector
        return np.max(self.__values)

    def __str__(self):
        return f"ID: {self.get_name_id()}, color: {self.get_color()}, type: {self.get_type()}, values: {self.get_values()}"

    def __eq__(self, other):
        if isinstance(other, MyVector):
            return (
                self.get_name_id() == other.get_name_id() and
                self.get_color() == other.get_color() and
                self.get_type() == other.get_type() and
                self.get_values() == other.get_values()
            )
        return False