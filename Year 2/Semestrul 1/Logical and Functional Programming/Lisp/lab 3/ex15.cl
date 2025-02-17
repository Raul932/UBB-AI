(
    defun num(L)
    (
        cond
        ((null L) 0)
        ((atom L) 1)
        (t
            (apply #'+ (mapcar #'num L))
        )
    )
)
(write (num '(a (b (c)) (d) (e (f)))))