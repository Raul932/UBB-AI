import unittest
from graph import Graph
from iterator import VertexIterator

class TestGraph(unittest.TestCase):
    def setUp(self):
        self.graph = Graph(directed=True, weighted=False)
        self.graph.add_vertex()  # Vertex 0
        self.graph.add_vertex()  # Vertex 1
        self.graph.add_vertex()  # Vertex 2
        self.graph.add_edge(0, 1)  # Edge (0, 1)
        self.graph.add_edge(0, 2)  # Edge (0, 2)
        self.graph.add_edge(1, 2)  # Edge (1, 2)

    def test_add_vertex(self):
        self.graph.add_vertex()
        self.assertEqual(self.graph.get_n(), 4)

    def test_add_edge(self):
        self.graph.add_edge(1, 0)
        self.assertTrue(self.graph.is_edge(1, 0))

    def test_remove_edge(self):
        self.graph.remove_edge(endpoints=(0, 1))
        self.assertFalse(self.graph.is_edge(0, 1))

    def test_remove_vertex(self):
        self.graph.remove_vertex(2)
        self.assertFalse(self.graph.is_edge(0, 2))
        self.assertEqual(self.graph.get_n(), 2)

    def test_create_random(self):
        self.graph.create_random(5, 6)
        self.assertEqual(self.graph.get_n(), 5)
        self.assertLessEqual(self.graph.get_m(), 6)

    def test_get_n(self):
        self.assertEqual(self.graph.get_n(), 3)

    def test_get_m(self):
        self.assertEqual(self.graph.get_m(), 3)

    def test_deg(self):
        self.assertEqual(self.graph.deg(0), 2)
        self.assertEqual(self.graph.deg(1), 2)
        self.assertEqual(self.graph.deg(2), 2)

    def test_is_edge(self):
        self.assertTrue(self.graph.is_edge(0, 1))
        self.assertFalse(self.graph.is_edge(1, 0))

    def test_outbound_edges(self):
        self.assertEqual(self.graph.outbound_edges(0), [(0, 1), (0, 2)])

    def test_inbound_edges(self):
        self.assertEqual(self.graph.inbound_edges(2), [(0, 2), (1, 2)])

    def test_copy_graph(self):
        copied_graph = self.graph.copy_graph()
        self.assertEqual(copied_graph.get_n(), self.graph.get_n())
        self.assertEqual(copied_graph.get_m(), self.graph.get_m())


class TestVertexIterator(unittest.TestCase):
    def setUp(self):
        self.graph = Graph(directed=True, weighted=False)
        self.graph.add_vertex()  # Vertex 0
        self.graph.add_vertex()  # Vertex 1
        self.graph.add_vertex()  # Vertex 2
        self.graph.add_edge(0, 1)  # Edge (0, 1)
        self.graph.add_edge(0, 2)  # Edge (0, 2)
        self.iterator = VertexIterator(self.graph, start_vertex=0)

    def test_first(self):
        self.assertEqual(self.iterator.first(), 0)

    def test_get_current(self):
        self.assertEqual(self.iterator.get_current(), 0)

    def test_next(self):
        self.assertEqual(self.iterator.next(), 1)
        self.assertEqual(self.iterator.next(), 2)

    def test_valid(self):
        self.assertTrue(self.iterator.valid)

    def test_get_path_length(self):
        self.assertEqual(self.iterator.get_path_length(), 0)  # Path length from start vertex to itself

if __name__ == '__main__':
    unittest.main()
