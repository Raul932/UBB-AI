(
    defun balanced(L)
    (labels((
        get_depth(L lvl)
        (
            cond
            ((null L) lvl)
            ((atom (car L)) (get_depth (cdr L) lvl))
            (t
                (max (get_depth (car L) (+ 1 lvl)) (get_depth (cdr L) lvl))
            )
        )
    )
    (
        verify(L)
        (
            cond
            ((null L) T)
            ((< 1 (abs (- (get_depth (cadr L) 0) (get_depth (caddr L) 0)))) nil)
            (t
                (and (verify (cadr L)) (verify (caddr L)))
            )
        )
    )
    )
        (verify L)
    )
)
(write (balanced '(A (B) (C (D) (E)))))