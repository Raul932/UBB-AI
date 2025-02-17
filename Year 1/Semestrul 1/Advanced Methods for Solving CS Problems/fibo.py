def fibo(x):
    if x==0 or x==1:
        return x
    return fibo(x-1)+fibo(x-2)
def fibo1(x):
    if x==0 or x==1:
        return x
    ant=0
    curent=1
    for i in range(x):
        aux=ant+curent
        ant=curent
        curent=aux
    return ant
def inmultire_matrice(m[][]):
    