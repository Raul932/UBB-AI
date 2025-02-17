(
    defun zig_zag(L direction count)
    (
        cond
        ((null L) t)
        ((= 1 (length L)) count)
        ((= direction 1)
         (
            cond
            ((> (car L) (cadr L)) (zig_zag (cdr L) 0 (+ 1 count)))
            (t nil)
         )
        )
        (t
            (
                cond
                ((< (car L) (cadr L)) (zig_zag (cdr L) 1 (+ 1 count)))
                (t nil)
            )
        )
    )
)
(
    defun check_zig_zag(L)
    (
        cond
        ((< (length L) 2) t)
        ((< (car L) (cadr L))
         (
            cond
            ((null (zig_zag (cdr L) 1 0)) nil)
            (t
             (
                cond
                ((= (- (length L) 2) (zig_zag (cdr L) 1 0)) t)
                (t nil)
             )
            )
         )
        )
        (t
            (
                cond
                ((null (zig_zag (cdr L) 0 0)) nil)
                (t
                 (
                    cond
                    ((= (- (length L) 2) (zig_zag (cdr L) 0 0)) t)
                    (t nil)
                 )
                )
            )
        )
    )
)
(write (check_zig_zag '(1 3 2 4 3 5)))