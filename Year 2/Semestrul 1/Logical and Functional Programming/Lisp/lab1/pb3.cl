(
    defun product(L1 L2)
    (
        cond
        ((null L1) 0)
        (t
            (+ (* (car L1) (car L2)) (product (cdr L1) (cdr L2)))
        )
    )
)
; (write (product '(1 2 3) '(4 5 2)))

(
    defun depth(L)
    (
        cond
        ((null L) 0)
        ((listp (car L)) 
         (
            + (depth (car L)) (+ 1 (depth (cdr L)))
         )
        )
        (t
            (depth (cdr L))
        )
    )
)
; (write (depth '(1 2 (3 4 (((5)) 6)) 7)))

(
    defun mergesort(L)
    (labels ((
        mergs(left right)
        (
            cond
            ((null left) right)
            ((null right) left)
            ((< (car left) (car right)) (append (list(car left)) (mergs (cdr left) right)))
            ((> (car left) (car right)) (append (list(car right)) (mergs left (cdr right))))
            (t
                ; (append (list(car left)) (mergs (cdr left) (cdr right)))
                (cons (car left) (mergs (cdr left) (cdr right)))
            )
        )
    )
    (
        split(L mid pos fst)
        (
            cond
            ((equal pos mid) (list (reverse fst) L))
            (t 
                (split (cdr L) mid (+ 1 pos) (cons (car L) fst))
            )
        )
    )
    (
        srts(L)
        (
            cond
            ((< (length L) 2) L)
            ((= (length L) 2) 
             (if (< (car L) (cadr L)) L
                (list (cadr L) (car L))
             )
            )
            (t
                (let ((aux (split L (floor (length L) 2) 0 '())))
                    (mergs (srts (car aux)) (srts (cadr aux)))
                )
            )
        )
    )
    )
        (srts L)
    )
)
(
    defun insertionsort(L)
    (labels ((
        insert(L elem)
        (
            cond
            ((null L) (list elem))
            ((<= elem (car L)) (cons elem L))
            (t
                (cons (car L) (insert (cdr L) elem))
            )
        )
    )
    (
        srt(L)
        (
            cond
            ((null L) ())
            (t
                (insert (srt (cdr L)) (car L))
            )
        )
    )
    )
        (srt L)
    )
)
; (write (mergesort '(9 2 3 7 8 1 5 6 8 4)))
; (write (insertionsort '(9 2 3 7 8 1 5 6 8 4)))

(
    defun intersect(L1 L2)
    (labels((
        membs(L elem)
        (
            cond
            ((null L) nil)
            ((equal (car L) elem) t)
            (t
                (membs (cdr L) elem)
            )
        )
    )
    (
        intsct(L1 L2)
        (
            cond
            ((null L1) nil)
            ((membs L2 (car L1)) (cons (car L1) (intsct (cdr L1) L2)))
            (t
                (intsct (cdr L1) L2)
            )
        )
    )
    )
        (intsct L1 L2)
    )
)
(write (intersect '(1 2 3 4) '(2 7 3 4 5 6)))