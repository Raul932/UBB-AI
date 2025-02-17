% main
unique_elements(Input, Result) :-
    filter_unique(Input, [], Result).

% base case
% [] if L = []
% filter_unique(tail(L)) if contains(head(L), tail(L)) or
% contains(head(L), seen)
% [head(L)] + filter_unique(tail(L)) if !contains(head(L), tail(L)) and
% !contains(head(L), seen)
filter_unique([], _, []).

% recursive case
filter_unique([H|T], Seen, Result) :-
    (contains(H, Seen) ; contains(H, T)),
    !,
    filter_unique(T, [H|Seen], Result).

filter_unique([H|T], Seen, [H|Result]) :-
    \+ contains(H, Seen),
    \+ contains(H, T),
    filter_unique(T, [H|Seen], Result).
%false, if L = []
%true, if head(L) = X
%contains(X, tail(L)) otherwise
contains(X, [X|_]) :- !.
contains(X, [_|Tail]) :-
    contains(X, Tail).
contains(_, []) :- fail.

