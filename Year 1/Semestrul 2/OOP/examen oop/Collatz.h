#ifndef COLLATZ_H
#define COLLATZ_H
#include <unordered_map>
struct Nod{
    Nod* previous1;
    Nod* previous2;
    Nod* next;
    int info;

    Nod(): previous1(nullptr), previous2(nullptr), next(nullptr), info(0) {}
    ~Nod();
};

class Collatz{
    private:
    Nod* head;
    std::unordered_map<int, int> visited;

    void CollatzMate(Nod* node);
    
    
    public:
    Collatz();
    ~Collatz();

    void add(int x);
    void printSequence();
};


#endif