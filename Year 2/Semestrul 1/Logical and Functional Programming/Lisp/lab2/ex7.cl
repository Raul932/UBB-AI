(
    defun parse-left-right(tree leftnodes leftchilds left)
    (
        cond
        ((null tree) (list left tree))
        ((= leftnodes (+ 1 leftchilds)) (list left tree))
        (t
            (parse-left-right (cddr tree) (+ 1 leftnodes) (+ (cadr tree) leftchilds) (append left (list (car tree) (cadr tree))))
        )
    )
)
(
    defun left-right(tree)
    (
        parse-left-right (cddr tree) 0 0 '()
    )
)
(
    defun level-node(tree node cnt)
    (
        cond
        ((null tree) 0)
        ((equal (car tree) node) cnt)
        (t
            (+ (level-node (car (left-right tree)) node (+ 1 cnt)) (level-node (cadr (left-right tree)) node (+ 1 cnt)))
        )
    )
)
(write (level-node '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) 'h 0))