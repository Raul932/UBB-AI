(
    defun insert(L elem)
    (
        cond
        ((null L) (list elem))
        ((< elem (car L)) (append (list elem) L))
        ((= elem (car L)) L)
        (t
            (append (list (car L)) (insert (cdr L) elem))
        )
    )
)
(
    defun insertion_sort(L)
    (
        cond
        ((null L) nil)
        (t
            (insert (insertion_sort(cdr L)) (car L))
        )
    )
)
(
    defun interleave(L1 L2)
    (
        cond
        ((null L1) L2)
        ((null L2) L1)
        ((< (car L1) (car L2)) (append (list (car L1)) (interleave (cdr L1) L2)))
        ((= (car L1) (car L2)) (append (list (car L1)) (interleave (cdr L1) (cdr L2))))
        (t
            (append (list (car L2)) (interleave L1 (car L2)))
        )
    )
)
(
    defun interleave_no_duplicates(L1 L2)
    (
        interleave (insertion_sort L1) (insertion_sort L2)
    )
)
(write (interleave_no_duplicates '(5 2 3 4 1 1 1) '(6 5 2 7)))