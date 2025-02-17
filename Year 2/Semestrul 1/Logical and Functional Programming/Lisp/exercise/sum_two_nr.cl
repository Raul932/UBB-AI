(
    defun rvs(L)
    (
        cond
        ((null L) nil)
        (t
            (append (rvs (cdr L)) (list (car L)))
        )
    )
)
(
    defun sum(L1 L2)
    (
        cond
        ((null L1) L2)
        ((null L2) L1)
        ((< (+ (car L1) (car L2)) 9) 
         (append (list (+ (car L1) (car L2))) (sum (cdr L1) (cdr L2)))
        )
        (t
            (append (list (mod (+ (car L1) (car L2)) 10)) (sum (cdr L1) (append (list (+ 1 (cadr L2))) (cddr L2))))
        )
    )
)
(
    defun sum_numbers(L1 L2)
    (
        rvs (sum (rvs (append (list 0) L1)) (rvs (append (list 0) L2)))
    )
)
(write (sum_numbers '(7 2 3) '(4 5 6 7)))