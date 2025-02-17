import random
import heapq
from collections import deque

class Graph:
    def __init__(self, directed, weighted):
        self.directed = directed
        self.weighted = weighted
        self.vertices = set()
        self.edges = []
        self.outbound_neighbors = {}
        self.inbound_neighbors = {}
        self.edge_weights = {}

    # O(1)
    def add_vertex(self):  # Complexity O(1)
        self.outbound_neighbors[len(self.vertices)] = set()
        self.inbound_neighbors[len(self.vertices)] = set()
        self.vertices.add(len(self.vertices))
    
    def add_n_vertices(self, n):
        for _ in range(n):
            self.add_vertex()

    # O(1)
    def add_edge(self, initial_vertex, terminal_vertex, weight=None):
        if initial_vertex not in self.vertices or terminal_vertex not in self.vertices:
            raise ValueError("Vertices do not exist")

        if self.is_edge(initial_vertex, terminal_vertex):
            raise ValueError("Edge already exists")

        if self.directed:
            self.edges.append((initial_vertex, terminal_vertex))
            if self.weighted and weight is not None:
                self.edge_weights[(initial_vertex, terminal_vertex)] = weight
            if terminal_vertex not in self.outbound_neighbors[initial_vertex]:
                self.outbound_neighbors[initial_vertex].add(terminal_vertex)
            if initial_vertex not in self.inbound_neighbors[terminal_vertex]:
                self.inbound_neighbors[terminal_vertex].add(initial_vertex)

        if not self.directed:
            self.edges.append((initial_vertex, terminal_vertex))
            self.edges.append((terminal_vertex, initial_vertex))
            if self.weighted and weight is not None:
                self.edge_weights[(initial_vertex, terminal_vertex)] = weight
                self.edge_weights[(terminal_vertex, initial_vertex)] = weight
            if terminal_vertex not in self.outbound_neighbors[initial_vertex]:
                self.outbound_neighbors[initial_vertex].add(terminal_vertex)
                self.outbound_neighbors[terminal_vertex].add(initial_vertex)
            if initial_vertex not in self.inbound_neighbors[terminal_vertex]:
                self.inbound_neighbors[terminal_vertex].add(initial_vertex)
                self.inbound_neighbors[initial_vertex].add(terminal_vertex)

    # O(m) where m is the number of edges
    def remove_edge(self, edge_id=None, endpoints=None):
        if edge_id:
            if edge_id not in self.edges:
                raise ValueError("Edge does not exist")
            initial_vertex, terminal_vertex = edge_id
        elif endpoints:
            initial_vertex, terminal_vertex = endpoints
            if (initial_vertex, terminal_vertex) not in self.edges:
                raise ValueError("Edge does not exist")
        else:
            raise ValueError("Either edge_id or endpoints must be provided")
        if edge_id:
            self.edges.remove(edge_id)
        elif endpoints:
            self.edges.remove((initial_vertex, terminal_vertex))
        self.outbound_neighbors[initial_vertex].remove(terminal_vertex)
        self.inbound_neighbors[terminal_vertex].remove(initial_vertex)
        if not self.directed:
            self.outbound_neighbors[terminal_vertex].remove(initial_vertex)
            self.inbound_neighbors[initial_vertex].remove(terminal_vertex)

        if self.weighted:
            if edge_id:
                del self.edge_weights[edge_id]
                if not self.directed:
                    del self.edge_weights[(terminal_vertex, initial_vertex)]
            elif endpoints:
                del self.edge_weights[(initial_vertex, terminal_vertex)]
                if not self.directed:
                    del self.edge_weights[(terminal_vertex, initial_vertex)]

    # O(n + m) where n is the number of vertices and m is the number of edges
    def remove_vertex(self, vertex):
        if vertex not in self.vertices:
            raise ValueError("Vertex does not exist")
        self.vertices.remove(vertex)
        self.edges = [edge for edge in self.edges if vertex not in edge]
        self.outbound_neighbors[vertex] = []
        self.inbound_neighbors[vertex] = []
        for i in range(len(self.inbound_neighbors)):
            self.inbound_neighbors[i] = [neighbor for neighbor in self.inbound_neighbors[i] if neighbor != vertex]
        if not self.directed:
            for i in range(len(self.outbound_neighbors)):
                self.outbound_neighbors[i] = [neighbor for neighbor in self.outbound_neighbors[i] if neighbor != vertex]
    
    #O(e) e, is the nr of edges
    def random_weights(self, weights_range):
        if not self.edges:
            raise ValueError("Graph has no edges")
        if not weights_range or len(weights_range) != 2:
            raise ValueError("Invalid weights range")

        if not self.weighted:
            self.weighted = True
            self.edge_weights = {}

        for edge in self.edges:
            weight = random.randint(weights_range[0], weights_range[1])
            self.edge_weights[edge] = weight

    #O(n+m+m)
    def prims_algorithm(graph):
        if not graph.vertices:
            return None
        
        mst = Graph(graph.directed, graph.weighted)
        for _ in range(len(graph.vertices)):
            mst.add_vertex()
        
        start_vertex = next(iter(graph.vertices))
        edge_heap = []
        visited = set([start_vertex])
        
        for neighbor in graph.outbound_neighbors[start_vertex]:
            edge_weight = graph.edge_weights[(start_vertex, neighbor)]
            heapq.heappush(edge_heap, (edge_weight, start_vertex, neighbor))
        
        while edge_heap:
            weight, frm, to = heapq.heappop(edge_heap)
            if to not in visited:
                visited.add(to)
                mst.add_edge(frm, to, weight)
                for neighbor in graph.outbound_neighbors[to]:
                    if neighbor not in visited:
                        edge_weight = graph.edge_weights[(to, neighbor)]
                        heapq.heappush(edge_heap, (edge_weight, to, neighbor))
        
        return mst
    
    #O(m)
    def calculate_tree_height(tree, start_vertex):
        if not tree.vertices:
            return -1
        
        start_vertex = next(iter(tree.vertices))
        queue = deque([(start_vertex, 0)])  # (vertex, current_height)
        visited = set()
        max_height = 0
        
        while queue:
            current_vertex, height = queue.popleft()
            max_height = max(max_height, height)
            visited.add(current_vertex)
            for neighbor in tree.outbound_neighbors[current_vertex]:
                if neighbor not in visited:
                    queue.append((neighbor, height + 1))
        
        return max_height

    #O(m)
    def is_eulerian(self):
        for vertex in self.vertices:
            if len(self.outbound_neighbors[vertex]) % 2 != 0:
                return False
        return True

    #O(m+n)
    def find_eulerian_circuit(self):
        if not self.is_eulerian():
            return None 

        stack = []
        current_vertex = next((v for v in self.vertices if self.outbound_neighbors[v]), None)
        stack.append(current_vertex)
        current_path = []

        while stack:
            if self.outbound_neighbors[current_vertex]:
                stack.append(current_vertex)
                next_vertex = self.outbound_neighbors[current_vertex].pop()
                self.outbound_neighbors[next_vertex].remove(current_vertex)
                current_vertex = next_vertex
            else:
                current_path.append(current_vertex)
                current_vertex = stack.pop()

        return current_path[::-1]  # Reverse to get the correct order

    # O(n^2)
    def create_random(self, n, m, directed=True, weights_range=None):
        if m > n * (n - 1):
            raise ValueError("Too many edges for the number of vertices")
        for i in range(n):
            self.add_vertex()
        edges = set()
        while len(edges) < m:
            initial_vertex = random.choice(self.vertices)
            terminal_vertex = random.choice(self.vertices)
            if initial_vertex != terminal_vertex and (initial_vertex, terminal_vertex) not in edges:
                if directed:
                    weight = None
                    if weights_range is not None:
                        weight = random.randint(weights_range[0], weights_range[1])
                    edges.add((initial_vertex, terminal_vertex))
                    self.add_edge(initial_vertex, terminal_vertex, weight)
                else:  # For undirected graph
                    edges.add((initial_vertex, terminal_vertex))
                    self.add_edge(initial_vertex, terminal_vertex)
    # O(1)
    def get_n(self):
        return len(self.vertices)

    # O(1)
    def get_m(self):
        return len(self.edges)
    
    def set_weight(self, initial_vertex, terminal_vertex, weight, directed):
        if not self.weighted:
            raise ValueError("Graph is not weighted")
        if (initial_vertex, terminal_vertex) not in self.edges:
            raise ValueError("Edge does not exist")
        if self.directed:
            self.edge_weights[(initial_vertex, terminal_vertex)] = weight
        else:
            self.edge_weights[(initial_vertex, terminal_vertex)] = weight
            self.edge_weights[(terminal_vertex, initial_vertex)] = weight


    # O(1)
    def deg(self, x, degree_type='total'):
        if x not in self.vertices:
            raise ValueError("Vertex does not exist")
        if degree_type == 'total':
            return len(self.outbound_neighbors[x])+len(self.inbound_neighbors[x])
        elif degree_type == 'in':
            return len(self.inbound_neighbors[x])
        elif degree_type == 'out':
            return len(self.outbound_neighbors[x])
        else:
            raise ValueError("Invalid degree type")

    # O(E) where E is the number of edges
    def is_edge(self, vertex1, vertex2):
        #if vertex1 not in self.vertices or vertex2 not in self.vertices:
            #raise ValueError("Vertices do not exist")
        for edge in self.edges:
            if edge == (vertex1, vertex2):
                return True
        return False
    
    # Theta(o) where o is the number of outbound edges
    def outbound_edges(self, vertex):
        if vertex not in self.vertices:
            raise ValueError("Vertex does not exist")
        
        outbound_edges = []
        for neighbor in self.outbound_neighbors[vertex]:
            outbound_edges.append((vertex, neighbor))
        
        return outbound_edges

    # Theta(i) where i is the number of inbound edges
    def inbound_edges(self, vertex):
        if vertex not in self.vertices:
            raise ValueError("Vertex does not exist")
        
        inbound_edges = []
        for neighbor in self.inbound_neighbors[vertex]:
            inbound_edges.append((neighbor, vertex))
        
        return inbound_edges

    # O(n^2)
    def copy_graph(self):
        new_graph = Graph(self.directed, self.weighted) 
        for vertex in self.vertices:
            new_graph.add_vertex()
        for edge in self.edges:
            new_graph.add_edge(edge[0], edge[1])
        return new_graph
    
    @classmethod
    def create_from_file(cls, filename):
        start_vertex=None
        with open(filename, 'r') as file:
            # directed and weighted
            n, m, directed, weighted = file.readline().strip().split()
            n, m = int(n), int(m)
            directed = True if directed == 'T' else False
            weighted = True if weighted == 'T' else False
            
            # new graph
            graph = cls(directed, weighted)

            # vertices
            for i in range(n):
                graph.add_vertex()

            # edges
            for _ in range(m):
                line = file.readline().strip().split()
                if start_vertex==None:
                    start_vertex = int(line[0])
                initial_vertex, terminal_vertex = int(line[0]), int(line[1])
                graph.add_edge(initial_vertex, terminal_vertex)

                if weighted and directed:
                    weight = int(line[2])
                    graph.edge_weights[(initial_vertex, terminal_vertex)] = weight
                if weighted and not directed:
                    weight = int(line[2])
                    graph.edge_weights[(initial_vertex, terminal_vertex)] = weight
                    graph.edge_weights[(terminal_vertex, initial_vertex)] = weight
        return graph, start_vertex
    
    def greedy_coloring(graph):
        result = [-1] * len(graph.vertices)

        available = [True] * len(graph.vertices)
        
        result[0] = 0

        for vertex in range(1, len(graph.vertices)):
            for neighbor in graph.outbound_neighbors[vertex]:
                if result[neighbor] != -1:
                    available[result[neighbor]] = False
            
            cr = 0
            while cr < len(graph.vertices):
                if available[cr]:
                    break
                cr += 1

            result[vertex] = cr 
            
            for neighbor in graph.outbound_neighbors[vertex]:
                if result[neighbor] != -1:
                    available[result[neighbor]] = True

        chromatic_number = max(result) + 1
        return chromatic_number, result
    

    def __str__(self):
        graph_str = "Vertices: {}\n".format(self.vertices)
        graph_str += "Edges:\n"
        for i, edge in enumerate(self.edges, start=1):
            graph_str += "Edge {}: {} -> {}\n".format(i, edge[0], edge[1])
            if self.weighted:
                graph_str += "    Weight: {}\n".format(self.edge_weights.get(edge, "N/A"))
        return graph_str