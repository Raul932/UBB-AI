(
    defun membs(L elem)
    (
        cond
        ((null L) nil)
        ((atom L) 
         (
            cond
            ((equal L elem) t)
            (t nil)
         )
        )
        (t
            (mapcan #'(lambda (L) (membs L elem)) L)
        )
    )
)
; (write (membs '(((A B) C) (D E)) 'F))
(write (membs '(A (B (C)) (D) (E (F))) 'B))