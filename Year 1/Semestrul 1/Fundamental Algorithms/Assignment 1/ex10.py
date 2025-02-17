def number_of_5s(num):
    '''
    Description: Looks for the digit 5 in the number
    Input: num: defined in the other function
    Output: The number of 5's that appear in num
    '''
    nr5=0
    while num!=0:
        if num%10==5:
            nr5+=1
        num//=10
    return nr5

def higher_5():
    '''
    Description: Reads a list of numbers until 0 is met, and then it prints the number of consecutive pairs n1, n2 which have the property that n1 has more 5's than n2
    Input: A list of numbers, and 0 when you are done
    Output: The number of pairs that have the property
    '''
    num1=int(input())
    num2=int(input())
    nr=0
    while num1!=0 and num2!=0:
        if number_of_5s(num1)>number_of_5s(num2):
            nr+=1
        num1=num2
        num2=int(input())
    return nr

print(higher_5())
