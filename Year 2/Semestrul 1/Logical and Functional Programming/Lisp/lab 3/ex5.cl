(
    defun membs(tree node)
    (
        cond
        ((null tree) nil)
        ((atom tree) 
         (
            cond
            ((equal tree node) (list t))
            (t nil)
         )
        )
        (t
            (mapcan #'(lambda(tree) (membs tree node)) tree)
        )
    )
)
(write (membs '(A (B (C)) (D) (E (F))) 'B))