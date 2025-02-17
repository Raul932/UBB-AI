(
    defun rvs(L)
    (
        cond
        ((null L) nil)
        ; ((atom (car L)) (append (rvs (cdr L)) (list (car L))))
        (t
            (append (rvs (cdr L)) (list (car L)))
        )
    )
)
;(write (rvs '(a (b c) (d) (e (f)))))

(
    defun revers(L)
    (
        cond
        ((null L) nil)
        (t
            (rvs(
                mapcar #'(lambda(X)
                (
                    cond
                    ((atom X) X)
                    (t
                        (revers X)
                    )
                )) L
            ))
        )
    )
)
(write (revers '(a (b c) (d) (e (f)))))