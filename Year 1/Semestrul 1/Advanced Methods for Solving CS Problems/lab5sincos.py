import numpy as np
from matplotlib import pyplot as plt

def s(x):
    return np.sin(x)

def sinus(x, eps):
    s=0
    t=x
    k=1
    while abs(t)>=eps:
        s+=t
        t= -t *x*x/2((2*k)*(2*k+1))
        k+=1
    return s

x=np.linspace(-10, 10, 100)
plt.plot(x, s(x), color='red')
plt.plot(x, sinus[x, 0.01], color='yellow')
plt.plot(x,)