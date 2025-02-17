(
    defun first-top-levels(L)
    (labels((
        find-depth(L cnt)
        (
            cond
            ((null L) 0)
            ((atom (car L)) (find-depth (cdr L) cnt))
            (t
                (max (+ 1 (find-depth (car L) 0)) (+ 1 (find-depth (cdr L) 0)))
            )
        )
    )
    (
        only-desired-sublst(L cnt depth aux)
        (
            cond
            ((null L) aux)
            ((equal cnt depth) ())
            ((atom (car L)) (cons (car L) (only-desired-sublst (cdr L) cnt depth aux)))
            (t
                (cons (only-desired-sublst (car L) (+ 1 cnt) depth aux) (only-desired-sublst (cdr L) cnt depth aux))
            )
        )
    )
    (
        first-elems(L aux)
        (
            cond
            ((null L) aux)
            ((equal 0 (length aux)) (first-elems (cdr L) (append (list (car L)) aux)))
            ((atom (car L)) (first-elems (cdr L) aux))
            (t
                (append  (first-elems (car L) '()) (first-elems (cdr L) aux))
            )
        )
    )
    )
        (first-elems (only-desired-sublst L 1 (find-depth L 0) '()) '())
    )
)
(write (first-top-levels '(1 2 (3 (4 5) (6 7)) 8 (9 10 11))))