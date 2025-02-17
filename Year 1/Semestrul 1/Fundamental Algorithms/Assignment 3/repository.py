import math
import matplotlib.pyplot as plt

class PointRepository:

    def __init__(self):
        self.__points=[]

    def add_points(self, point):
        '''
        Description: Adds a point to the class
        '''
        self.__points.append(point)

    def get_points(self):
        '''
        Description: Gets the points from a class
        Output: The points that are in the class, separated by a new line
        '''
        points = []
        for i, point in enumerate(self.__points, start=0):
            points.append(f"Point {i}: ({point.get_x()}, {point.get_y()}, {point.get_color()})")
        return "\n".join(points)
    
    def get_point_at_index(self, index):
        '''
        Description: Gets the point at a given index
        Input: index- int
        Output: The point at the index you gave
        '''
        point=(self.__points[index].get_x(), self.__points[index].get_y(), self.__points[index].get_color())
        return point
    
    def points_of_color(self, color):
        '''
        Description: Searches for points of the given color and returns them
        Input: Color- string
        Output: The points of your chosen color
        '''
        points_color=[(point.get_x(), point.get_y()) for point in self.__points if point.get_color()==color] #if the color is good, we get the x and y coords from the points
        return points_color
    
    def delete_points(self, x, y):
        '''
        Description: Deletes a point
        Input: x, y- coordinates of the point that you want to delete(int)
        '''
        self.__points = [point for point in self.__points if not (point.get_x()==x and point.get_y()==y)] #we overwrite the list of points with the points that we want to keep
    
    def find_points_inside_square(self, upleft_x, upleft_y, length):
        '''
        Description: Finds all the points inside of a square based on the upleft coordinates and the length
        Input: upleft_x, upleft_y, length- int
        Output: The points inside the square (list)
        '''
        x1=upleft_x+length
        y1=upleft_y-length
        #search if we have any points included in the square
        points=[(point.get_x(), point.get_y()) for point in self.__points if point.get_x()>=upleft_x and point.get_x()<=x1 and point.get_y()>=y1 and point.get_y()<=upleft_y]
        return points
    
    def minimum_distance(self):
        '''
        Description: Computes the minimum distance between two points in our class
        Output: minimum- real number
        '''
        minimum=9999
        for index1 in range(len(self.__points)):
            for index2 in range(index1+1, len(self.__points)):
                #we use the formula for the distance between two points
                d=((self.__points[index2].get_x()-self.__points[index1].get_x())**2+(self.__points[index2].get_y()-self.__points[index1].get_y())**2)**0.5
                if d<minimum:
                    minimum=d
        return minimum
    
    def update_point(self, new_x, new_y, new_color,  index):
        '''
        Description: Updates a point at a given index
        Input: new_x, new_y, index - integers
               new_color - string
        Output: The list with the updated point
        '''
        self.__points[index].set_x(new_x)
        self.__points[index].set_y(new_y)
        self.__points[index].set_color(new_color)
        return self.__points
    
    def delete_by_index(self, index):
        '''
        Description: Deletes a point at a given index
        Input: index- int
        '''
        self.__points.pop(index)
    
    def delete_inside_square(self, upleft_x, upleft_y, length):
        '''
        Description: Deletes all the points found in a square with given upleft coordinates and length
        Input: upleft_x, upleft_y, length - ints
        '''
        points_inside = self.find_points_inside_square(upleft_x, upleft_y, length)
        for point in points_inside:
            self.__points = [p for p in self.__points if p != point]
    
    def plot_points(self):
        '''
        Description: Plots all the points from our class using matplotlib
        '''
        for point in self.__points:
            plt.scatter(point.get_x(), point.get_y(), c=point.get_color())
        plt.show()
        return None
    
    def find_points_inside_circle(self, center_x, center_y, radius):
        '''
        Description: Searches for points that are inside of a circle with a given center and radius
        Input: center_x, center_y, radius - ints
        Output: Empty list if there are no points inside the circle, or the points otherwise
        '''
        points_inside= []
        for point in self.__points:
            if self.distance_between_two_points(point, center_x, center_y) <= radius: #basically if the distance between the center and the point is smaller than the radius, the point is inside the circle, and it stores the point in a new list
                points_inside.append((point.get_x(), point.get_y(), point.get_color()))
        return points_inside
    
    def distance_between_two_points(self, point, center_x, center_y):
        '''
        Description: Computes the distance between two points
        Input: point- tuple with coord x, y and a color
               center_x, center_y- ints
        Output: Distance- Real number
        '''
        distance = math.sqrt((point.get_x()-center_x)**2 + (point.get_y() - center_y)**2)#formula for the dist between two points
        return distance
    
    def nr_of_points(self):
        '''
        Description: Computes the number of points in our class
        Output: The number of points
        '''
        return len(self.__points)
    
    def __str__(self):
        return "\n".join(str(point) for point in self.__points)
    