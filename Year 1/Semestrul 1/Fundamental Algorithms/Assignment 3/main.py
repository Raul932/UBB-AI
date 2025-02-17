import matplotlib.pyplot as plt
import unittest
import repository as repo
from test import TestRepository
from point import MyPoint




def print_menu():
    print("1. Add a point to the repository")
    print("2. Get all points")
    print("3. Get a point at a given index")
    print("4. Get points of a given color")
    print("5. Find points inside a square")
    print("6. Get the minimum distance between two points")
    print("7. Update a point at a given index")
    print("8. Delete a point by index")
    print("9. Delete all points that are inside a given square")
    print("10. Plot points")
    print("11. Delete a point by coordinates")
    print("12. Display all points that are inside of a circle")
    print("13. Get the number of points of a given color")
    print("14. Exit")
    print("--------------------------------")



suite=unittest.TestLoader().loadTestsFromTestCase(TestRepository)


result=unittest.TextTestRunner().run(suite)

if result.wasSuccessful():
    print("All tests passed. Starting the main program.")
    print("--------------------------------")
else:
    print("Some tests failed.")
    exit()

point1 = MyPoint(1, 2, "red")
point2 = MyPoint(3, 4, "green")
point3 = MyPoint(5, 6, "blue")
point4 = MyPoint(7, 8, "yellow")
point5 = MyPoint(9, 10, "magenta")
point6 = MyPoint(11, 12, "red")
point7 = MyPoint(13, 14, "green")
point8 = MyPoint(15, 16, "blue")
point9 = MyPoint(17, 18, "yellow")
point10 = MyPoint(19, 20, "magenta")

repository= repo.PointRepository()

repository.add_points(point1)
repository.add_points(point2)
repository.add_points(point3)
repository.add_points(point4)
repository.add_points(point5)
repository.add_points(point6)
repository.add_points(point7)
repository.add_points(point8)
repository.add_points(point9)
repository.add_points(point10)




if __name__=="__main__":
    while True:
        print_menu()

        option = int(input("Enter your choice (1-14): "))

        if option==1:
            x=int(input("Enter the x coordinate: "))
            y=int(input("Enter the y coordinate: "))
            color=input("Choose a color(red, green, blue, yellow, magenta): ")
            while color!="red" and color!="green" and color!="blue" and color!="yellow" and color!="magenta":
                color=input("Wrong color! Choose one of the given options: ")
            
            new_point=MyPoint(x, y, color)
            repository.add_points(new_point)
            print("--------------------------------")

        elif option==2:
            print(repository.get_points())
            print("--------------------------------")

        elif option==3:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            index=int(input("Enter the index: "))
            while index<0 or index>=repository.nr_of_points():
                index=int(input("Wrong value! Enter a good one: "))

            print(repository.get_point_at_index(index))
            print("--------------------------------")

        elif option==4:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            color=input("Enter color(red, green, blue, yellow, magenta): ")
            while color!="red" and color!="green" and color!="blue" and color!="yellow" and color!="magenta":
                color=input("Wrong color! Choose one of the given options: ")

            print(repository.points_of_color(color))
            print("--------------------------------")

        elif option==5:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            print("Give the following values for the square: ")
            upleft_x=int(input("Up-left x coordinate: "))
            upleft_y=int(input("Up-left y coordinate: "))
            length=int(input("Length: "))

            while length<=0:
                length=int(input("Give a correct length: "))
            print(repository.find_points_inside_square(upleft_x, upleft_y, length))
            print("--------------------------------")

        elif option==6:
            if repository.nr_of_points()<2:
                print("You don't have enough points to perform this operation.")
            else:
                print(f"The minimum distance between two given points is: {repository.minimum_distance()}")
            print("--------------------------------")
        elif option==7:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            index=int(input("Enter the index: "))
            while index<0 or index>=repository.nr_of_points():
                index=int(input("Wrong index! Enter a good one: "))

            x=int(input("x: "))
            y=int(input("y: "))
            color=input("Enter color(red, green, blue, yellow, magenta): ")
            while color!="red" and color!="green" and color!="blue" and color!="yellow" and color!="magenta":
                color=input("Wrong color! Choose one of the given options: ")

            repository.update_point(x, y, color, index)
            print("Point updated! Your new list of points is: ")
            print(repository.get_points())
            print("--------------------------------")

        elif option==8:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            index=int(input("Enter the index: "))
            while index<0 or index>=repository.nr_of_points():
                index=int(input("Wrong index! Enter a good one: "))

            repository.delete_by_index(index)
            print("Point deleted! Here is your new list: ")
            print(repository.get_points())
            print("--------------------------------")

        elif option==9:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            print("Give the following values for the square: ")
            upleft_x=int(input("Up-left x coordinate: "))
            upleft_y=int(input("Up-left y coordinate: "))
            length=int(input("Length: "))
            while length<=0:
                length=int(input("Give a correct length: "))

            repository.delete_inside_square(upleft_x, upleft_y, length)
            print("Points deleted! Your new list of points is: ")
            print(repository.get_points())
            print("--------------------------------")

        elif option==10:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            repository.plot_points()
            print("--------------------------------")

        elif option==11:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            print("Enter the coordinates: ")
            x=int(input("x: "))
            y=int(input("y: "))

            repository.delete_points(x, y)
            print("Points deleted! Your new list of points is: ")
            print(repository.get_points())
            print("--------------------------------")

        elif option==12:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            print("Enter the coords for the center of the circle: ")
            center_x=int(input("x: "))
            center_y=int(input("y: "))
            radius=int(input("Enter the radius: "))
            while radius<=0:
                radius=int(input("Enter a correct radius: "))

            print(repository.find_points_inside_circle(center_x, center_y, radius))
            print("--------------------------------")

        elif option==13:
            if not repository.nr_of_points():
                print("You do not have enough points for this.")
                continue

            color=input("Enter color(red, green, blue, yellow, magenta): ")
            while color!="red" and color!="green" and color!="blue" and color!="yellow" and color!="magenta":
                color=input("Wrong color! Choose one of the given options: ")

            print(len(repository.points_of_color(color)))
            print("--------------------------------")

        elif option==14:
            break

        else:
            while option<1 or option >14:
                option=int(input("Choose an option(1-14): "))
            