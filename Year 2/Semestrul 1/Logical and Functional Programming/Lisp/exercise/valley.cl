(
    defun valley(L direction switch)
    (
        cond
        ((or (null L) (= (length L) 1))
         (
            cond
            ((= direction 1)
             (
                cond
                ((= switch 1) t)
                (t nil)
             )
            )
            (t nil)
         )
        )
        ((> (car L) (cadr L))
         (
            cond
             ((= direction 0) (valley (cdr L) 0 1))
             (t nil)
         )
        )
        ((< (car L) (cadr L)) (valley (cdr L) 1 switch))
        (t nil)
    )
)
;(write (valley '(3 1) 0 0))

(
    defun number_to_list(num)
    (
        cond
        ((= num 0) nil)
        (t
            (append (number_to_list (floor num 10)) (list(mod num 10)) )
        )
    )
)

(
    defun get_power(num)
    (
        cond
        ((= 0 num) 1)
        (t
            (* 10 (get_power (- num 1)))
        )
    )
)

(
    defun list_to_number(L p)
    (
        cond
        ((null L) 0)
        (t
            (+  (* p (car L)) (list_to_number (cdr L) (floor p 10)))
        )
    )
)
(write (list_to_number '(1 2 3) (get_power (- (length '(1 2 3)) 1))))