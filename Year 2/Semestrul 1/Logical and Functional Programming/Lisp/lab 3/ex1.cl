(
    defun depth(L)
    (
        cond
        ((null L) 1)
        ((atom L) 0)
        (t
            (+ 1 (apply #'max (mapcar #'depth L)))
        )
    )
)

(write (depth '(a (b (c)) d)))