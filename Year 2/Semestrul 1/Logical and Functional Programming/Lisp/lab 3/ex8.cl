(
    defun maxim(L maxi)
    (
        cond
        ((null L) maxi)
        ((atom L)
         (
            cond
            ((< maxi L) L)
            (t maxi)
         )
        )
        (t
            (apply #'max (mapcar #'(lambda(L) (maxim L maxi)) L))
        )
    )
)
(write (maxim '(((1 2) 3) (4 5) 2) 0))