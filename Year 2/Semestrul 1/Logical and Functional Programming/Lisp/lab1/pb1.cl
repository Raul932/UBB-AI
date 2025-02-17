(
    defun insert_after_even (L elem)
    (labels(
        (insert (L pos)
            (cond 
                ((null L) ())
                ((equal 0 (mod pos 2)) (cons (car L) (cons elem (insert (cdr L) (+ 1 pos)))))
                (t
                    (cons (car L) (insert (cdr L) (+ 1 pos)))
                )
            )
        )
        )

        (insert L 0)
    )
)
; (write (insert_after_even '(1 2 3 4) 2))

(
    defun extract(L) 
    (   
        cond
        ((null L) ())
        ((atom (car L)) (append (extract (cdr L)) (list(car L))))
        (t
            (append (extract (cdr L)) (extract (car L)))
        )
    )
)
;(write  (extract '(((A B) C) (D E))))

(
    defun denoms(L)
    (
        cond
        ((null L) 1)
        ((numberp (car L)) (* (car L) (denoms (cdr L))))
        (t
            (*(denoms (car L)) (denoms (cdr L)))
        )
    )
)
;(write (denoms '(2 3 4 5)))

(
    defun occurences(L elem)
    (
        cond
        ((null L) 0)
        ((atom (car L)) (
            if (equal (car L) elem)
                (+ 1 (occurences (cdr L) elem))
                (occurences (cdr L) elem)
        ))
        (t
            (+ (occurences (car L) elem) (occurences (cdr L) elem))
        )
    )
)
(write (occurences '(3 2 (4 (2)) 2 3 2) 2))