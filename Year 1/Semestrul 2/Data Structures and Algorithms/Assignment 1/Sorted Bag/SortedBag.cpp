#include "SortedBag.h"
#include "SortedBagIterator.h"

//O(1)
SortedBag::SortedBag(Relation r) {
	this->capacity = 10;
	this->current_size = 0;
	this->elements = new Pair[this->capacity];
	this->r = r;
}	

//O(n*n) if we exceed the capacity
void SortedBag::add(TComp e) {

	int position = findPosition(e);
	if (position!=-1 && this->elements[position].element==e){
		this->elements[position].frequency++;
	} else {
		if (this->current_size==this->capacity){
			this->capacity*=2;
			Pair* doubledElements = new Pair[this->capacity];
			for (int i=0; i<this->current_size;i++)
				doubledElements[i]=this->elements[i];
		
		delete[] this->elements;
		this->elements=doubledElements;
		}
		for(int j=this->current_size;j>position;j--){
			this->elements[j]=this->elements[j-1];
		}
		this->elements[position].element=e;
		this->elements[position].frequency = 1;
        this->current_size++;

	}

}

//O(n*n) if we get the last element in the bag
bool SortedBag::remove(TComp e) {
	int position = findPosition(e);
    if (position != -1 && this->elements[position].element == e) {
        if (this->elements[position].frequency > 1) {
            this->elements[position].frequency--;
        } else {
            for (int j = position; j < this->current_size - 1; j++) {
                this->elements[j] = this->elements[j + 1];
            }
            this->current_size--;
        }
        return true;
    }
    return false;
}

//O(log2 n)
bool SortedBag::search(TComp elem) const {
	int position = findPosition(elem);
    return (position != -1 && this->elements[position].element == elem);
}

//O(n*n)
int SortedBag::nrOccurrences(TComp elem) const {
	int position = findPosition(elem);
    if (position != -1 && this->elements[position].element == elem) {
        return this->elements[position].frequency;
    }
    return 0;
}

//O(log2 n)
int SortedBag::findPosition(TComp e) const{
	int left=0;
	int right=this->current_size-1;
	while(left<=right){
		int mid=(left+right)/2;
		if(this->elements[mid].element==e)
			return mid;
		if(this->r(this->elements[mid].element, e))
			left=mid+1;
		else
			right=mid-1;
	}
	return left;
}

//O(1)
int SortedBag::size() const {
	return this->current_size;
	return 0;
}

//O(1)
bool SortedBag::isEmpty() const {
	return this->current_size == 0;
}

//O(1)
SortedBagIterator SortedBag::iterator() const {
	return SortedBagIterator(*this);
}

//O(1)
SortedBag::~SortedBag() {
	delete[] this->elements;
}

