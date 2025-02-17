
#include "SortedBagIterator.h"
#include <stdexcept>
using namespace std;
SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b), current(b.head) {}

TComp SortedBagIterator::getCurrent() {
    if (!valid()) 
        throw exception();
    return bag.elems[current];
}

bool SortedBagIterator::valid() {
    return current != -1 && bag.freqs[current] > 0;
}

void SortedBagIterator::next() {
    if (!valid()) 
        throw exception();
    current = bag.next[current];
}

void SortedBagIterator::first() {
    current = bag.head;
}
