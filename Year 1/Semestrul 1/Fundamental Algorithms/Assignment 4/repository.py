from vector import MyVector
import matplotlib.pyplot as plt

class VectorRepository:
    def __init__(self):
        self.__vectors=[]

    def get_vector_count(self):
        '''
        Description: Counts the number of vectors
        Output: Number of vectors
        '''
        return len(self.__vectors)
    
    def add_vector(self, vector):
        '''
        Description: Adds a vector to the repository
        Input: The vector
        Output: None
        '''
        if isinstance(vector, MyVector):
            self.__vectors.append(vector)
        else:
            print("Invalid type.")
    
    def get_all_vectors(self):
        '''
        Description: Prints all the vectors
        Input: None
        Output: The list of vectors
        '''
        return '\n'.join(vector.__str__() for vector in self.__vectors)
    
    def get_vector_at_index(self, index):
        '''
        Description: Prints the vector at a given index
        Input: Index
        Output: Vector at given index
        '''
        if 0 <= index and index < len(self.__vectors):
            vector_at_index = self.__vectors[index]
            return MyVector(
                vector_at_index.get_name_id(),
                vector_at_index.get_color(),
                vector_at_index.get_type(),
                vector_at_index.get_values()
            )
        return None
    
    def update_vector_at_index(self, index, new_vector):
        '''
        Description: Changes the value of the vector at the given index
        Input: Index, new_vector
        '''
        if 0 <= index and index < len(self.__vectors):
            vector_to_update = self.__vectors[index]
            vector_to_update.set_name_id(new_vector.get_name_id())
            vector_to_update.set_color(new_vector.get_color())
            vector_to_update.set_type(new_vector.get_type())
            vector_to_update.set_values(new_vector.get_values())
            print(f"Vector at index {index} updated.")
        else:
            print("Index out of range.")

    def update_vector_by_name_id(self, name_id, new_vector):
        updated = False
        for i in range(len(self.__vectors)):
            if self.__vectors[i].get_name_id() == name_id:
                self.__vectors[i] = new_vector
                updated = True
                break

        if not updated:
            print(f"Vector with name_id {name_id} not found.")
    
    def delete_vector_at_index(self, index):
        '''
        Description: Deletes a vector at a given index
        Input: index- int
        '''
        if 0 <= index and index < len(self.__vectors):
            del self.__vectors[index]
            print(f"Vector at index {index} deleted.")
        else:
            print("Index out of range.")
    
    def delete_vector_by_name_id(self, name_id):
        '''
        Description: Deletes a vector by the name_id
        Input: name_id- int
        '''
        for vector in self.__vectors:
            if vector.get_name_id() == name_id:
                self.__vectors.remove(vector)
                print(f"Vector with name_id {name_id} deleted.")
                return
        print(f"Vector with name_id {name_id} not found.")

    def plot_all_vectors(self):
        """
        Description: this function plots all the vectors
        Input: None
        Output: None
        """
        fig, ax = plt.subplots(figsize=(17, 9))
 
        for vector in self.__vectors:
            name_id = vector.get_name_id()
            type = vector.get_type()
            color = vector.get_color()
            values = vector.get_values()
 
            if type == 1:
                ax.plot(values, label=name_id, color=color, marker="o")
            elif type == 2:
                ax.plot(values, label=name_id, color=color, marker="s")
            elif type == 3:
                ax.plot(values, label=name_id, color=color, marker="^")
            elif type > 3:
                ax.plot(values, label=name_id, color=color, marker="D")
        ax.set_title("All Vectors")
        ax.legend()
        plt.show()

    def get_max_vectors_with_sum_above(self, value):
        '''
        Description: Gets the max of all vectors having the sum greater than value
        Input: Value- int
        Output: max_vector - the maximum vector if it exists, None otherwise
        '''
        eligible_vectors = [vector for vector in self.__vectors if sum(vector.get_values()) > value]

        if not eligible_vectors:
            print("No vectors with sum above the threshold.")
            return None

        max_vector = eligible_vectors[0]
        max_value = max_vector.get_values()

        for vector in eligible_vectors[1:]:
            current_value = vector.get_values()
            if max(current_value) > max(max_value):
                max_vector = vector
                max_value = current_value

        return max_vector
    
    def delete_vectors_with_product_above(self, value):
        '''
        Description: Deletes all vectors that have a product bigger than the given value
        Input: value- int
        '''
        vectors_to_delete = [vector for vector in self.__vectors if vector.product_elements() > value]

        for vector in vectors_to_delete:
            self.__vectors.remove(vector)

        print(f"Deleted {len(vectors_to_delete)} vectors with product above {value}.")
        
    def update_vector_color_by_name_id(self, name_id, new_color):
        '''
        Description: Changes the color of a vector with the given name_id
        Input: name_id- int
               new_color- string
        '''
        for vector in self.__vectors:
            if vector.get_name_id() == name_id:
                vector.set_color(new_color)
                print(f"Updated color for vector with name_id {name_id} to {new_color}.")
                return

        print(f"No vector found with name_id {name_id}.")