from graph import Graph
from iterator import VertexIterator

def print_menu():
    print("1. Add Vertex")
    print("2. Add Edge")
    print("3. Remove Edge")
    print("4. Remove Vertex")
    print("5. Create Random Graph")
    print("6. Get Number of Vertices")
    print("7. Get Number of Edges")
    print("8. Get Degree of Vertex")
    print("9. Check if Edge Exists")
    print("10. Get Outbound Edges of Vertex")
    print("11. Get Inbound Edges of Vertex")
    print("12. Print Graph")
    print("13. Randomize Weights")
    print("14. Set Weight for Edge")
    print("15. Create Graph from File")
    print("16. Iterator")
    print("0. Exit")

def main():
    directed = input("Is the graph directed? (y/n): ").lower() == 'y'
    weighted = input("Is the graph weighted? (y/n): ").lower() == 'y'
    graph = Graph(directed, weighted)
    start_vertex = None
    
    while True:
        print("\nGraph Menu:")
        print_menu()
        choice = input("Enter your choice: ")
        try:
            if choice == '1':
                graph.add_vertex()
                if start_vertex == None:
                    start_vertex = 0
                print("Vertex added.")
            elif choice == '2':
                initial_vertex = int(input("Enter initial vertex: "))
                terminal_vertex = int(input("Enter terminal vertex: "))
                weight = None
                if graph.weighted:
                    weight = int(input("Enter weight: "))
                graph.add_edge(initial_vertex, terminal_vertex, weight)
                print("Edge added.")
            elif choice == '3':
                initial_vertex = int(input("Enter initial vertex: "))
                terminal_vertex = int(input("Enter terminal vertex: "))
                graph.remove_edge((initial_vertex, terminal_vertex))
                print("Edge removed.")
            elif choice == '4':
                vertex = int(input("Enter vertex to remove: "))
                graph.remove_vertex(vertex)
                print("Vertex removed.")
            elif choice == '5':
                n = int(input("Enter number of vertices: "))
                m = int(input("Enter number of edges: "))
                graph.create_random(n, m)
                start_vertex = 0
                print("Random graph created.")
            elif choice == '6':
                print("Number of vertices:", graph.get_n())
            elif choice == '7':
                print("Number of edges:", graph.get_m())
            elif choice == '8':
                vertex = int(input("Enter vertex: "))
                degree_type = input("Enter degree type (total/in/out): ")
                print("Degree of vertex:", graph.deg(vertex, degree_type))
            elif choice == '9':
                vertex1 = int(input("Enter first vertex: "))
                vertex2 = int(input("Enter second vertex: "))
                print("Edge exists:", graph.is_edge(vertex1, vertex2))
            elif choice == '10':
                vertex = int(input("Enter vertex: "))
                print("Outbound edges:", graph.outbound_edges(vertex))
            elif choice == '11':
                vertex = int(input("Enter vertex: "))
                print("Inbound edges:", graph.inbound_edges(vertex))
            elif choice == '12':
                print("Graph:")
                print(graph)
            elif choice == '13':
                weights_range = input("Enter weights range (e.g., '0 10'): ").split()
                weights_range = [int(val) for val in weights_range]
                graph.random_weights(weights_range)
                print("Weights randomized.")
            elif choice == '14':
                initial_vertex = int(input("Enter initial vertex: "))
                terminal_vertex = int(input("Enter terminal vertex: "))
                weight = int(input("Enter weight: "))
                graph.set_weight(initial_vertex, terminal_vertex, weight, directed)
                print("Weight set for edge.")
            elif choice == '15':
                filename = input("Enter filename: ")
                graph, start_vertex = Graph.create_from_file(filename)
                print("Graph created from file.")
            elif choice == '16':
                if start_vertex is None:
                    print("Error: Please create a graph first.")
                    continue
                current_vertex = int(input("Enter the current vertex: "))
                iterator = VertexIterator(graph, start_vertex)
                path = []
                while iterator.valid() and iterator.get_current() != current_vertex:
                    iterator.next()
                    path = iterator.get_path()
                if iterator.valid():
                    path_length = len(path) - 1
                    print("Path from vertex", start_vertex, "to vertex", current_vertex, ":", path)
                    print("Path length:", path_length)
                else:
                    print("The current vertex is not reachable from the initial vertex.")
            elif choice == '0':
                print("Exiting...")
                break
            else:
                print("Invalid choice. Please try again.")
        except ValueError as e:
            print("Error: ", e)

if __name__ == "__main__":
    g = Graph(directed=False, weighted=False)

    # Adding vertices
    g.add_vertex()  # Vertex 0
    g.add_vertex()  # Vertex 1
    g.add_vertex()  # Vertex 2
    g.add_vertex()  # Vertex 3
    g.add_vertex()  # Vertex 4

    # Adding edges
    g.add_edge(0, 1)
    g.add_edge(1, 2)
    g.add_edge(2, 0)
    g.add_edge(2, 3)
    g.add_edge(3, 4)

    # Calculate the chromatic number and get the coloring
    chromatic_number, coloring = g.greedy_coloring()

    print("Chromatic Number:", chromatic_number)
    print("Vertex Coloring:", coloring)