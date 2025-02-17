#include "MultiMapIterator.h"
#include "MultiMap.h"
#include <exception>
#include <iostream> // For debug prints
//O(n)
MultiMapIterator::MultiMapIterator(const MultiMap& c) : col(c), currentPos(-1) {
    first();
}

//O(1)
TElem MultiMapIterator::getCurrent() const {
    if (!valid()) {
        throw std::exception();
    }
    return this->col.hashTable[currentPos];
}

//O(1)
bool MultiMapIterator::valid() const {
    return currentPos != -1 && this->col.hashTable[currentPos].first != -1000000;
}

//O(n)
void MultiMapIterator::next() {
    if (!valid()) {
        throw std::exception();
    }

    if (this->col.next[currentPos] != -1) {
        currentPos = this->col.next[currentPos];
    } else {
        do {
            currentPos++;
            if (currentPos >= this->col.capacity) {
                currentPos = -1;
                return;
            }
        } while (this->col.hashTable[currentPos].first == -1000000);
    }

}

//O(n)
void MultiMapIterator::first() {
    currentPos = 0;
    while (currentPos < this->col.capacity && this->col.hashTable[currentPos].first == -1000000) {
        currentPos++;
    }
    if (currentPos >= this->col.capacity) {
        currentPos = -1; 
    }

}
