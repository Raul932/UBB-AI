#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <iostream>
using namespace std;
SortedBag::SortedBag(Relation r){
	head = nullptr;
	relation = r;
}
//O(n)
void SortedBag::add(TComp e) {
    Node* current = head;
    Node* prev = nullptr;

	//Parcurgem lista pana ajungem la elementul potrivit
    while (current != nullptr && relation(current->element, e)) {
		prev = current;

		if(current != nullptr and current->element == e){
			current->frequency += 1; //Daca avem elementul in lista ii incrementam frecventa si am terminat
			return;
		}

        current = current->next;
    }

	Node* newNode = new Node(e, 1);
	if (prev == nullptr) {

		head = newNode;

		if(current != nullptr){
			head->next = current; //Aici adaugam elementul cand este goala lista
		}
	} else {

		prev->next = newNode;

		if(current != nullptr)
			newNode->next = current; //Aici adaugam elementul cand nu este goala lista
	}
}

//O(find(e))
bool SortedBag::remove(TComp e) {
	Node* current = find(e);
    if (current != nullptr) {
        if (current->frequency > 1) {
            current->frequency--; // Decrementam frecventa daca apare de mai multe ori
        } else {
            // Stergem nodul daca apare doar o data
            if (current == head) {
                head = head->next;
            } else {
                Node* prev = findPrevious(e);
                prev->next = current->next;
            }
            delete current;
        }
        return true;
    }
    return false;
}

//O(n)
bool SortedBag::search(TComp elem) const {
	Node* current = head;
	while (current!=nullptr && current->element != elem){
		current=current->next;
	}
	return current != nullptr;
}

//O(find(elem))
int SortedBag::nrOccurrences(TComp elem) const {
	Node* current = find(elem);
    return current != nullptr ? current->frequency : 0;
}

//O(n)
int SortedBag::size() const {
	int count=0;
	Node* current = head;
	while(current!=nullptr){
		count += current->frequency;
		current = current->next;
	}
	return count;
}

//O(1)
bool SortedBag::isEmpty() const {
	return head==nullptr;
}

//O(1)
SortedBagIterator SortedBag::iterator() const {
	return SortedBagIterator(*this);
}

//O(n)
Node* SortedBag::find(TComp elem) const{
	Node* current = head;
	while(current != nullptr && current->element != elem){
		current=current->next;
	}
	return current;
}

//O(n)
Node* SortedBag::findPrevious(TComp elem) const{
	Node* prev = nullptr;
	Node* current = head;
	while (current != nullptr && current->element != elem){
		prev = current;
		current = current->next;
	}
	return prev;
}

//O(1)
void SortedBag::insertAfter(Node* prev, TComp elem) {
	if (prev == nullptr){
		head = new Node(elem, 1, head);
	} else {
		prev->next = new Node(elem, 1, prev->next);
	}
}

//O(1)
void SortedBag::removeFromList(Node* prev) {
	if (prev==nullptr || prev->next == nullptr)
		return;
	
	Node* current = prev->next;
	if (current->frequency>1){
		current->frequency--;
	} else {
		prev->next = current->next;
		delete current;
	}
}

//Best case: O(1)
//Worst case: O(nr*n)
int SortedBag::removeOccurrences(int nr, TElem elem){
	if(nr < 0)
		throw exception();
	int removed=0;
	while(removed<nr){
		Node* current=find(elem);
		if(current!=nullptr){
			int remainingElements=current->frequency - removed;
			if(remainingElements <= 0){
				//stergem nodul daca nu mai are aparitii
				removeFromList(findPrevious(elem));
				removed += current->frequency;
			} else {
				//schimbam frecventa daca mai exista elemente
				current->frequency-= nr - removed;
				removed=nr;
			}
		} else {
			//daca nu gasim elementul oprim while-ul
			break;
		}
	}
	return removed;
}

//O(n)
SortedBag::~SortedBag() {
	while (head!=nullptr){
		Node* temp = head;
		head = head->next;
		delete temp;
	}
}
