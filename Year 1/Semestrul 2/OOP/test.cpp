#include <iostream>
using namespace std;

class A {
public:
    virtual void print() {
        cout << "printA" << endl;
    }
    virtual ~A() {
        cout << "~A" << endl;
    }
};

class B : public A {
public:
    void print() {
        cout << "printB" << endl;
    }
    ~B() {
        cout << "~B" << endl;
    }
};

int main() {
    A* o1 = new A();
    A* o2 = new B();

    o1->print();  // Outputs "printA"
    o2->print();  // Outputs "printB" due to polymorphism

    delete o1;    // Outputs "~A"
    delete o2;    // Outputs "~B" followed by "~A" due to polymorphism and correct destructor management

    return 0;
}
