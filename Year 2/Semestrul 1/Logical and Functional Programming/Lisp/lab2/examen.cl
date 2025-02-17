(
    defun parse-left-right(tree leftnodes leftchilds left) ;splits the tree into the left subtree and right subtree
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
    defun left-right(tree) ;wrapper
    (
        parse-left-right (cddr tree) 0 0 '()
    )
)

(
    defun width(tree)
    (
        
    )
)





;   A
; B   F
;C E G H
;D     I