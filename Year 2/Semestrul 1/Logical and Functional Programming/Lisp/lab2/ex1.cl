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
    defun has-node(tree node)
    (
        cond
        ((null tree) nil)
        ((equal (car tree) node) t)
        (t
            (has-node (cdr tree) node)
        )
    )
)
(
    defun path(tree node)
    (
        cond
        ((null tree) nil)
        ((has-node (car (left-right tree)) node) (append (list (car tree)) (path (car (left-right tree)) node)))
        ((has-node (cadr (left-right tree)) node) (append (list (car tree)) (path (cadr (left-right tree)) node)))
    )
)
(write (path '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) 'g))
