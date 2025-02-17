(
    defun product(L)
    (
        cond
        ((null L) 1)
        ((atom L) L)
        (t
            (apply #'* (mapcar #'product L))
        )
    )
)
(write (product '(2 3 (2 ((5))))))