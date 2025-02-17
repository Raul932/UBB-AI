#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

//O(1)
SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
    current = bag.head;
}

//O(1)
TComp SortedBagIterator::getCurrent() {
	if(!valid())
		throw exception();
	return current->element;
}

//O(1)
bool SortedBagIterator::valid() {
	return current!=nullptr;
}

//O(1)
void SortedBagIterator::next() {
    if (!valid())
        throw exception();
    if (current->frequency > 1) {
        current->frequency--;
    } else {
        current = current->next;
    }
}

//O(1)
void SortedBagIterator::first() {
	current = bag.head;
}

