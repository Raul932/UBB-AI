% current_max, if L = []
% find_max(tail(L), max(head(L), current_max)) otherwise
find_max([H|T], Max) :-
    find_max(T, H, Max).

find_max([], Max, Max).
find_max([H|T], CurrentMax, Max) :-
    (   H > CurrentMax
    ->  find_max(T, H, Max)
    ;   find_max(T, CurrentMax, Max)
    ).
% [] if L = []
% remove_all(tail(L), max_elem) if head(L)=max_elem
% [head(L) + remove_all(tail(L), max_elem)] otherwise
remove_all(_, [], []).
remove_all(Elem, [Elem|T], R) :-
    remove_all(Elem, T, R).
remove_all(Elem, [H|T], [H|R]) :-
    Elem \= H,
    remove_all(Elem, T, R).
remove_max_occurrences(List, Result) :-
    find_max(List, Max),
    remove_all(Max, List, Result).
