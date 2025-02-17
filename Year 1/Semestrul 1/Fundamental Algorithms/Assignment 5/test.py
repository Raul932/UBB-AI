import unittest
from patient import Patient
from department import Department, DepartmentRepository
from controller import sort_repo, filter_repo, create_combinations, backtracking

class TestHospitalFunctions(unittest.TestCase):

    def setUp(self):
        # Create sample data for testing
        self.patient1 = Patient("John", "Doe", "1234567890", "Fever")
        self.patient2 = Patient("Jane", "Smith", "5987654321", "Cough")
        self.department1 = Department(1, "Internal Medicine", 30, [self.patient1, self.patient2])
        self.repository = DepartmentRepository()
        self.repository.add_department(self.department1)

    def test_sort_repo(self):
        sorted_patients = sort_repo(self.department1.get_list_of_patients(), Patient.get_cnp)
        self.assertEqual(sorted_patients, [self.patient1, self.patient2])

    def test_filter_repo(self):
        filtered_patients = filter_repo(self.department1.get_list_of_patients(), lambda patient: patient.get_first_name() == "John")
        self.assertEqual(filtered_patients, [self.patient1])

    def test_create_combinations(self):
        combinations = create_combinations([1, 2, 3], 2)
        self.assertEqual(combinations, [[1, 2], [1, 3], [2, 3]])

    def test_backtracking(self):
        combinations = []
        backtracking([1, 2, 3], 2, combinations, [], 0)
        self.assertEqual(combinations, [[1, 2], [1, 3], [2, 3]])

    def test_add_patient_to_department(self):
        new_patient = Patient("Alice", "Johnson", "3456789012", "Headache")
        self.department1.get_list_of_patients().append(new_patient)
        self.assertEqual(self.department1.get_list_of_patients(), [self.patient1, self.patient2, new_patient])

    def test_delete_department(self):
        dep_id_to_delete = 1
        departments = self.repository.get_departments()
        for department in departments:
            if department.get_id() == dep_id_to_delete:
                departments.remove(department)
                break
        self.assertEqual(self.repository.get_departments(), [])

if __name__ == '__main__':
    unittest.main()