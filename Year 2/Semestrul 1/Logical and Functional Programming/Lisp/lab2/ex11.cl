(
    defun get_lvl_max_nodes(L)
    (labels((
        get_level(L lvl ans cnt)
        (
            cond
            ((null L) ans)
            ((equal cnt lvl) (append (list (car L)) ans))
            (t
                (append ans (get_level (cadr L) lvl '() (+ 1 cnt)) (get_level (caddr L) lvl '() (+ 1 cnt)))
            )
        )
    )
    (
        get_depth(L lvl)
        (
            cond
            ((null L) lvl)
            ((atom (car L)) (get_depth (cdr L) lvl))
            (t
                (max (get_depth (car L) (+ 1 lvl)) (get_depth (cdr L) lvl))
            )
        )
    )
    (
        get_all_lvls(L lvls ans cnt)
        (
            cond
            ((equal lvls cnt) ans)
            (t
                (append ans (list (get_level L cnt '() 0)) (get_all_lvls L lvls '() (+ 1 cnt)))
            )
        )
    )
    (
        get_final_answer(L lvl maxi ans)
        (
            cond
            ((null L) (cons (list maxi) ans))
            ((< maxi (length (car L))) (get_final_answer (cdr L) (+ 1 lvl) (length (car L)) (car L)))
            (t
                (get_final_answer (cdr L) (+ 1 lvl) maxi ans)
            )
        )
    )
    )
        (get_final_answer (get_all_lvls L (get_depth L 0) '() 0) 0 0 '())
    )
)
(write (get_lvl_max_nodes '(A (B) (C (D (F)) (E)))))