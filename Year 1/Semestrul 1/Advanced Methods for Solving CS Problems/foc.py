import matplotlib.pyplot as plt
import matplotlib.animation as animation
import random
from mpl_toolkits import mplot3d
import numpy as np
import math

def Gauss(x, y, sigmaX, sigmaY, A):
    return A * math.exp(-(x**2 / (2* sigmaX**2) + y**2 / (2*sigmaY**2)))

EPS=0.001

def update_graph(_):
    num_points= len(points[0])

    for index in range(num_points):
        gauss_dist=Gauss(points[0][index], points[1][index], 0.3, 0.3, 0.05)
        points[0][index] += np.random.uniform(-EPS, EPS)
        points[1][index] += np.random.uniform(-EPS, EPS)
        points[2][index] += gauss_dist
        points[3][index] -= gauss_dist

    graph._offsets3d=(points[0], points[1], points[2])
points=[[], [], [], []]
for i in range(10000):
    x=random.uniform(-1,1)
    y=random.uniform(-1,1)
    z=0
    if x**2+y**2<=1:
        points[0].append(x)
        points[1].append(y)
        points[2].append(z)
        points[3].append(1)
fig=plt.figure() 
ax=fig.add_subplot(projection='3d')
graph=ax.scatter(points[0], points[1], points[2], alpha=points[3], s=5, marker='o', color='magenta')
ani=animation.FuncAnimation(fig, update_graph, frames=1000, interval=100, repeat=False)
plt.show()