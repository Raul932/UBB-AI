#pragma once
#include "SortedBag.h"

class SortedBag;

class SortedBagIterator
{
	friend class SortedBag;

private:
	const SortedBag& bag;	
	Node* current;


public:
	SortedBagIterator(const SortedBag& b);
	TComp getCurrent();
	bool valid();
	void next();
	void first();
};

