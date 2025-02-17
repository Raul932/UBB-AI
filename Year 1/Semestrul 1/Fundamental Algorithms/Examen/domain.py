class Painting:
    def __init__(self, name, title, start, end):
        self.__name=name
        self.__title=title
        self.__start=start
        self.__end=end
    def get_name(self):
        return self.__name
    def get_title(self):
        return self.__title
    def get_start(self):
        return self.__start
    def get_end(self):
        return self.__end
    def set_name(self, name):
        self.__name=name
    def set_title(self, title):
        self.__title=title
    def set_start(self, start):
        self.__start=start
    def set_end(self, end):
        self.__end=end
    def __str__(self):
        return f"{self.get_title()} by {self.get_name()} starting at {self.get_start()} lei ending at {self.get_end()} lei"