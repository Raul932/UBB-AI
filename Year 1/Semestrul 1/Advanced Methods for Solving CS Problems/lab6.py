from queue import PriorityQueue


gr={"in":{0:{1:4,7:8},
          1:{0:4,2:8,7:11},
          2:{1:8,3:7,5:4,8:2},
          3:{2:7,4:9,5:14},
          4:{3:9,5:10},
          5:{3:14,4:10,6:2},
          6:{5:2,7:1,8:7},
          7:{0:8,6:1,}}, 
    "out":{0:{1:4,7:8},
          1:{0:4,2:8,7:11},
          2:{1:8,3:7,5:4,8:2},
          3:{2:7,4:9,5:14},
          4:{3:9,5:10},
          5:{3:14,4:10,6:2},
          6:{5:2,7:1,8:7},
          7:{0:8,6:1,}}}

def dijkstra(gr, s):
    d=[float('inf')]*9
    d[s]=0
    p=[-1]*9
    c=PriorityQueue(s)
    v=set([])
    while not c.empty():
        p, current=c.get()
        if current in v:
            continue
        v.add(current)
        for child in gr["out"][current]:
            if child not in v:
                if d[child]>d[current]+gr["out"][current][child]:
                    d[child]=d[current]+gr["out"][current][child]
                    p[child]=current
                    c.put((d[child],child))

    return d,p

def process_dijkstra():

