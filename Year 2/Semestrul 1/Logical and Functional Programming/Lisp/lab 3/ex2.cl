(
    defun get-atoms(L)
    (
        cond
        ((null L) nil)
        ((atom L) (list L))
        (t
            (mapcan #'get-atoms L)
        )
    )
)
(write (get-atoms '(((A B) C) (D E))))