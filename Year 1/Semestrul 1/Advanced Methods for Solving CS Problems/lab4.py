#ai un vector cu numere intregi si trebuie afisate tripletele care dau suma s
def triplet_suma(l, suma):
    '''
    Afiseaza tripletele care dau suma s
    Input: l- lista cu numere
           suma- suma
    Output: nr de triplete cerute
    '''
    count=0
    for i in range(len(l)):
        for j in range(i+1, len(l)):
            for k in range(j+1, len(l)):
                if l[i]+l[j]+l[k]==suma:
                    count+=1
    return count
def interclasare(lista):
    i=k=0
    j=len(lista//2)
    new_list=[]
    while i<len(lista//2) and j<len(lista):
        if lista[i]<=lista[j]:
            new_list[k]=lista[i]
            i+=1
            k+=1
        else:
            new_list[k]=lista[j]
            j+=1
            k+=1
    while i<len(lista//2):
        new_list[k]=lista[i]
        i+=1
        k+=1
    while j<len(lista):
        new_list[k]=lista[j]
        j+=1
        k+=1
    return new_list
def merge_sort(lista):
    i=0
    mijl=j=len(lista)//2
    new_list=interclasare(lista)
    print(new_list)
def test():
    suma=15
    l=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
    assert triplet_suma==12