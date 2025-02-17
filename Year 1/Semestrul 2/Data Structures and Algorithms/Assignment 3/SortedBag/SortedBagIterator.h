#pragma once
#include "SortedBag.h"

class SortedBag;

class SortedBagIterator
{
	friend class SortedBag;

private:
	const SortedBag& bag;
	int current;

	//TODO - Representation

public:
	SortedBagIterator(const SortedBag& b);
	TComp getCurrent();
	bool valid();
	void next();
	void first();
};

