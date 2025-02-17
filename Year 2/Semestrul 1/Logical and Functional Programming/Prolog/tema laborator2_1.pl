insert_position(1).
insert_position(Pos) :-
    insert_position(P),
    Pos is 2 * P + 1.

% Main
insert_after_powers(List, Value, Result) :-
    insert_helper(List, Value, 1, 1, Result).

% list, value, currentposition, nextposition, result
insert_helper([], _, _, _, []).

insert_helper([H|T], Value, Pos, NextInsertPos, [H|R]) :-
    Pos \= NextInsertPos,
    NextPos is Pos + 1,
    insert_helper(T, Value, NextPos, NextInsertPos, R).

insert_helper([H|T], Value, Pos, Pos, [H,Value|R]) :-
    NextInsertPos is 2 * Pos + 1,
    NextPos is Pos + 1,
    insert_helper(T, Value, NextPos, NextInsertPos, R).
