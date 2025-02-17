(
    defun height(tree h)
    (
        cond
        ((null tree) 0)
        ((atom tree) h)
        (t
            (apply #'max (mapcar #'(lambda(X) (height X (+ 1 h))) tree))
        )
    )
)
(write (height '(a (b (c)) (d) (e (f))) 0))