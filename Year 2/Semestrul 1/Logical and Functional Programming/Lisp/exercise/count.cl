(
    defun remove_all(L elem)
    (
        cond
        ((null L) nil)
        ((atom (car L))
         (
            cond
            ((not(equal (car L) elem)) (append (list (car L)) (remove_all (cdr L) elem)))
            (t
                (remove_all (cdr L) elem)
            )
         )
        )
        (t
            (cons (remove_all (car L) elem) (remove_all (cdr L) elem))
        )
    )
)
;(write (remove_all '(1 2 3 (1 2 (1)) (3 2 1) 1) 1))
(
    defun cnt(L elem)
    (
        cond
        ((null L) 0)
        ((atom (car L))
         (
            cond
            ((equal (car L) elem) (+ 1 (cnt (cdr L) elem)))
            (t
                (cnt (cdr L) elem)
            )
         )
        )
        (t
            (+ (cnt (car L) elem) (cnt (cdr L) elem))
        )
    )
)
;(write (cnt '(1 2 3 (1 2 nil (1)) (3 2 1) 1) 1))
(
    defun count_all(L)
    (
        cond
        ((null L) nil)
        ((null (car L)) (count_all (cdr L)))
        (t
            (cons (list (car L) (cnt L (car L))) (count_all (remove_all L (car L))))
        )
    )
)
(write (count_all '(1 2 3 1)))
