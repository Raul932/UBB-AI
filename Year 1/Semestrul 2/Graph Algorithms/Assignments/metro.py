import heapq
from graph import Graph


class MetroSystem(Graph):

    def __init__(self, nr_nodes=0):
        Graph.__init__(self, False, True)
        self.edge_metro_line = {}
        self.add_n_vertices(nr_nodes)
        self.locations = {}

    def read_metro_lines_from_file(self, file_name):
        with open(file_name, "r") as file:
            for line in file:
                if line == "\n":
                    continue
                line = line.strip().split(",")
                metro_line = line[0]
                from_station = int(line[1].strip("()").split(";")[0][1:])
                for i in range(2, len(line)):
                    info = line[i].strip("()").split(";")
                    to_station = int(info[0][1:])
                    weight = int(info[1])
                    if not self.is_edge(from_station, to_station):
                        self.add_edge(from_station, to_station, weight)
                        self.edge_metro_line[(from_station, to_station)] = []
                        self.edge_metro_line[(from_station, to_station)].append(metro_line)
                    from_station = to_station

    def get_physical_locations_from_file(self, file_name):
        with open(file_name, "r") as file:
            for line in file:
                if line == "\n":
                    continue
                line = line.strip().split()
                station = int(line[0][1:])
                x, y = line[1].split(",")
                self.locations[station] = (int(x), int(y))

    def distance_squared(self, node1, node2):
        x1, y1 = self.locations[node1]
        x2, y2 = self.locations[node2]
        return (x1 - x2) ** 2 + (y1 - y2) ** 2

    def reconstruct_path(self, came_from, start, goal):
        current = goal
        path = [current]
        while current != start:
            current = came_from[current]
            path.append(current)
        path.reverse()
        return path

    def find_route_bfs_greedy(self, start, end):
        visited = [False] * len(self.vertices)
        heap = []
        came_from = {}
        heapq.heappush(heap, (self.distance_squared(start, end), start))
        visited[start] = True
        while len(heap) != 0:
            _, node = heapq.heappop(heap)
            if node == end:
                return self.reconstruct_path(came_from, start, end)
            for neighbor in self.outbound_neighbors[node]:
                if not visited[neighbor]:
                    heapq.heappush(heap, (self.distance_squared(neighbor, end), neighbor))
                    came_from[neighbor] = node
                    visited[neighbor] = True
        return False

metro = MetroSystem(18)
metro.read_metro_lines_from_file("metro_lines_example.txt")
metro.get_physical_locations_from_file("metro_lines_example_locations.txt")
print(metro.find_route_bfs_greedy(9, 4))
print(metro.find_route_bfs_greedy(1, 8))
print(metro.find_route_bfs_greedy(11, 1))
