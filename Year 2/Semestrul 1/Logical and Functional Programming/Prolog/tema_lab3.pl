bet(1).
bet(x).
bet(2).

valid_bets(Bets) :-
    Bets = [B1, B2, B3, B4],
    bet(B1), bet(B2), bet(B3), bet(B4),
    B4 \= 2,  %last bet cant be 2
    count_x(Bets, CountX),
    CountX =< 2.  %no more than two x bets

count_x([], 0).
count_x([x|T], Count) :-
    count_x(T, CountTail),
    Count is CountTail + 1.
count_x([H|T], Count) :-
    H \= x,
    count_x(T, Count).

all_valid_bets(AllBets) :-
    findall(Bets, valid_bets(Bets), AllBets).


