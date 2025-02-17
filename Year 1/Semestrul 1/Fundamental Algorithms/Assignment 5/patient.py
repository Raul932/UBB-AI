class Patient:
    def __init__(self, first_name, last_name, cnp, disease):
        self.__first_name=first_name
        self.__last_name=last_name
        self.__cnp=int(cnp)
        self.__disease=disease
    def get_first_name(self):
        return self.__first_name
    def get_last_name(self):
        return self.__last_name
    def get_cnp(self):
        return self.__cnp
    def get_disease(self):
        return self.__disease
    def set_first_name(self, first_name):
        self.__first_name=first_name
    def set_last_name(self, last_name):
        self.__last_name=last_name
    def set_cnp(self, cnp):
        self.__cnp=cnp
    def set_disease(self, disease):
        self.__disease=disease
    def __str__(self):
        return (f"{self.get_first_name()} {self.get_last_name()} {self.get_cnp()} Disease: {self.get_disease()}")

