(
    defun nthelem(L n)
    (labels((
        selectnth(L n k)
        (
            cond
            ((null L) nil)
            ((equal n k) (car L))
            ((> n k) (selectnth (cdr L) n (+ 1 k)))
            (t
                nil
            )
        )
    ))
    (selectnth L n 1)
    )
)
;(write (nthelem '(1 2 3 4 5 6 7 8 9) 9))

(
    defun membs(L elem)
    (
        cond
        ((null L) nil)
        ((atom (car L))
          (
            if (equal (car L) elem)
                t
                (membs (cdr L) elem)
          )
        )
        (t
            (membs (car L) elem)
            (membs (cdr L) elem)
        )
    )
)
;(write (membs '(1 2 3 4 5) 5))

(
    defun sublst (L)
    (labels ((
        sublist(L)
        (
            cond
            ((null L) ())
            ((listp (car L)) 
                (
                    append (list(car L)) (sublist (cdr L))
                    (sublist (car L))
                )
            )
            (t
                (sublist (cdr L))
            )
        )
    ))
        (append (list L) (sublist L))
    )
    
)
; (write (sublst '(1 2 (3 (4 5) (6 7)) 8 (9 10))))

(
    defun makeset(L)
    (labels((
        sets(L ans)
        (
            cond
            ((null L) ans)
            ((not(membs ans (car L))) (sets (cdr L) (append (list(car L)) ans)))
            (t
                (sets (cdr L) ans)
            )
        )
    ))
        (sets L ())
    )
)
(write (makeset '(1 2 2 3 2 4 4 5 5 5 4 2 1)))
