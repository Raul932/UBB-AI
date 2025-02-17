#include "SMIterator.h"
#include "SortedMap.h"
#include <exception>
using namespace std;

SortedMap::SortedMap(Relation r) {
	this->root = nullptr;
    this->rel = r;
    this->nrElements = 0;
}

TValue SortedMap::add(TKey k, TValue v) {
	if (this->root == nullptr) {
        // add the root if it's empty
        auto * newNode = new BSTNode();
        newNode->info.first = k;
        newNode->info.second = v;
        this->root = newNode;
        this->nrElements++;
        return NULL_TVALUE;
    } else {
        if(this->root->info.first == k){
            //add to the root if it's the same key
            int returnValue = root->info.second;
            root->info.second = v;
            return returnValue;
        }
        BSTNode* currentNode = this->root;
        BSTNode* previousNode = nullptr;

        //find the parent of the new node

        while (currentNode != nullptr) {
            // we go to the next based on the BST order
            if (this->rel(k, currentNode->info.first)) {
                //left case
                previousNode = currentNode;
                currentNode = currentNode->left;
            } else {
                //right case
                previousNode = currentNode;
                currentNode = currentNode->right;
            }
            if(currentNode != nullptr) { // if we haven't reached the end
                if (currentNode->info.first == k) { // if we found the slot where we should add it
                    int returnValue = currentNode->info.second;
                    currentNode->info.second = v;
                    return returnValue;
                }
            }
        }
        // otherwise, add the new node and connect it to its parent
        auto * newNode = new BSTNode();
        newNode->info.first = k;
        newNode->info.second = v;
        newNode->parent = previousNode;
        this->nrElements++;
        if (this->rel(k, previousNode->info.first)) {
            //node will be the left child
            previousNode->left = newNode;
            return NULL_TVALUE;
        } else {
            //node will be the right child
            previousNode->right = newNode;
            return NULL_TVALUE;
        }
    }
}

TValue SortedMap::search(TKey k) const {
	TValue returnValue = NULL_TVALUE;
    BSTNode* currentNode = this->root;
    if(currentNode == nullptr){
        return returnValue;
    }
    if(currentNode->info.first == k){
        returnValue = currentNode->info.second;
        return returnValue;
    }
    while(currentNode != nullptr){ // we go through tree
        if(this->rel(k, currentNode->info.first)){
            currentNode = currentNode->left;
        }
        else{
            currentNode = currentNode->right;
        }
        if(currentNode != nullptr) {
            if (currentNode->info.first == k) { // if we found the key we return the value
                returnValue = currentNode->info.second;
                return returnValue;
            }
        }
    }
    return returnValue;
}

TValue SortedMap::remove(TKey k) {
	TValue returnValue = NULL_TVALUE;
    bool found = false;
    BSTNode* currentNode = this->root;
    if(currentNode == nullptr){
        return returnValue;
    }
    if(currentNode->info.first == k){
        returnValue = currentNode->info.second;
        this->removeRoot(currentNode);
        this->nrElements--;
        return returnValue;
    }
    while(currentNode != nullptr && !found){
        if(this->rel(k, currentNode->info.first)){
            currentNode = currentNode->left;
        }else{
            currentNode = currentNode->right;
        }
        if(currentNode != nullptr) {
            if (currentNode->info.first == k) {
                returnValue = currentNode->info.second;
                if (currentNode == this->root) {
                    this->removeRoot(currentNode);
                } else {
                    this->removeNode(currentNode);
                }
                this->nrElements--;
                return returnValue;
            }
        }
    }
    return NULL_TVALUE;
}

int SortedMap::size() const {
	return this->nrElements;
}

bool SortedMap::isEmpty() const {
	if(this->nrElements == 0)
        return true;
    return false;
}

SMIterator SortedMap::iterator() const {
	return SMIterator(*this);
}

SortedMap::~SortedMap() {
	this->deleteSortedMap(this->root);
}

void SortedMap::deleteSortedMap(BSTNode *currentNode) {
    if(currentNode != nullptr){
        if(currentNode->left != nullptr){
            this->deleteSortedMap(currentNode->left);
        }
        if(currentNode->right != nullptr){
            this->deleteSortedMap(currentNode->right);
        }
    }
    delete currentNode;
}

// BC: Theta(1)
// WC: Theta(height) - search for max and max is leaf
void SortedMap::removeRoot(BSTNode *toRemove) {
    if (toRemove->right != nullptr && toRemove->left != nullptr) {
        //remove a root with two children
        BSTNode* replacementNode = this->findMaxLeftSide(toRemove);
        if (replacementNode == toRemove->left) {
            // the replacement is the left child, no new links made
            replacementNode->right = toRemove->right;
            replacementNode->parent = nullptr;
            BSTNode *rootToDelete = toRemove;
            this->root = replacementNode;
            delete rootToDelete;
        } else {
            // the replacement is not the left child, additional links need to be done
            replacementNode->right = toRemove->right;
            replacementNode->right->parent = replacementNode;
            replacementNode->parent = nullptr;
            replacementNode->left = toRemove->left;
            BSTNode* rootToDelete = toRemove;
            this->root = replacementNode;
            delete rootToDelete;
        }
    }
    else if (toRemove->right == nullptr && toRemove->left == nullptr) {
        //remove a leaf root
        BSTNode* rootToDelete = toRemove;
        this->root = nullptr;
        delete rootToDelete;
    } else {
        //remove a root with one  child
        if (toRemove->right != nullptr) {
            //the root has a right child
            BSTNode* newRoot = toRemove->right;
            BSTNode* rootToDelete = toRemove;
            newRoot->parent = nullptr;
            this->root = newRoot;
            delete rootToDelete;
        }
        else {
            //the root has a left child
            BSTNode* newRoot = toRemove->left;
            BSTNode* rootToDelete = toRemove;
            newRoot->parent = nullptr;
            this->root = newRoot;
            delete rootToDelete;
        }
    }
}

//BC: Theta(1)
//WC: Theta(height)
void SortedMap::removeNode(BSTNode* toRemove) {
    if (toRemove->right != nullptr && toRemove->left != nullptr) {
        //remove a node with two children
        BSTNode* replacementNode = this->findMaxLeftSide(toRemove);
        if (replacementNode == toRemove->left) {
            //the replacement is the left child, no links need to be done
            replacementNode->right = toRemove->right;
            replacementNode->parent = toRemove->parent;
            BSTNode* nodeToDelete = toRemove;
            delete nodeToDelete;
        } else {
            // the replacement is not the immediate left child, we need additional links
            replacementNode->right = toRemove->right;
            replacementNode->parent = toRemove->parent;
            replacementNode->left = toRemove->left;
            BSTNode* nodeToDelete = toRemove;
            delete nodeToDelete;
        }
    } else if (toRemove->right == nullptr && toRemove->left == nullptr) {
        //remove a leaf node
        if (toRemove->parent->left == toRemove) {
            // the node is the left child of its parent
            toRemove->parent->left = nullptr;
            delete toRemove;
        } else if (toRemove->parent->right == toRemove) {
            // the node is the right child of its parent
            toRemove->parent->right = nullptr;
            delete toRemove;
        }
    } else {
        //remove a node with one child
        if (toRemove->parent->left == toRemove) {
            // the node is the left child of its parent
            if (toRemove->left != nullptr) {
                // if the node has a left child, we need to link it to the parent of its parent
                toRemove->parent->left = toRemove->left;
                toRemove->left->parent = toRemove->parent;
            } else {
                // if the node has a right child, we need to link it to the parent of its parent
                toRemove->parent->left = toRemove->right;
                toRemove->right->parent = toRemove->parent;
            }
            delete toRemove;
        } else {
            // the node is the right child of its parent
            if (toRemove->left != nullptr) {
                // if the node has a left child, we need to link it to the parent of its parent
                toRemove->parent->right = toRemove->left;
                toRemove->left->parent = toRemove->parent;
            } else {
                // if the node has a right child, we need to link it to the parent of its parent
                toRemove->parent->right = toRemove->right;
                toRemove->right->parent = toRemove->parent;
            }
            delete toRemove;
        }
    }
}

//BC: Theta(1)
//WC: Theta(height)
BSTNode* SortedMap::findMaxLeftSide(BSTNode* currentNode) {
    //find the maximum of the left subtree
    BSTNode* node = currentNode->left;
    bool changed = false;
    while (node->right != nullptr) {
        node = node->right;
        changed = true;
    }
    if (changed) {
        node->parent->right = nullptr;
    }
    return node;
}

// BC: Theta(1)
// Theta(nrElements)
void SortedMap::replace(TKey k, TValue oldValue, TValue newValue) {
    BSTNode* currentNode = this->root;
    if(currentNode == nullptr){ // empty tree
        return;
    }
    if(currentNode->info.first == k){ // first key is the key
        if(currentNode->info.second == oldValue){ // if condition then we add
            currentNode->info.second = newValue;
        }
        return;
    }
    while(currentNode != nullptr){ // we go through tree
        if(this->rel(k, currentNode->info.first)){
            currentNode = currentNode->left;
        }
        else{
            currentNode = currentNode->right;
        }
        if(currentNode != nullptr) {
            if (currentNode->info.first == k) { // if we found the key
                if(currentNode->info.second == oldValue){ // if condition then we add
                    currentNode->info.second = newValue;
                }
                return;
            }
        }
    }
}


//BC O(height)
//AC O(log n)
//WC O(n)
int SortedMap::getKeyRange() const {
    if (this->root == nullptr) {
        // If the map is empty, return -1
        return -1;
    }

    BSTNode* current = this->root;
    // Find the minimum key (leftmost node)
    while (current->left != nullptr) {
        current = current->left;
    }
    TKey minKey = current->info.first;

    current = this->root;
    // Find the maximum key (rightmost node)
    while (current->right != nullptr) {
        current = current->right;
    }
    TKey maxKey = current->info.first;

    // Return the difference between the maximum and minimum key
    return maxKey - minKey;
}
