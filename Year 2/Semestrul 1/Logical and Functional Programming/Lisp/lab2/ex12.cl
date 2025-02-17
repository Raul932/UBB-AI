(
    defun preorder(L)
    (
        cond
        ((null L) ())
        (t
            (append (list (car L)) (preorder (cadr L)) (preorder (caddr L)))
        )
    )
)
(write (preorder '(A (B) (C (D) (E)))))