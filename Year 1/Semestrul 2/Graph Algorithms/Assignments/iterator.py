class VertexIterator:
    def __init__(self, graph, start_vertex):
        self.graph = graph
        self.stack = [start_vertex]
        self.start_vertex = start_vertex
        self.visited = set()
        self.valid_iter = True
        self.path = []

    #O(1)
    def first(self):
        self.visited = set()
        self.valid_iter = True
        self.stack = [self.start_vertex]
        self.path = [self.start_vertex]

    #O(1)
    def get_current(self):
        if not self.valid():
            raise ValueError("Iterator is invalid")
        return self.stack[-1]

    #O(n)
    def next(self):
        current = self.stack[-1]
        if(current in self.visited):
            self.valid_iter = False
            self.stack.pop()
            return None
        self.visited.add(current)
        self.path.append(current)
        self.stack.pop()
        if current in self.graph.vertices:
            for v in self.graph.outbound_neighbors[current]:
                if(v not in self.visited):
                    self.stack.append(v)
        while(len(self.stack) != 0 and self.stack[-1] in self.visited):
            self.stack.pop()
        if(len(self.stack) == 0):
            self.valid_iter = False

    #O(1)
    def valid(self):
        return self.valid_iter
    
    #O(1)
    def get_path(self):
        return self.path