import unittest
import math
import repository as repo
from point import MyPoint

class TestRepository(unittest.TestCase):

    def setUp(self):
        self.repository = repo.PointRepository()
        self.point1 = MyPoint(1, 2, "red")
        self.point2 = MyPoint(3, 4, "green")
        self.point3 = MyPoint(5, 6, "blue")
        self.point4 = MyPoint(7, 8, "yellow")
        self.point5 = MyPoint(9, 10, "magenta")

    #def test_add_points_and_get_points(self):
        #self.repository.add_points(self.point1)
        #self.assertIn(f"Point 0: (1, 2, red)", self.repository.get_points())
        #self.repository.add_points(self.point3)
        #self.assertIn(f"Point 0: (1, 2, red)\nPoint 1: (5, 6, blue)", self.repository.get_points())
    
    def test_get_point_by_index(self):
        self.repository.add_points(self.point3)
        self.assertEqual((5, 6, "blue"), self.repository.get_point_at_index(0))

    def test_points_of_color(self):
        self.repository.add_points(self.point1)
        self.repository.add_points(self.point2)
        self.repository.add_points(self.point3)
        self.repository.add_points(self.point4)
        self.repository.add_points(self.point5)
        self.assertEqual([(1, 2)], self.repository.points_of_color("red"))
        self.assertEqual([(9, 10)], self.repository.points_of_color("magenta"))
        self.assertNotEqual([(5, 6)], self.repository.points_of_color("yellow"))
        
    def test_delete_points(self):
        self.repository.add_points(self.point4)
        self.repository.delete_points(7, 8)
        self.assertNotIn(f"Point 0: (7, 8, yellow)", self.repository.get_points())

    def test_find_points_inside_square(self):
        self.repository.add_points(self.point5)
        points_inside_square = self.repository.find_points_inside_square(1, 10, 10)
        self.assertEqual(points_inside_square, [(9, 10)])
    
    def test_minimum_distance(self):
        self.repository.add_points(self.point1)
        self.repository.add_points(self.point2)
        self.repository.add_points(self.point3)
        min_distance = self.repository.minimum_distance()
        self.assertEqual(min_distance, math.sqrt(8))

    def test_update_point(self):
        self.repository.add_points(self.point1)
        self.repository.update_point(73, 27, "magenta", 0)
        self.assertIn(f"Point 0: (73, 27, magenta)", self.repository.get_points())
    
    def test_delete_by_index(self):
        self.repository.add_points(self.point2)
        self.repository.delete_by_index(0)
        self.assertNotIn(f"Point 0: (3, 4, green)", self.repository.get_points())

    def test_delete_inside_square(self):
        self.repository.add_points(self.point5)
        self.repository.delete_inside_square(8, 9, 5)
        self.assertEqual(self.repository.nr_of_points(), 1)

    def test_find_points_inside_circle(self):
        self.repository.add_points(self.point1)
        self.repository.add_points(self.point2)
        self.repository.add_points(self.point3)
        self.repository.add_points(self.point4)
        self.repository.add_points(self.point5)
        self.assertEqual(self.repository.find_points_inside_circle(0, 0, 37), [(1, 2, "red"), (3, 4, "green"), (5, 6, "blue"), (7, 8, "yellow"), (9, 10, "magenta")])

    def test_distance_between_two_points(self):
        self.assertAlmostEqual(self.repository.distance_between_two_points(self.point1, 0, 0), 2.236, 2)
        self.assertAlmostEqual(self.repository.distance_between_two_points(self.point2, 1, 4), 2, 2)
    
    def test_nr_of_points(self):
        self.repository.add_points(self.point1)
        self.assertEqual(self.repository.nr_of_points(), 1)
        self.repository.add_points(self.point5)
        self.assertEqual(self.repository.nr_of_points(), 2)

if __name__ == '__main__':
    unittest.main()
