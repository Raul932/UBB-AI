(
    defun pair(elem L)
    (
        cond
        ((null L) nil)
        (t
            (cons (list elem (car L)) (pair elem (cdr L)))
        )
    )
)
(
    defun make_all_pairs(L)
    (
        cond
        ((null L) nil)
        (t
            (append (pair (car L) (cdr L)) (make_all_pairs (cdr L)))
        )
    )
)
(write (make_all_pairs '(a b c d)))