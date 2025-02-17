(
    defun postorder(L)
    (
        cond
        ((null L) ())
        (t
            (append (postorder (cadr L)) (postorder (caddr L)) (list (car L)))
        )
    )
)
(write (postorder '(A (B) (C (D) (E)))))