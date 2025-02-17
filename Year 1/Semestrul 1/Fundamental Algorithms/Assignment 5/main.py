from patient import Patient
from department import Department, DepartmentRepository
from controller import *

def validate_age(age):
    try:
        age = int(age)
        if age < 0:
            raise ValueError("Age must be a non-negative integer.")
        return age
    except ValueError:
        raise ValueError("Invalid age input. Please enter a non-negative integer.")

def validate_string_input(input_string, field_name):
    if not input_string.strip():
        raise ValueError(f"{field_name} cannot be empty.")

def validate_department_id(dep_id, repository):
    try:
        dep_id = int(dep_id)
        if dep_id <= 0 or dep_id > len(repository.get_departments()):
            raise ValueError("Invalid department id.")
        return dep_id
    except ValueError:
        raise ValueError("Invalid department id. Please enter a positive integer.")

patient1 = Patient("John", "Doe", "502021190901", "Cough")
patient2 = Patient("Jane", "Smith", "5041110303706", "Cough")
patient3 = Patient("Alice", "Johnson", "192020212350", "Headache")
patient4 = Patient("Bob", "Williams", "5678901234", "Sore Throat")
patient5 = Patient("Eva", "Miller", "4321098765", "Back Pain")
patient6 = Patient("John", "Brown", "7890123456", "Migraine")
patient7 = Patient("Olivia", "Davis", "6543210987", "Cough")
patient8 = Patient("David", "Jones", "8765432109", "Sprained Ankle")
patient9 = Patient("Sophia", "Wilson", "2109876543", "Cough")
patient10 = Patient("William", "Moore", "1098765432", "Cough")

department1 = Department(1, "Internal Medicine", 30, [patient1, patient2, patient3])
department2 = Department(2, "Pediatrics", 25, [patient4, patient5, patient6])
department3 = Department(3, "Orthopedics", 20, [patient7, patient8, patient9, patient10])
department4 = Department(4, "Neurology", 15, [patient1, patient3, patient5, patient7])
department5 = Department(5, "Dermatology", 18, [patient2, patient4, patient6, patient8, patient10])
department6 = Department(6, "Cardiology", 22, [patient9])
department7 = Department(7, "Ophthalmology", 12, [patient1, patient3, patient7])
department8 = Department(8, "ENT", 16, [patient2, patient4, patient6, patient8])
department9 = Department(9, "Psychiatry", 10, [patient5, patient9, patient10])
department10 = Department(10, "Surgery", 28, [patient1, patient2, patient3, patient4, patient5, patient6])

repository = DepartmentRepository()
repository.add_department(department1)
repository.add_department(department2)
repository.add_department(department3)
repository.add_department(department4)
repository.add_department(department5)
repository.add_department(department6)
repository.add_department(department7)
repository.add_department(department8)
repository.add_department(department9)
repository.add_department(department10)

def print_menu():
    print("\n--- Hospital Management Menu ---")
    print("1. Display all departments and patients")
    print("2. Add a new department")
    print("3. Sort patients in a department by personal numerical code")
    print("4. Sort departments by the number of patients")
    print("5. Sort departments by the number of patients above a given age")
    print("6. Sort departments by the number of patients and patients' names alphabetically")
    print("7. Identify departments with patients under a given age")
    print("8. Identify patients with a given string in a department's first name or last name")
    print("9. Identify departments with patients having a given first name")
    print("10. Form groups of patients with the same disease in the same department")
    print("11. Form groups of departments with at most 𝒑 patients suffering from the same disease")
    print("12. Add a patient to a department")
    print("13. Delete a patient from a department")
    print("14. Delete a department")
    print("0. Exit")


while True:
    print_menu()
    choice = input("Enter your choice (0-14): ")

    if choice == "0":
        print("Exiting the program. Goodbye!")
        break

    elif choice == "1":
        print(repository)

    elif choice == "2":
        try:
            id = int(input("Enter department id: "))
            # Check if the ID already exists
            if any(dep.get_id() == id for dep in repository.get_departments()):
                raise ValueError("Department with the same ID already exists.")
            
            name = input("Enter department name: ")
            number_of_beds = int(input("Enter number of beds: "))
            validate_string_input(name, "Department name")
            if number_of_beds <= 0:
                raise ValueError("Number of beds must be a positive integer.")
            
            list_of_patients = []  # You can add patients here as needed
            new_department = Department(id, name, number_of_beds, list_of_patients)
            repository.add_department(new_department)
            print("Department added successfully.")
        except ValueError as e:
            print(f"Error: {e}")

    elif choice == "3":
        # Sort patients in a department by personal numerical code
        dep_id = int(input("Enter department id: "))
        department = repository.get_departments()[dep_id - 1]
        sorted_patients = sort_repo(department.get_list_of_patients(), Patient.get_cnp)
        for patients in sorted_patients:
            print(patients)

    elif choice == "4":
        # Sort departments by the number of patients
        sorted_departments = sort_repo(repository.get_departments(), Department.get_nr_of_patients)
        for department in sorted_departments:
            print(department)
        print("\n\n\n\n\n")

    elif choice == "5":
    # Sort departments by the number of patients above a given age with input validation
        try:
            age_limit = input("Enter age limit: ")
            age_limit = validate_age(age_limit)
            # Assuming CNP is a string containing the personal identification number
            filtered_departments = filter_repo(repository.get_departments(), lambda dep: any(int(str(patient.get_cnp())[1:7]) > age_limit for patient in dep.get_list_of_patients()))
            sorted_dep=sort_repo(filtered_departments, Department.get_nr_of_patients)
            for dep in sorted_dep:
                print(dep)
        except ValueError as e:
            print(f"Error: {e}")
    
    elif choice == "6":
    # Sort departments by the number of patients and patients in a department alphabetically
        sorted_departments = sort_repo(repository.get_departments(), lambda dep: (Department.get_nr_of_patients(dep), sorted([f"{patient.get_first_name()} {patient.get_last_name()}" for patient in dep.get_list_of_patients()])))
        for dep in sorted_departments:
            print(dep)

    elif choice == "7":
        # Identify departments with patients under a given age
        age_limit = int(input("Enter age limit: "))
        filtered_departments = filter_repo(repository.get_departments(), lambda dep: any(int(str(patient.get_cnp())[1:7]) < age_limit for patient in dep.get_list_of_patients()))
        for dep in filtered_departments:
            print(dep)

    elif choice == "8":
        # Identify patients with a given string in a department's first name or last name
        dep_id = int(input("Enter department id: "))
        search_string = input("Enter search string: ")
        department = repository.get_departments()[dep_id - 1]
        filtered_patients = filter_repo(department.get_list_of_patients(), lambda patient: search_string.lower() in patient.get_first_name().lower() or search_string.lower() in patient.get_last_name().lower())
        for patients in filtered_patients:
            print(patients)

    elif choice == "9":
        # Identify departments with patients having a given first name
        first_name = input("Enter first name: ")
        filtered_departments = filter_repo(repository.get_departments(), lambda dep: any(patient.get_first_name().lower() == first_name.lower() for patient in dep.get_list_of_patients()))
        for dep in filtered_departments:
            print(dep)

    elif choice == "10":
        # Form groups of patients with the same disease in the same department
        dep_id = int(input("Enter department id: "))
        k_value = int(input("Enter k value: "))
        department = repository.get_departments()[dep_id - 1]
        
        # Group patients by disease
        grouped_by_disease = {}
        for patient in department.get_list_of_patients():
            disease = patient.get_disease()
            if disease in grouped_by_disease:
                grouped_by_disease[disease].append(patient)
            else:
                grouped_by_disease[disease] = [patient]

        # Create combinations of patients with the same disease
        for disease, patients in grouped_by_disease.items():
            if len(patients) >= k_value:
                combinations = create_combinations(patients, k_value)
                print(f"Groups for disease '{disease}':")
                for group in combinations:
                    print([str(patient) for patient in group])
            


    elif choice == "11":
        # Form groups of departments with at most 𝒑 patients suffering from the same disease
        k_value = int(input("Enter k value: "))
        p_value = int(input("Enter p value: "))

        c = []

        valid_for_p = []

        for department in repository.get_departments():
            illness = ()
            patients = department.get_list_of_patients()

            for patient in patients:
                ill = patient.get_disease()
                if ill in illness:
                    illness[ill] += 1
                else:
                    illness.insert(ill,1)

            ok = True
            for ill in illness:
                if illness[ill] > p_value:
                    ok = False

            if ok:
                valid_for_p.append(department)

            print(valid_for_p)
        

 

        # Group departments by diseases
        grouped_by_disease = {}
        for department in repository.get_departments():
            diseases = set(patient.get_disease() for patient in department.get_list_of_patients())
            for disease in diseases:
                if disease in grouped_by_disease:
                    grouped_by_disease[disease].append(department)
                else:
                    grouped_by_disease[disease] = [department]

        print(group)
        # Filter groups with at most p_value departments
        valid_groups = [group for group in grouped_by_disease.values() if len(group) <= p_value]

        # Create combinations of k groups using backtracking
        combinations = create_combinations(list(valid_groups), k_value)

        # Print the formed groups with department names
        for group in combinations:
            print([department.get_name() for department in group])




    elif choice == "12":
        # Add a patient to a department
        dep_id = int(input("Enter department id: "))
        department = repository.get_departments()[dep_id - 1]

        # Gather patient information
        first_name = input("Enter patient's first name: ")
        last_name = input("Enter patient's last name: ")
        cnp = input("Enter patient's personal numerical code (CNP): ")
        disease = input("Enter patient's disease: ")

        # Create a new patient
        new_patient = Patient(first_name, last_name, cnp, disease)

        # Add the new patient to the specified department
        department.get_list_of_patients().append(new_patient)

        print(f"Patient {new_patient.get_first_name()} {new_patient.get_last_name()} added to department {department.get_name()}.")
    elif choice == "13":
        # Delete a patient from a department
        dep_id = int(input("Enter department id: "))
        department = repository.get_departments()[dep_id - 1]

        cnp_to_delete = int(input("Enter patient's CNP to delete: "))

        # Find the patient with the specified CNP and remove it
        patients = department.get_list_of_patients()
        for patient in patients:
            if patient.get_cnp() == cnp_to_delete:
                patients.remove(patient)
                print(f"Patient with CNP {cnp_to_delete} deleted from department {department.get_name()}.")
                break
        else:
            print(f"No patient found with CNP {cnp_to_delete} in department {department.get_name()}.")

    elif choice == "14":
        # Delete a department
        dep_id_to_delete = int(input("Enter department id to delete: "))

        # Find the department with the specified id and remove it
        departments = repository.get_departments()
        for department in departments:
            if department.get_id() == dep_id_to_delete:
                departments.remove(department)
                print(f"Department {department.get_name()} deleted.")
                break
        else:
            print(f"No department found with id {dep_id_to_delete}.")
    else:
        print("Invalid choice. Please enter a number between 0 and 11.")