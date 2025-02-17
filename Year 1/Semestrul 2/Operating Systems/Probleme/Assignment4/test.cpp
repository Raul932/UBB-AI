#include <iostream>
#include <memory>

class Box {
public:
    Box() {
        std::cout << "Box constructed" << std::endl;
    }
};

class Container {
public:
    Container() {
        std::cout << "Container constructed" << std::endl;
    }
};

int main() {
    myBox = Box();
    myContainer = Container();
    myBox2 = Box();
    myBox3 = Box(2);
    return 0;
}
