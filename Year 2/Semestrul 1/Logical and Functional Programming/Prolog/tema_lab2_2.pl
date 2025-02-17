process_list([], []).

process_list([N, L|Rest], [N, ModifiedL|ProcessedRest]) :-
    is_list(L),
    insert_after_powers(L, N, ModifiedL),
    process_list(Rest, ProcessedRest).

process_list([L|Rest], [ModifiedL|ProcessedRest]) :-
    is_list(L),
    process_list(L, ModifiedL),
    process_list(Rest, ProcessedRest).

process_list([H|T], [H|R]) :-
    \+ is_list(H),
    process_list(T, R).
