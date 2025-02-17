(
    defun diff-even-odd(L)
    (
        cond
        ((null L) nil)
        ((atom L)
         (
            cond
            ((= (mod L 2) 0) L)
            (t (* L -1))
         )
        )
        (t
            (apply #'+ (mapcar #'diff-even-odd L))
        )
    )
)
(write (diff-even-odd '(4 2 6 (1 ((3)) (5)) 2)))