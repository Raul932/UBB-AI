from fitness import Workout

class WorkoutRepository:
    def __init__(self):
        self.__list_of_workouts=[]
    def add_workout(self, id, name, type, diff, duration):
        for workout in self.__list_of_workouts:
            if workout.get_id()==id and id:
                raise ValueError("Wrong ID")
        if not isinstance(name, str):
            raise ValueError("Please enter a good name")
        if type!="Cardio" and type!="Strength" and type!="Flexibility":
            raise ValueError("Please enter an appropriate type")
        if diff>5 or diff<1:
            raise ValueError("Please enter an appropriate difficulty")
        if duration<1 or duration<=0:
            raise ValueError("Please enter an appropriate duration")
        self.__list_of_workouts.append(Workout(id, name, type, diff, duration))
    def get_all(self):
        for workout in self.__list_of_workouts:
            print(workout)
    def sort_workouts(self):
        self.__list_of_workouts=sorted(self.__list_of_workouts, key=lambda x: x.get_diff(), reverse=True)
    def remove_workouts(self):
        self.__list_of_workouts=[x for x in self.__list_of_workouts if x.get_type()!="Strength" and x.get_diff()<=3]

        
        
    