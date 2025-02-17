import matplotlib.pyplot as plt
import matplotlib as pl
import matplotlib.animation as animation
import random
fig,ax=plt.subplots()
xs=[]
ys=[]
cx=[]
cy=[]
def update(N):
    c=0
    for i in range(N):
        x=random.uniform(-1, 1)
        y=random.uniform(-1, 1)
        if x**2+y**2<=1:
            c+=1
            cx.append(x)
            cy.append(y)
        else:
            xs.append(x)
            ys.append(y)
    ax.clear()
    ax.scatter(cx,cy)
    ax.scatter(xs,ys)
ani=animation.FuncAnimation(fig, update, frames=2000, interval=0, repeat=False)
plt.show()