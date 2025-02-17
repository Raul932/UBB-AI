#include "ShortTest.h"
#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <assert.h>

bool relation1(TComp e1, TComp e2) {
	return e1 <= e2;
}

void testAll() {
	SortedBag sb(relation1);
	sb.add(5);
	sb.add(6);
	sb.add(0);
	sb.add(5);
	sb.add(10);
	sb.add(8);
	sb.add(3);
	sb.add(3);

	assert(sb.size() == 8);
	assert(sb.nrOccurrences(5) == 2);

	assert(sb.removeOccurrences(2, 3) == 2); //il stergem pe 3 de 2 ori
	assert(sb.nrOccurrences(3)==0); //verificam daca chiar este sters

	assert(sb.removeOccurrences(1, 17)==0); //il stergem pe 17 o data, dar 17 nu este in lista
	assert(sb.nrOccurrences(17)==0);

	assert(sb.remove(5) == true);
	assert(sb.size() == 5);

	assert(sb.search(6) == true);
	assert(sb.isEmpty() == false);

	SortedBagIterator it = sb.iterator();
	assert(it.valid() == true);
	while (it.valid()) {
		it.getCurrent();
		it.next();
	}
	assert(it.valid() == false);
	it.first();
	assert(it.valid() == true);

}

