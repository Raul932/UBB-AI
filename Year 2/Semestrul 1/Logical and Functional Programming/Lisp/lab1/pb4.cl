(
    defun revers1(L)
    (
        cond
        ((null L) ())
        ((atom (car L)) (append(revers1 (cdr L)) (list(car L))))
        (t
            (cons (revers1 (car L)) (revers1 (cdr L)))
        )
    )
) ; basically 9b
;(write (revers1 '(A B C (D (E F) G H I))))

(defun revers2 (L)
    (labels ((
        acc (lst aux)
        (
            cond
            ((null lst) aux)
            ((atom (car lst)) (acc (cdr lst) (cons (car lst) aux)))
            (t
                (append aux (list (revers2 (car lst))) (acc (cdr lst) '()))
            )
        )
    ))
        (acc L '())
    )   
)
;(write (revers2 '(A B C (D (E F) G H I))))

(
    defun top-level-max(L)
    (labels((
        maxim(L maxi)
        (
            cond
            ((null L) maxi)
            ((atom (car L)) (
                cond
                ((< maxi (car L)) (maxim (cdr L) (car L)))
                (t (maxim (cdr L) maxi))
            ))
            (t
                (maxim (car L) maxi)
                (maxim (cdr L) maxi)
            )
        )
    ))
        (maxim L 0)
    )
)
(write (top-level-max '(4 3 1 2 (5))))