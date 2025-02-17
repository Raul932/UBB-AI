class Workout:
    def __init__(self, id, name, type, diff, duration):
        '''
        Description: Initializes the class
        '''
        self.__id=id
        self.__name=name
        self.__type=type
        self.__diff=diff
        self.__duration=duration
    def get_id(self):
        return self.__id
    def get_name(self):
        return self.__name
    def get_type(self):
        return self.__type
    def get_diff(self):
        return self.__diff
    def get_duration(self):
        return self.__duration
    def set_id(self, new_id):
        self.__id=new_id
    def set_name(self, new_name):
        self.__name=new_name
    def set_type(self, new_type):
        self.__type=new_type
    def set_diff(self, new_diff):
        self.__diff=new_diff
    def set_duration(self, new_duration):
        self.__duration=new_duration
    def __str__(self):
        print(f"{self.get_id()} {self.get_name()} {self.get_type()} {self.get_diff()} {self.get_duration()}")
