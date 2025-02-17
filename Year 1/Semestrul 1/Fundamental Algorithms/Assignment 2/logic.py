import main


def add(score_list, score):
    '''
    Description: Adds a value to the last participant in the list
    Input: score_list- the list of participants(list)
           score- the score of the participant(integer)
    Precondition: 0<score<=10
    Output: Inserts the score in the list
    '''
    while score<=0 or score>10:
        print("Wrong score! Add a new value between 1 and 10: ")
        score=int(input())
    main.history.append(list(score_list))
    score_list.append(score)
    return score_list

def insert(score_list, index, score):
    '''
    Description: Inserts a value to a participant in the list
    Input: score_list- the list of participants(list)
           index- the index of the participant that we want to add the score to(integer)
           score- the score of the participant(integer)
    Precondition: 0<=index
                  0<score<=10
    Output: Inserts the score in the list
    '''
    while index<0:
        print("Wrong index! Add a valid one: ")
        index=int(input())
    while score<=0 or score>10:
        print("Wrong score! Add a new value between 1 and 10: ")
        score=int(input())
    main.history.append(list(score_list))
    score_list.insert(index, score)
    return score_list

def remove(score_list, index):
    '''
    Description: Removes the value from the index position
    Input: score_list- list of participants
           index- the index of the participant(integer)
    Preconditions: 0<=index
    '''
    while index<0 or index>=len(score_list):
        print("Wrong index! Add a valid one: ")
        index=int(input())
    main.history.append(list(score_list))
    score_list.pop(index)
    return score_list

def remove_multiple_values(score_list, from_index, to_index):
    '''
    Description: Removes the values between the two given indexes
    Input: score_list- list of participants
           from_index- the index where we start the removal(integer)
           to_index- the index where we stop the removal(integer)
    '''
    while (from_index>to_index) or (from_index<0 or from_index>len(score_list)) or (to_index<0 or to_index>len(score_list)):
        print("Wrong values! Starting index should be lower than the ending index, add two new values: ")
        from_index=int(input())
        to_index=int(input())
    main.history.append(list(score_list))
    for index in range(to_index, from_index-1, -1):
        score_list.pop(index)
    return score_list

def replace(score_list, index, new_value):
    '''
    Description: Replaces the value at the index position with new_value
    Input: score_list- list of participants
           index- index of the participant from the list(integer)
           new_value- the new score of the participant from index(integer)
    Preconditions: 0<=index
                   0<new_value<=10
    '''
    while index<0 or index>=len(score_list):
        print("Wrong index! Please add a valid one: ")
        index=int(input())
    while new_value<=0 or new_value>10:
        print("Wrong score! Please add a number between 1 and 10")
        new_value=int(input())
    main.history.append(list(score_list))
    score_list[index]=new_value
    return score_list

def less(score_list, value):
    '''
    Description: Shows participants that have a score less than the given value
    Input: score_list- list of participants
           value- the score(integer)
    Preconditions: 0<value<=10
    Output: The participants that have a lower score than value
    '''
    while value>10 or value<1:
        print("Wrong value! Please add a valid number between 1 and 10: ")
        value=int(input())
    new_list=[]
    for index in range(len(score_list)):
        if score_list[index]<value:
            new_list.append(index)
    return new_list

def sorted_participants(score_list):
    '''
    Description: Sorts the list based on the score
    Input: score_list- the list of participants
    Output: The list of participants, but sorted
    '''
    sorted_indexes = sorted(range(len(score_list)), key=lambda i: score_list[i])
    return sorted_indexes

def sorted_by_value(score_list, value):
    '''
    Description: Sorts the list with scores higher than the given value
    Input: score_list- the list of participants
           value- the minimum value in the new list(integer)
    Preconditions: 0<value<=10
    Output: The new list, sorted
    '''
    while value<1 or value>10:
        print("Wrong value! Please add a valid one: ")
        value=int(input())
    sorted_indexes=sorted(index for index, element in enumerate(score_list) if element>value)
    return sorted_indexes

def avg(score_list, from_index, to_index):
    '''
    Description: Computes the average of the scores between the starting index and the ending index
    Input: score_list- participants list
           from_index- starting index(integer)
           to_index- finishing index(integer)
    Preconditions: 0<=from_index<=to_index<the length of the list
    Output: The average from starting index to ending index
    '''
    while (from_index>to_index) or (from_index<0 or from_index>len(score_list)) or (to_index<0 or to_index>len(score_list)):
        print("Wrong indexes! Please add new ones: ")
        from_index=int(input("Starting index: "))
        to_index=int(input("Finishing index: "))
    average=0
    for index in range(from_index, to_index+1):
        average+=score_list[index]
    return average/(to_index-from_index+1)

def min(score_list, from_index, to_index):
    '''
    Description: Finds the minimum score in the list between the two indexes
    Input: score_list- list of participants
           from_index- int(starting index)
           to_index- int(final index)
    Preconditions: 0<=from_index<=to_index<len(score_list)
    Output: minimum- int(the minimum value)               
    '''
    if len(score_list)==0:
        return None
    minimum=11
    while (from_index>to_index) or (from_index<0 or from_index>len(score_list)) or (to_index<0 or to_index>len(score_list)):
        print("Wrong indexes! Please add new ones: ")
        from_index=int(input("Starting index: "))
        to_index=int(input("Finishing index: "))
    for index in range(from_index, to_index+1):
        if score_list[index]<minimum:
            minimum=score_list[index]
    return minimum

def mul(score_list, value, from_index, to_index):
    '''
    Description: Finds the elements in the list that are multiples of value
    Input: score_list- list of participants
           value- int
           from_index- int(starting index)
           to_index- int(final index)
    Preconditions: 1<=from_index<=to_index<len(score_list)
    Output: mul_list- list of the multiples if they exist, or None otherwise
    '''
    while (from_index>to_index) or (from_index<0 or from_index>len(score_list)) or (to_index<0 or to_index>len(score_list)):
        print("Wrong indexes! Please add new ones: ")
        from_index=int(input("Starting index: "))
        to_index=int(input("Finishing index: "))
    while value<1 or value>10:
        value=int(input("Add a value between 1 and 10: "))
    mul_list=[]
    for index in range(from_index, to_index+1):
        if score_list[index]%value==0:
            mul_list.append(score_list[index])
    if len(mul_list):
        return mul_list
    return None

def filter_mul(score_list, value):
    '''
    Description: Filters the values in the list, leaving only the multiples of {value}
    Input: score_list- list of participants
           value- int
    Output: new_list-list with multiples of value
    '''
    new_list=[]
    for element in score_list:
        if element%value==0:
            new_list.append(element)
    main.history.append(list(score_list))
    return new_list

def filter_greater(score_list, value):
    '''
    Description: Filters the values in the list, leaving only the ones greater than {value}
    Input: score_list- list of participants
           value- int
    Output: new_list- list with elements greater than value
    '''
    new_list=[]
    for element in score_list:
        if element>value:
            new_list.append(element)
    main.history.append(list(score_list))
    return new_list

def undo(score_list):
    '''
    Description: Undo's the last operation done
    Output: Returns the list before the last operation or None if there wasn't any operation made
    '''
    if main.history:
        score_list[:]=main.history.pop()
        return score_list
    else:
        return None