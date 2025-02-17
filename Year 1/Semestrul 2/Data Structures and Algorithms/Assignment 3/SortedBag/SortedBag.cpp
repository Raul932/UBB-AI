#include "SortedBagIterator.h" 
#include "SortedBag.h"
#include <iostream>

SortedBag::SortedBag(Relation r) : head(-1), tail(-1), firstEmpty(0), capacity(10), numElements(0), rel(r) {
    elems = new TElem[capacity];
    freqs = new int[capacity];
    next = new int[capacity];
    prev = new int[capacity];
    for (int i = 0; i < capacity; i++) {
        next[i] = i + 1;
        prev[i] = i - 1;
        freqs[i] = 0;
    }
    next[capacity - 1] = -1;
    prev[0] = -1;
}

//BC O(1) WC O(n) AC O(n)
void SortedBag::add(TComp e) {
    if (firstEmpty == -1) {
        resize();
    }
    int current = head;
    int prevIndex = -1;
    while (current != -1 && rel(elems[current], e) && elems[current] != e) {
        prevIndex = current;
        current = next[current];
    }
    
    if (current != -1 && elems[current] == e) {
        freqs[current]++;
        numElements++;
    } else {
        // Else, add new element
        int newIndex = firstEmpty;
        firstEmpty = next[firstEmpty];
        elems[newIndex] = e;
        freqs[newIndex] = 1;
        next[newIndex] = current;
        prev[newIndex] = prevIndex;
        if (prevIndex != -1) {
            next[prevIndex] = newIndex;
        } else {
            head = newIndex;
        }
        if (current != -1) {
            prev[current] = newIndex;
        } else {
            tail = newIndex;
        }
        numElements++;
    }
}

//BC O(1) WC O(n) AC O(n)
bool SortedBag::remove(TComp e) {
    if (isEmpty()) {
        return false;
    }
    else {
        if(numElements>1){
            if(elems[head] > elems[tail] && ((e > elems[head]) || (e < elems[tail])))
                return false;
            else if(elems[head] < elems[tail] && ((e < elems[head]) || (e > elems[tail])))
                return false;
        }
    }
    int current = head;
    while (current != -1 && elems[current] != e) {
        current = next[current];
    }
    if (current == -1) {
        return false; // Element not found
    }
    freqs[current]--;
    numElements--;
    if (freqs[current] == 0) {
        // Remove element from the list
        if (prev[current] != -1) {
            next[prev[current]] = next[current];
        } else {
            head = next[current];
        }
        if (next[current] != -1) {
            prev[next[current]] = prev[current];
        } else {
            tail = prev[current];
        }
        // Add to empty spots
        next[current] = firstEmpty;
        firstEmpty = current;
    }
    return true;
}

//BC O(1) WC O(n) AC O(n)
bool SortedBag::search(TComp e) const {
    if (isEmpty()) {
        return false;
    }
    else {
        if(numElements>1){
            if(elems[head] > elems[tail] && ((e > elems[head]) || (e < elems[tail])))
                return false;
            else if(elems[head] < elems[tail] && ((e < elems[head]) || (e > elems[tail])))
                return false;
        }
    }
    int current = head;
    while (current != -1) {
        if (elems[current] == e && freqs[current] > 0) {
            return true;
        }
        current = next[current];
    }
    return false;
}

//BC O(1) WC O(n) AC O(n)
int SortedBag::nrOccurrences(TComp e) const {
    int current = head;
    while (current != -1) {
        if (elems[current] == e) {
            return freqs[current];
        }
        current = next[current];
    }
    return 0;
}

//O(1)
int SortedBag::size() const {
    return numElements;
}

//O(1)
bool SortedBag::isEmpty() const {
    return numElements == 0;
}
//BC O(1) WC O(n) AC O(n)
int SortedBag::removeOccurrences(int nr, TComp elem) {
    if (nr < 0) {
        throw std::exception();
    }

    if (nr == 0) {
        return 0;  // No occurrences to remove, so return 0
    }

    int current = head;
    int countRemoved = 0;

    // Find the element in the bag
    while (current != -1 && elems[current] != elem) {
        current = next[current];
    }

    if (current == -1) {
        return 0;  // Element not found, return 0
    }

    // Element found, check the frequency
    if (freqs[current] > nr) {
        freqs[current] -= nr;
        countRemoved = nr;
    } else {
        countRemoved = freqs[current];
        freqs[current] = 0;  // Remove all occurrences

        // Now, remove the element entirely from the list
        if (prev[current] != -1) {  // Not the head
            next[prev[current]] = next[current];
        } else {
            head = next[current];  // Update the head
        }

        if (next[current] != -1) {  // Not the tail
            prev[next[current]] = prev[current];
        } else {
            tail = prev[current];  // Update the tail
        }

        // Add current to the free list
        next[current] = firstEmpty;
        prev[current] = -1;  // Not necessary, but for clarity
        firstEmpty = current;
    }
    numElements-=countRemoved;
    return countRemoved;
}

SortedBagIterator SortedBag::iterator() const {
    return SortedBagIterator(*this);
}

SortedBag::~SortedBag() {
    delete[] elems;
    delete[] freqs;
    delete[] next;
    delete[] prev;
}

void SortedBag::resize() {
    int newCapacity = capacity * 2;
    TElem* newElems = new TElem[newCapacity];
    int* newFreqs = new int[newCapacity];
    int* newNext = new int[newCapacity];
    int* newPrev = new int[newCapacity];
    for (int i = 0; i < capacity; i++) {
        newElems[i] = elems[i];
        newFreqs[i] = freqs[i];
        newNext[i] = next[i];
        newPrev[i] = prev[i];
    }
    // Initialize new elements
    for (int i = capacity; i < newCapacity; i++) {
        newNext[i] = i + 1;
        newPrev[i] = i - 1;
        newFreqs[i] = 0;
    }
    newNext[newCapacity - 1] = -1;
    newPrev[capacity] = capacity - 1;
    firstEmpty = capacity;
    delete[] elems;
    delete[] freqs;
    delete[] next;
    delete[] prev;
    elems = newElems;
    freqs = newFreqs;
    next = newNext;
    prev = newPrev;
    capacity = newCapacity;
}

