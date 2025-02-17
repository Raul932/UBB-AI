import logic
import test
history=[]
def read_list(file_name):
    '''
    Description: Read list from a file
    Input: file_name- string
    Output: list in file
    '''
    line=""
    with open(file_name, "r+") as f:
        line=f.readline()
    l=line.split(",")
    return [int(x) for x in l]

def write_to_file(file_name, line):
    '''
    Description: Writes list to file
    Input: String, list
    '''
    line=str(line)
    with open(file_name, "w+") as f:
        f.write(line)

score_list=read_list("data.in")

def repeat(yes_or_no):
    '''
    Description: Asks the user if he wants to repeat the operation
    Input: yes_or_no- string
    '''
    print("Do you want to repeat this operation? (Y/N)")
    yes_or_no=input()
    while yes_or_no!="Y" and yes_or_no!="y" and yes_or_no!="N" and yes_or_no!="n":
        print("Please answer with Y or N: ")
        yes_or_no=input()
    if(yes_or_no=="N" or yes_or_no=="n"):
        print("**************")
    return yes_or_no
if __name__=="__main__":
    test.do_tests()
    while True:
        print("1. Add the result of a new participant to the array(add, insert)")
        print("2. Modify the scores in the array(remove, remove_multiple, replace)")
        print("3. Get the participants with scores having some properties(less, sorted_participants, sorted_by_value)")
        print("4. Obtain different characteristics of participants(avg, min, mul)")
        print("5. Filter values(filter_mul, filter_greater)")
        print("6. Undo(undo)")
        print("7. Exit(print)")
        option=int(input("Please choose an option: "))
        if option==1:
            yes_or_no="Y"
            print("What would you like to do?")
            print("1. Add a value to the last position")
            print("2. Insert a value at the index of your choice", "\n")
            opt=int(input("Please choose an option: "))
            while opt>2 or opt<1:
                opt=int(input("Add a valid option: "))
            if opt==1:
                while yes_or_no=="Y" or yes_or_no=="y":
                    score=int(input("Add the score: "))
                    logic.add(score_list, score)
                    yes_or_no=repeat(yes_or_no)
                print(score_list)
            if opt==2:
                while yes_or_no=="Y" or yes_or_no=="y":
                    score=int(input("Add the score: "))
                    index=int(input("Add the index: "))
                    logic.insert(score_list, index, score)
                    yes_or_no=repeat(yes_or_no)
                print(score_list)

        if option==2:
            yes_or_no="Y"
            print("What would you like to do?")
            print("1. Remove element at given index")
            print("2. Remove elements between given indexes")
            print("3. Replace the score at index with a new one", "\n")
            opt=int(input("Please choose an option: "))
            while opt>3 or opt<1:
                opt=int(input("Add a valid option: "))
            if opt==1:
                while yes_or_no=="Y" or yes_or_no=="y":
                    index=int(input("Add the index: "))
                    score_list=logic.remove(score_list, index)
                    yes_or_no=repeat(yes_or_no)
                print(score_list)
            if opt==2:
                while yes_or_no=="Y" or yes_or_no=="y":
                    from_index=int(input("Add the starting index: "))
                    to_index=int(input("Add the finishing index: "))
                    score_list=logic.remove_multiple_values(score_list, from_index, to_index)
                    yes_or_no=repeat(yes_or_no)
                print(score_list)
            if opt==3:
                while yes_or_no=="Y" or yes_or_no=="y":
                    index=int(input("Add the index: "))
                    new_value=int(input("Add the new value: "))
                    score_list=logic.replace(score_list, index, new_value)
                    yes_or_no=repeat(yes_or_no)
                print(score_list)
        if option==3:
            yes_or_no="Y"
            print("What would you like to do?")
            print("1. Get participants with scores less than given value")
            print("2. Get participants sorted by their score")
            print("3. Get participants with a score higher than value, sorted", "\n")
            opt=int(input("Please choose an option: "))
            while opt>3 or opt<1:
                opt=int(input("Add a valid option: "))
            if opt==1:
                while yes_or_no=="Y" or yes_or_no=="y":
                    value=int(input("Add a value: "))
                    print(logic.less(score_list, value))
                    yes_or_no=repeat(yes_or_no)
            if opt==2:
                while yes_or_no=="Y" or yes_or_no=="y":
                    print(logic.sorted_participants(score_list))
                    yes_or_no=repeat(yes_or_no)
            if opt==3:
                while yes_or_no=="Y" or yes_or_no=="y":
                    value=int(input("Add a value: "))
                    print(logic.sorted_by_value(score_list, value))
                    yes_or_no=repeat(yes_or_no)
        if option==4:
            yes_or_no="Y"
            print("What would you like to do?")
            print("1. Get the average score between two indexes")
            print("2. Get minimum score between two given indexes")
            print("3. Get the score of participants between two given indexes, which are multiples of value")
            opt=int(input("Please choose an option: "))
            while opt>3 or opt<1:
                opt=int(input("Add a valid option: "))
            print(score_list)
            if opt==1:
                while yes_or_no=="Y" or yes_or_no=="y":
                    from_index=int(input("Add a starting index: "))
                    to_index=int(input("Add an ending index: "))
                    print(logic.avg(score_list, from_index, to_index))
                    yes_or_no=repeat(yes_or_no)
            if opt==2:
                while yes_or_no=="Y" or yes_or_no=="y":
                    from_index=int(input("Add a starting index: "))
                    to_index=int(input("Add an ending index: "))
                    print(logic.min(score_list, from_index, to_index))
                    yes_or_no=repeat(yes_or_no)
            if opt==3:
                while yes_or_no=="Y" or yes_or_no=="y":
                    value=int(input("Add a value: "))
                    from_index=int(input("Add a starting index: "))
                    to_index=int(input("Add an ending index: "))
                    print(logic.mul(score_list,value , from_index, to_index))
                    yes_or_no=repeat(yes_or_no)
        if option==5:
            yes_or_no="Y"
            print("What would you like to do?")
            print("1. Keep the scores that are multiples of value, removing the others")
            print("2. Keep the scores that are greater than value, removing the others")
            opt=int(input("Please choose an option: "))
            while opt>2 or opt<1:
                opt=int(input("Add a valid option: "))
            if opt==1:
                while yes_or_no=="Y" or yes_or_no=="y":
                    value=int(input("Add a value: "))
                    score_list=logic.filter_mul(score_list, value)
                    yes_or_no=repeat(yes_or_no)
                print(score_list)
            if opt==2:
                while yes_or_no=="Y" or yes_or_no=="y":
                    value=int(input("Add a value: "))
                    score_list=logic.filter_greater(score_list, value)
                    yes_or_no=repeat(yes_or_no)
                print(score_list)
        if option==6:
            yes_or_no="Y"
            new_list=logic.undo(score_list)
            if new_list is not None:
                print("Undo successful. Updated score list:", new_list)
                score_list=new_list
            else:
                print("Nothing to undo")
        if option==7:
            write_to_file("data.out", score_list)
            break