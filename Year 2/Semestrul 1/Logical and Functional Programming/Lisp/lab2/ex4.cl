(
    defun convert(tree)
    (   
        cond
        ((null tree) nil)
        (t
            (
                cond
                ((and (cadr tree) (caddr tree)) (append (list (car tree)) (list 2) (convert (cadr tree)) (convert (caddr tree))))
                ((cadr tree) (append (list (car tree)) (list 1) (convert (cadr tree))))
                ((caddr tree) (append (list (car tree)) (list 1) (convert (caddr tree))))
                (t
                    (append (list (car tree)) (list 0))
                )
            )
        )
    )
)
(write (convert '(A (B) (C (D) (E)))))