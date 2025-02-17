(
    defun path(L X)
    (labels((
        has_node(L X)
        (
            cond
            ((null L) nil)
            ((equal X (car L)) t)
            (t
                (or (has_node (cadr L) X) (has_node (caddr L) X))
            )
        )
    )
    (
        find_path(L X)
        (
            cond
            ((null L) nil)
            ((equal (car L) X) (list (car L)))
            ((has_node (cadr L) X) (append (list (car L)) (find_path (cadr L) X )))
            ((has_node (caddr L) X) (append (list (car L)) (find_path (caddr L) X )))
        )
    )
    )
        (find_path L X)
    )
)
(write (path '(A (B) (C (D (F)) (E))) 'F))