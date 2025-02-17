from patient import Patient

class Department:
    
    def __init__(self, id, name, number_of_beds, list_of_patients):
        self.__id = id
        self.__name = name
        self.__number_of_beds = number_of_beds
        self.__list_of_patients = list_of_patients
    
    def get_id(self):
        return self.__id
    
    def get_name(self):
        return self.__name
    
    def get_number_of_beds(self):
        return self.__number_of_beds
    
    def get_list_of_patients(self):
        return self.__list_of_patients
    
    def set_id(self, new_id):
        self.__id = new_id
    
    def set_name(self, new_name):
        self.__name = new_name
    
    def set_number_of_beds(self, new_number_of_beds):
        self.__number_of_beds = new_number_of_beds
    
    def set_list_of_patients(self, new_list_of_patients):
        self.__list_of_patients = new_list_of_patients
    
    def get_nr_of_patients(self):
        return len(self.__list_of_patients)
    
    def __str__(self):
        return f'\nDepartment {self.__id} of name: {self.__name}\n{self.__number_of_beds} beds\nPatients: ' + '\n' + '\n'.join(f'{patient.get_first_name()} {patient.get_last_name()} CNP: {patient.get_cnp()} suffering of: {patient.get_disease()}\n-------------' for patient in self.get_list_of_patients())

class DepartmentRepository:
    def __init__(self):
        self.__departments=[]

    def set_departments(self, new_departments):
        self.__departments = new_departments

    def get_departments(self):
        return self.__departments
    
    def add_department(self, new_department):
        self.__departments.append(new_department)

    def remove_department(self, department):
        self.__departments.remove(department)

    def update_department(self, id, name, nr_beds, patients):
        for department in self.__departments:
            if department.get_id()==id:
                department.set_name(name)
                department.set_number_of_beds(nr_beds)
                department.set_list_of_patients(patients)
                
    def __str__(self):
        return '\n'.join(f'{str(department)}' for department in self.__departments)
     