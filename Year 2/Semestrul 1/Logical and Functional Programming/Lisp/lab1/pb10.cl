; (+ * 2 4 - 5 * 2 2)
;(+ (* 2 4) (- 5 (* 2 2)))
(
    defun compute_expr(L)
    (labels((
        get_expr(L expr)
        (
            cond
            ((null L) expr)
            ((not(numberp (car L))) 
             (
                if (and (numberp (cadr L)) (numberp (caddr L)))
                    (
                        cond
                        ((equal '+ (car L)) (get_expr (cdddr L) (append (list (+ (cadr L) (caddr L))) expr)))
                        ((equal '- (car L)) (get_expr (cdddr L) (append (list (- (cadr L) (caddr L))) expr)))
                        ((equal '* (car L)) (get_expr (cdddr L) (append (list (* (cadr L) (caddr L))) expr)))
                        ((equal '/ (car L)) (get_expr (cdddr L) (append (list (/ (cadr L) (caddr L))) expr)))
                    )
                    (get_expr (cdr L) (append (list (car L)) expr))
             )
            )
            (t
                (get_expr (cdr L) (append (list (car L)) expr))
            )
        )
    )
    (
        repeat_computing(expr)
        (
            cond
            ((equal 1 (length expr)) expr)
            (t
                (repeat_computing (get_expr (reverse expr) '()))
            )
        )
    )
    )
        (repeat_computing (get_expr L '()))
    )
)
(write (compute_expr '(+ * 2 4 - 5 * 2 2)))