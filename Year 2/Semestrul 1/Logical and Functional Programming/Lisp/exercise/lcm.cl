(
    defun my_gcd(a b)
    (
        cond
        ((= a 0) b)
        ((= b 0) a)
        ((= a b) a)
        ((> a b) (my_gcd (- a b) b))
        (t (my_gcd a (- b a)))
    )
)
(
    defun list_gcd(L)
    (
        cond
        ((= 1 (length L)) (car L))
        ((atom (car L)) (my_gcd (car L) (list_gcd (cdr L))))
        (t
           (my_gcd (list_gcd (car L)) (list_gcd (cdr L)))
        )
    )
)
(
    defun product(L)
    (
        cond
        ((null L) 1)
        ((atom (car L)) (* (car L) (product (cdr L))))
        (t
            (* (product (car L)) (product (cdr L)))
        )
    )
)
(
    defun list_lcm(L)
    (
        / (product L) (list_gcd L)
    )
)
(write (list_lcm '(2 (3) 4)))