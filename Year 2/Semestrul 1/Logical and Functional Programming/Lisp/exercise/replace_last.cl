(
    defun replc(L)
    (
        cond
        ((null L) nil)
        ((atom (car L)) (append (list (car L)) (replc (cdr L))))
        (t
            (append (last (car L)) (replc (cdr L)))
        )
    )
)
(
    defun has_sublists(L)
    (
        cond
        ((null L) t)
        ((listp (car L)) nil)
        (t
            (has_sublists (cdr L))
        )
    )
)
(
    defun replace_all(L)
    (
        cond
        ((has_sublists L) L)
        (t
            (replace_all (replc L))
        )
    )
)
(write (replace_all '(a (b c) (d ((e) f)))))