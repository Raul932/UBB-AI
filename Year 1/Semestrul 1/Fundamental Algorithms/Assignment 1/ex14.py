def binary_ones(n):
    '''
    Description: Calculates the number of 1's that appear in the binary writing of n
    Input: n-integer
    Preconditions: n>0
    Output: The number of 1's that appear in the binary writing of n
    '''
    nr=0
    while n>0:
        if n%2:
            nr+=1
        n//=2
    return nr
n=int(input())
print(binary_ones(n))