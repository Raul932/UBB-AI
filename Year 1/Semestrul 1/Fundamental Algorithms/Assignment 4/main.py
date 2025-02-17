from vector import MyVector
from repository import VectorRepository
import unittest
from test import TestVectorRepository
vector_repo = VectorRepository()
vec1 = MyVector(1, 'r', 1, [1.2, 2.3, 3.4, 4.5])
vec2 = MyVector(2, 'g', 2, [2.5, 3.6, 4.7, 5.8])
vec3 = MyVector(3, 'b', 3, [3.8, 4.9, 5.0, 6.1])
vec4 = MyVector(4, 'y', 4, [1.0, 2.0, 3.0, 4.0])
vec5 = MyVector(5, 'm', 5, [2.0, 3.0, 4.0, 5.0])
vec6 = MyVector(6, 'r', 6, [3.0, 4.0, 5.0, 6.0])
vec7 = MyVector(7, 'g', 7, [1.5, 2.5, 3.5, 4.5])
vec8 = MyVector(8, 'b', 8, [2.5, 3.5, 4.5, 5.5])
vec9 = MyVector(9, 'y', 9, [3.5, 4.5, 5.5, 6.5])
vec10 = MyVector(10, 'm', 10, [1.8, 2.8, 3.8, 4.8])

vector_repo.add_vector(vec1)
vector_repo.add_vector(vec2)
vector_repo.add_vector(vec3)
vector_repo.add_vector(vec4)
vector_repo.add_vector(vec5)
vector_repo.add_vector(vec6)
vector_repo.add_vector(vec7)
vector_repo.add_vector(vec8)
vector_repo.add_vector(vec9)
vector_repo.add_vector(vec10)

suite=unittest.TestLoader().loadTestsFromTestCase(TestVectorRepository)


result=unittest.TextTestRunner().run(suite)

if result.wasSuccessful():
    print("All tests passed. Starting the main program.")
    print("--------------------------------")
else:
    print("Some tests failed.")
    exit()

def print_menu():
    print("Vector Repository Menu:")
    print("1. Add a vector")
    print("2. Get all vectors")
    print("3. Get vector at index")
    print("4. Update vector at index")
    print("5. Update vector by name_id")
    print("6. Delete vector at index")
    print("7. Delete vector by name_id")
    print("8. Plot all vectors")
    print("9. Get max vector with sum above value")
    print("10. Delete vectors with product above value")
    print("11. Update vector color by name_id")
    print("0. Exit")

def is_name_id_unique(name_id, vector_repo):
    for vector in vector_repo.get_all_vectors():
        if vector.get_name_id() == name_id:
            return False
    return True


def is_valid_color(color):
    valid_colors = ['r', 'g', 'b', 'y', 'm']
    return color in valid_colors

def create_vector_from_input():
    name_id = int(input("Enter name_id: "))
    color = input("Enter color: ")
    type = int(input("Enter type: "))
    values = [float(val) for val in input("Enter values (comma-separated): ").split(",")]
    return MyVector(name_id, color, type, values)

while True:
    print_menu()
    choice = input("Enter your choice (0-11): ")

    if choice == '0':
        print("Exiting the program. Goodbye!")
        break
    elif choice == '1':
        while True:
            name_id = int(input("Enter name_id: "))
            # Check if the name_id is unique within the existing vectors
            if not any(vector.get_name_id() == name_id for vector in vector_repo._VectorRepository__vectors):
                break
            else:
                print("Name_id must be unique. Please choose a different one.")
        while True:
            color = input("Enter color: ")
            if is_valid_color(color):
                break
            else:
                print("Invalid color. Please choose from ['r', 'g', 'b', 'y', 'm'].")
        type = int(input("Enter type: "))
        values = [float(val) for val in input("Enter values (comma-separated): ").split(",")]
        new_vector = MyVector(name_id, color, type, values)
        vector_repo.add_vector(new_vector)
        print("Vector added successfully.")
    elif choice == '2':
        print(vector_repo.get_all_vectors())
    elif choice == '3':
        index = int(input("Enter index: "))
        while index<0 or index>=vector_repo.get_vector_count():
            index=int(input("Enter a valid index: "))
        vector = vector_repo.get_vector_at_index(index)
        print(vector.__str__())
    elif choice == '4':
        index = int(input("Enter index: "))
        while index<0 or index>vector_repo.get_vector_count():
            index=int(input("Enter a valid index: "))
        while True:
            name_id = int(input("Enter name_id: "))
            if not any(vector.get_name_id() == name_id for vector in vector_repo._VectorRepository__vectors):
                break
            else:
                print("Name_id must be unique. Please choose a different one.")
        while True:
            color = input("Enter color: ")
            if is_valid_color(color):
                break
            else:
                print("Invalid color. Please choose from ['r', 'g', 'b', 'y', 'm'].")
        type = int(input("Enter type: "))
        values = [float(val) for val in input("Enter values (comma-separated): ").split(",")]
        new_vector = MyVector(name_id, color, type, values)
        vector_repo.update_vector_at_index(index, new_vector)
    elif choice == '5':
        while True:
            name_id = int(input("Enter name_id: "))
            # Check if the name_id exists in the repository
            if any(vector.get_name_id() == name_id for vector in vector_repo._VectorRepository__vectors):
                break
            else:
                print(f"Vector with name_id {name_id} does not exist. Please enter a valid name_id.")
        while True:
            color = input("Enter color: ")
            if is_valid_color(color):
                break
            else:
                print("Invalid color. Please choose from ['r', 'g', 'b', 'y', 'm'].")
        type = int(input("Enter type: "))
        values = [float(val) for val in input("Enter values (comma-separated): ").split(",")]
        new_vector = MyVector(name_id, color, type, values)
        vector_repo.update_vector_by_name_id(name_id, new_vector)
    elif choice == '6':
        index = int(input("Enter index: "))
        while index<0 or index>=vector_repo.get_vector_count():
            index=int(input("Enter a valid index: "))
        vector_repo.delete_vector_at_index(index)
    elif choice == '7':
        while True:
            name_id = int(input("Enter name_id: "))
            if any(vector.get_name_id() == name_id for vector in vector_repo._VectorRepository__vectors):
                break
            else:
                print(f"Vector with name_id {name_id} does not exist. Please enter a valid name_id.")
        vector_repo.delete_vector_by_name_id(name_id)
    elif choice == '8':
        vector_repo.plot_all_vectors()
    elif choice == '9':
        value = float(input("Enter value: "))
        max_vector = vector_repo.get_max_vectors_with_sum_above(value)
        if max_vector:
            print(max_vector.__str__())
    elif choice == '10':
        value = float(input("Enter value: "))
        vector_repo.delete_vectors_with_product_above(value)
    elif choice == '11':
        while True:
            name_id = int(input("Enter name_id: "))
            if any(vector.get_name_id() == name_id for vector in vector_repo._VectorRepository__vectors):
                break
            else:
                print(f"Vector with name_id {name_id} does not exist. Please enter a valid name_id.")
        while True:
            color = input("Enter color: ")
            if is_valid_color(color):
                break
            else:
                print("Invalid color. Please choose from ['r', 'g', 'b', 'y', 'm'].")
        vector_repo.update_vector_color_by_name_id(name_id, color)
    else:
        print("Invalid choice. Please enter a number between 0 and 11.")