(
    defun find_node_lvl(L X)
    (labels ((
        get_node_level(L X lvl)
        (
            cond
            ((null L) 0)
            ((equal (car L) X) lvl)
            (t
                (+ (get_node_level (cadr L) X (+ 1 lvl)) (get_node_level (caddr L) X (+ 1 lvl)))
            )
        )
    ))
        (get_node_level L X 0)
    )
)
(write (find_node_lvl '(A (B) (C (D (F)) (E))) 'F))