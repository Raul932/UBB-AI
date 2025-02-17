#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;

// Complexity: Θ(1)
int MultiMap::hashFunction(TKey k) {
	int pos = k % this->capacity;
	if (pos > 0) {
		return pos;
	}
	return -pos;
}
//O(n)
MultiMap::MultiMap() {
	this->capacity = 13;
	this->firstEmpty = 0;
	this->dim = 0;
	this->hashTable = new TElem[this->capacity];
	this->next = new int[this->capacity];
	for (int i = 0; i < this->capacity; i++) {
		this->next[i] = -1;
		this->hashTable[i].first = -1000000;
		this->hashTable[i].second = -1000000;
	}
}

//O(n*n) daca trebuie resize si rehash
void MultiMap::add(TKey c, TValue v) {
	if (this->dim == this->capacity){
		this->resize();
		this->rehashing();
	}
	int pos = this->hashFunction(c);
	if (this->hashTable[pos].first == -1000000){
		this->hashTable[pos].first = c;
		this->hashTable[pos].second = v;
		this->next[pos] = -1;
		this->dim += 1;
	}
	else {
		this->hashTable[this->firstEmpty].first = c;
		this->hashTable[this->firstEmpty].second = v;
		this->next[this->firstEmpty] = -1;
		while (this->next[pos] != -1)
			pos = this->next[pos];
		this->next[pos] = this->firstEmpty;
		this->dim += 1;
	}
	if (this->hashTable[firstEmpty].first != -1000000)
		this->firstEmpty = this->updateFirstEmpty();
}

//O(n)
bool MultiMap::remove(TKey c, TValue v) {
	int pos = this->hashFunction(c);
	int posAnterior = -1;
	
	for (int i=0; i<this->capacity && posAnterior == -1; i++)
		if (this->next[i] == pos)
			posAnterior = i;

	while (pos != -1 && !(this->hashTable[pos].first == c && this->hashTable[pos].second == v)){
		posAnterior = pos;
		pos = this->next[pos];
	}
	if (pos == -1){
		return false;
	} else {
		bool ok = false;
		int posNext;
		int posCurrent;
		do {
			posNext = this->next[pos];
			posCurrent = pos;
			while (posNext != -1 && this->hashFunction(this->hashTable[posNext].first) != pos) {
				posCurrent = posNext;
				posNext = this->next[posNext];
			}
			if (posNext == -1) ok = true;
			else {
				this->hashTable[pos] = this->hashTable[posNext];
				posAnterior = posCurrent;
				pos = posNext;
			}
		} while (!ok);
		if (posAnterior != -1){
			this->next[posAnterior] = this->next[pos];
		}
		this->hashTable[pos].first = -1000000;
		this->hashTable[pos].second = -1000000;
		this->next[pos] = -1;
		if (this->firstEmpty > pos){
			this->firstEmpty = pos;
		}
		this->dim -= 1;
		return true;
	}
}

//O(1) amortizat
vector<TValue> MultiMap::search(TKey c) {
	vector<TValue> values;
	int pos = this->hashFunction(c);
	while (pos != -1){
		if (this->hashTable[pos].first == c) values.push_back(this->hashTable[pos].second);
		pos = this->next[pos];
	}
	return values;
}

//O(1)
int MultiMap::size() const {
	return this->dim;
}

//O(1)
bool MultiMap::isEmpty() const {
	return this->dim == 0;
}
//O(n*n)
void MultiMap::rehashing() {
	TElem* oldHashTable = new TElem[this->capacity];
	for (int i=0; i < this->capacity; i++){
		oldHashTable[i] = this->hashTable[i];
	}
	int oldDim = this->dim;
	for(int i=0; i < this->capacity; i++){
		this->hashTable[i].first = -1000000;
		this->hashTable[i].second = -1000000;
		this->next[i] = -1;
	}
	this->firstEmpty = this->updateFirstEmpty();
	this->dim = 0;
	for (int i=0; i < oldDim; i++)
		this->add(oldHashTable[i].first, oldHashTable[i].second);
}

//O(n*n)
void MultiMap::resize() {
	TElem* newHashTable = new TElem[this->capacity * 2];
    int* newNext = new int[this->capacity * 2];
    for (int i = 0; i < this->capacity * 2; i++) {
        newHashTable[i].first = -1000000;
		newHashTable[i].second = -1000000;
        newNext[i] = -1;
    }
    for (int i = 0; i < this->capacity; i++) {
        if (this->hashTable[i].first != -1000000 && this->hashTable[i].second != -1000000) {
            newHashTable[i] = this->hashTable[i];
            newNext[i] = this->next[i];
        }
    }
    delete[] this->hashTable;
    delete[] this->next;
    this->hashTable = newHashTable;
    this->next = newNext;
    this->capacity *= 2;
    this->firstEmpty = this->updateFirstEmpty();
}
//O(n)
int MultiMap::updateFirstEmpty() {
	for (int i=0; i<this->capacity; i++){
		if (this->hashTable[i].first == -1000000) return i;
	}
}

//O(n) n-dimension of MultiMap m
int MultiMap::addIfNotPresent(MultiMap& m){
	int count = 0;
	for (MultiMapIterator it = m.iterator(); it.valid(); it.next()){
		TElem elem = it.getCurrent();
		TKey key = elem.first;
		TValue value = elem.second;

		vector<TValue> results = this->search(key);
		if (results.empty()){
			this->add(key, value);
			count++;
		}
	}
	return count;
}

MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}


MultiMap::~MultiMap() {
	delete[] this->hashTable;
	delete[] this->next;
}

