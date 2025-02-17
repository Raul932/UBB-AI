def equality(num1,num2):
    '''
    Description: Verifies if the last digit of num1 and num2 are equal
    Input: num1, num2- ints
    Output: True if the digits are equal, False otherwise
    '''
    if num1%10==num2%10:
        return True
    else:
        return False
def common_digits(num1, num2):
    '''
    Description: Searches if the numbers have common digits, and if they do, it writes them and counts them
    Input: num1, num2- ints
    Output: The number of common digits between the two numbers on the first line, and the common digits on the second line
    '''
    aux=num2
    nr_of_digits=0
    digits=[0,0,0,0,0,0,0,0,0,0]
    while num1>0:
        while num2>0:
            if equality(num1,num2) and digits[num1%10]==0:
                nr_of_digits+=1
                digits[num1%10]+=1
            num2//=10
        num2=aux
        num1//=10
    print(nr_of_digits)
    i=0
    while i<10:
        if digits[i]==1:
            print(i, end=" ")
        i+=1
num1=int(input())
num2=int(input())
common_digits(num1, num2)
