import logic
import main
def test_add():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.add(score_list1, 6)==[1,2,3,4,5,6])
    assert(logic.add(score_list2, 9)==[9,8,9,8,9,8,9])
    logic.add(score_list3, 5)
    assert(score_list3[len(score_list3)-1]==5)
def test_insert():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.insert(score_list1, 2, 10)==[1,2,10,3,4,5])
    assert(logic.insert(score_list2, 6, 9)==[9,8,9,8,9,8,9])
    assert(logic.insert(score_list3, 0, 10)==[10,1,7,3,9,10,4])
def test_remove():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.remove(score_list1, 3)==[1,2,3,5])
    assert(logic.remove(score_list2, 5)==[9,8,9,8,9])
    assert(logic.remove(score_list3, 0)==[7,3,9,10,4])
def test_remove_multiple_values():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.remove_multiple_values(score_list1, 1, 4)==[1])
    assert(logic.remove_multiple_values(score_list2, 2, 4)==[9,8,8])
    assert(logic.remove_multiple_values(score_list3, 0, 5)==[])
def test_replace():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.replace(score_list1, 3, 10)==[1,2,3,10,5])
    assert(logic.replace(score_list2, 5, 1)==[9,8,9,8,9,1])
    assert(logic.replace(score_list3, 0, 2)==[2,7,3,9,10,4])
def test_less():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.less(score_list1, 3)==[0,1]) #it shows the indexes
    assert(logic.less(score_list2, 5)==[])
    assert(logic.less(score_list3, 10)==[0,1,2,3,5])
def test_sorted_participants():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.sorted_participants(score_list1)==[0,1,2,3,4]) #it shows the indexes
    assert(logic.sorted_participants(score_list2)==[1,3,5,0,2,4])
    assert(logic.sorted_participants(score_list3)==[0,2,5,1,3,4])
def test_sorted_by_value():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.sorted_by_value(score_list1, 3)==[3,4]) #it shows the indexes
    assert(logic.sorted_by_value(score_list2, 8)==[0,2,4])
    assert(logic.sorted_by_value(score_list3, 10)==[])
def test_avg():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.avg(score_list1, 0, 4)==3)
    assert(logic.avg(score_list2, 3, 5)==8.333333333333334)
    assert(logic.avg(score_list3, 0, 2)==3.6666666666666665)
def test_min():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.min(score_list1, 0, 4)==1)
    assert(logic.min(score_list2, 3, 5)==8)
    assert(logic.min(score_list3, 3, 5)==4)
def test_mul():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.mul(score_list1, 2, 0, 4)==[2, 4])
    assert(logic.mul(score_list2, 5, 0, 5)==None)
    assert(logic.mul(score_list3, 1, 0, 2)==[1, 7, 3])
def test_filter_mul():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.filter_mul(score_list1, 2)==[2, 4])
    assert(logic.filter_mul(score_list2, 5)==[])
    assert(logic.filter_mul(score_list3, 1)==[1, 7, 3, 9, 10, 4])
def test_filter_greater():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    assert(logic.filter_greater(score_list1, 2)==[3, 4, 5])
    assert(logic.filter_greater(score_list2, 5)==[9, 8, 9, 8, 9, 8])
    assert(logic.filter_greater(score_list3, 1)==[7, 3, 9, 10, 4])
def test_undo():
    score_list1=[1,2,3,4,5]
    score_list2=[9,8,9,8,9,8]
    score_list3=[1,7,3,9,10,4]
    logic.add(score_list1, 10)
    assert(logic.undo(score_list1)==[1, 2, 3, 4, 5])
    logic.insert(score_list2, 2, 1)
    assert(logic.undo(score_list2)==[9, 8, 9, 8, 9, 8])
    logic.remove(score_list3, 3)
    assert(logic.undo(score_list3)==[1, 7, 3, 9, 10, 4])
    logic.filter_greater(score_list1, 2)
    assert(logic.undo(score_list1)==[1, 2, 3, 4, 5])
def do_tests():
    test_add()
    test_insert()
    test_remove()
    test_remove_multiple_values()
    test_replace()
    test_less()
    test_sorted_participants()
    test_sorted_by_value()
    test_avg()
    test_min()
    test_mul()
    test_filter_mul()
    test_filter_greater()
    test_undo()
    main.history=[]
    print("Tests successful")