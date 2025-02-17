(
    defun associate(L1 L2)
    (
        cond
        ((null L1) ())
        (t
            (cons (cons (car L1) (car L2)) (associate (cdr L1) (cdr L2)))
        )
    )
)
(write (associate '(A B C) '(X Y Z)))