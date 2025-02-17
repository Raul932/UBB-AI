insertM(L, _, M, CPoz, []) :- L=[],CPoz mod M=\=0.
insertM(L, E, M, CPoz, [E]) :- L=[],CPoz mod M=:=0.
insertM(L, E, M, CPoz, [H|R]):-
    L=[H|T],
    CPoz mod M=\=0,
    CPoz1 is CPoz+1,
    insertM(T, E, M, CPoz1, R).
insertM(L, E, M, CPoz, [E|R]):-
    L=[_|_],
    CPoz mod M =:=0,
    CPoz1 is CPoz+1,
    insertM(L, E, M, CPoz1, R).

insertMain(L, E, M, R):-insertM(L, E, M, 1, R).




