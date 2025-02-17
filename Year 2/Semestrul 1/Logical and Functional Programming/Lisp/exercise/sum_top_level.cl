(
    defun sum(L)
    (
        cond
        ((null L) 0)
        ((atom (car L)) (+ (car L) (sum (cdr L))))
        (t
            (sum (cdr L))
        )
    )
)
(write (sum '(1 2 (3 (4 5) (6 7)) 8 (9 10 11))))