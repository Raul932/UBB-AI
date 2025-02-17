(
    defun delets(L elem)
    (
        cond
        ((null L) nil)
        ((atom L)
         (
            cond
            ((not(equal L elem)) L)
         )
        )
        (t
           (mapcar #'(lambda(X) (delets X elem))L)
        )
    )
)
(write (delets '(a (b (c) b) (d) (e (f))) 'b))