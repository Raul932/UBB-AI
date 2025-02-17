(
    defun sum(L)
    (
        cond
        ((null L) 0)
        ((atom L) L)
        (t
            (apply #'+ (mapcar #'sum L))
        )
    )
)

(write (sum '(((1 2) 3) (4 5))))