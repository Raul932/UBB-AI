#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {

	first();
}

TComp SortedBagIterator::getCurrent() {
	if (!valid())
		throw exception();

	return bag.elements[current_position];
}

bool SortedBagIterator::valid() {
	
	return current_position >= 0 && current_position < bag.current_size;
}

void SortedBagIterator::next() {
	if (!valid())
		throw exception();
	current_position++;
	
}

void SortedBagIterator::first() {
	current_position = 0;
}

