(
    defun nr-nodes-level(L k cnt)
    (
        cond
        ((null L) nil)
        ((atom L)
         (
            cond
            ((= k cnt) (list L))
            (t nil)
         )
        )
        (t
            (mapcan #'(lambda(X) (nr-nodes-level X k (+ 1 cnt)))L)
        )
    )
)
(write (nr-nodes-level '(a (b (c)) (d) (e (f))) 0 0))