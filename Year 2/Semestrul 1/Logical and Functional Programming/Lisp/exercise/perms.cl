(
    defun permutations(L)
    (
        cond
        ((null (cdr L)) (list L))
        (t
            (
                mapcan  #'(
                    lambda (elem) 
                    ( 
                        mapcar #'(lambda(auxList) (cons elem auxList)) (permutations (remove elem L))
                    )
                ) L              
            )
        )
    )
)

(write (permutations '(1 2 3)))
; (1 2 3)
; (1 3 2)
; (2 3 1)
; (2 1 3)
; (3 1 2)
; (3 2 1)
