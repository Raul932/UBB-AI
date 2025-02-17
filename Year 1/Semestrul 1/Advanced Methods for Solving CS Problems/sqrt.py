import math

def square_root(x, eps):
    '''
    function that returns the aproximation of the square root of the supraunitary real number x with precision eps
    input: x
    preconditions: x real, x>1
    output: rez
    postconditions: |rez-sqrt(x)|<eps, rez apartine (1,x)
    '''
    diff=eps+1
    rez=1.0
    while diff>eps:
        rez=rez-(rez*rez-x)/2
        diff=rez*rez-x
        if diff<0:
            diff*=-1
    return rez
def test_sqrt():
    x=2
    eps=0.00000000001
    exp=math.sqrt(x)
    s=square_root(x, eps)
    assert(abs(exp-s)<eps)
test_sqrt()
