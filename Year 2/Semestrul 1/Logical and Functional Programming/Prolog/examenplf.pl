

%problema 2
find_palindromes(_, _).
find_palindromes(ELEM, INV):-
    ELEM \= 0,
    ELEM1 is ELEM//10,
    INV1 is INV*10+(ELEM mod 10),
    find_palindromes(ELEM1, INV1).


find_sum([],0).
find_sum([H|T], Sum):-
    H1 is H,
    INV = 0,
    find_palindromes(H1, INV),
    H \= INV,
    Sum1 is Sum+H,
    find_sum(T, Sum1).


find_palindrome_sum(List, Sum):-
    Sum = 0,
    find_sum(List, Sum).



