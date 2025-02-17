(
    defun replc(L elem lst)
    (
        cond
        ((null L) nil)
        ((atom L) 
         (
            cond
            ((equal L elem) lst)
            (t L)
         )
        )
        (t
            (mapcar #'(lambda(L) (replc L elem lst)) L)
        )
    )
)
(write (replc '(((1 2) 3) (4 5) 2) 2 '(3 4)))