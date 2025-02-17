from Repository import WorkoutRepository

def test_add():
    WorkoutRepository.add_workout(1, "Da", "Cardio", 5, 12)
    assert(len(WorkoutRepository.__list_of_workouts)==1)
test_add()