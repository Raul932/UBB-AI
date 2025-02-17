(
    defun perms(L)
    (
        cond
        ((null L) (list nil))
        (t
            (
                mapcan #'(lambda (E) 
                (
                    mapcar #'(lambda (P) (cons E P))
                    (perms (remove E L))
                )
                ) L
            ) 
        )
    )
)

(write (perms '(1 2 3 4)))