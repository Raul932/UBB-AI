(
    defun inorder(L)
    (
        cond
        ((null L) ())
        (t (append (inorder (cadr L)) (list(car L)) (inorder (caddr L))))
    )
)
(write (inorder '(A (B) (C (D) (E)))))