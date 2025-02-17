import unittest
from vector import MyVector
from repository import VectorRepository

class TestVectorRepository(unittest.TestCase):

    def setUp(self):
        self.repo = VectorRepository()
        self.vec1= MyVector(0, 'r', 2, [1, 2, 3, 4, 5])
        self.vec2= MyVector(2, 'g', 1, [3, 5, 7, 10, 12, 48])
        self.vec3= MyVector(3, 'b', 7, [13, 54, 65, -8, 3, 2])
        self.message = "This function isn't working properly"
    def test_add_vector_and_vector_count(self):
        self.repo.add_vector(self.vec1)
        self.assertEqual(self.repo.get_vector_count(), 1, self.message)
        self.repo.add_vector(self.vec2)
        self.assertEqual(self.repo.get_vector_count(), 2, self.message)
        self.repo.add_vector(self.vec3)
        self.assertEqual(self.repo.get_vector_count(), 3, self.message)
    def test_get_all_vectors(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        expected_result = f"{str(self.vec1)}\n{str(self.vec2)}\n{str(self.vec3)}"
        self.assertEqual(self.repo.get_all_vectors(), expected_result, self.message)
    def test_get_vector_at_index(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        self.assertEqual(self.repo.get_vector_at_index(0), self.vec1, self.message)
        self.assertEqual(self.repo.get_vector_at_index(1), self.vec2, self.message)
        self.assertEqual(self.repo.get_vector_at_index(5), None, self.message)
    def test_update_vector_at_index(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        self.repo.update_vector_at_index(0, self.vec2)
        self.repo.update_vector_at_index(1, self.vec3)
        self.repo.update_vector_at_index(2, self.vec1)
        self.assertEqual(self.repo.get_vector_at_index(0).get_name_id(), 2, self.message)
        self.assertEqual(self.repo.get_vector_at_index(1).get_name_id(), 3, self.message)
        self.assertEqual(self.repo.get_vector_at_index(2).get_name_id(), self.vec1.get_name_id(), self.message)
    def test_update_vector_by_name_id(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        self.repo.update_vector_by_name_id(0, self.vec3)
        self.assertEqual(self.repo.get_vector_at_index(0).get_name_id(), 3, self.message)
    def test_delete_vector_at_index(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        self.assertEqual(self.repo.get_vector_count(), 3, self.message)
        self.repo.delete_vector_at_index(1)
        self.assertEqual(self.repo.get_vector_count(), 2, self.message)
        self.repo.delete_vector_at_index(25)
        self.assertEqual(self.repo.get_vector_count(), 2, self.message)
    def test_delete_vector_by_name_id(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        self.assertEqual(self.repo.get_vector_count(), 3, self.message)
        self.repo.delete_vector_by_name_id(0)
        self.assertEqual(self.repo.get_vector_count(), 2, self.message)
        self.repo.delete_vector_by_name_id(25)
        self.assertEqual(self.repo.get_vector_count(), 2, self.message)
    def test_get_max_vectors_with_sum_above(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        self.assertEqual(self.repo.get_max_vectors_with_sum_above(100), self.vec3, self.message)
        self.assertEqual(self.repo.get_max_vectors_with_sum_above(20), self.vec3, self.message)
        self.assertEqual(self.repo.get_max_vectors_with_sum_above(-5), self.vec3, self.message)
    def test_delete_vectors_with_product_above(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        self.repo.delete_vectors_with_product_above(800)
        self.assertEqual(self.repo.get_vector_count(), 2, self.message)
        self.repo.delete_vectors_with_product_above(10)
        self.assertEqual(self.repo.get_vector_count(), 1, self.message)
        self.repo.delete_vectors_with_product_above(-10000000)
        self.assertEqual(self.repo.get_vector_count(), 0, self.message)
    def test_update_vector_color_by_name_id(self):
        self.repo.add_vector(self.vec1)
        self.repo.add_vector(self.vec2)
        self.repo.add_vector(self.vec3)
        self.repo.update_vector_color_by_name_id(0, 'g')
        self.assertEqual(self.repo.get_vector_at_index(0).get_color(), 'g', self.message)
        self.repo.update_vector_color_by_name_id(2, 'r')
        self.assertNotEqual(self.repo.get_vector_at_index(1).get_color(), 'g', self.message)
        self.repo.update_vector_color_by_name_id(3, 'y')
        self.assertEqual(self.repo.get_vector_at_index(2).get_color(), 'y', self.message)
if __name__ == '__main__':
    unittest.main()