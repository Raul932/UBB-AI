(
    defun parse_left_right(tree leftnodes leftchild left)
    (
        cond
        ((null tree) (list left  tree))
        ((= leftnodes (+ 1 leftchild)) (list left tree))
        (t 
            (parse_left_right (cddr tree) (+ 1 leftnodes) (+ (cadr tree) leftchild) (append left (list (car tree) (cadr tree))))
        )
    )
)

(
    defun left_right(tree)
    (parse_left_right (cddr tree) 0 0 '())
)

(
    defun inorder(tree)
    (
        cond
        ((null tree) nil)
        (t
            (append (inorder (car (left_right tree))) (list(car tree)) (inorder (cadr (left_right tree))))
        )

    )
)

(
    defun postorder(tree)
    (
        cond
        ((null tree) nil)
        (t
            (append (postorder (car (left_right tree))) (postorder (cadr (left_right tree))) (list (car tree)))
        )
    )
)

(
    defun preorder(tree)
    (
        cond
        ((null tree) nil)
        (t
            (append (list (car tree)) (preorder (car (left_right tree))) (preorder (cadr (left_right tree))))
        )
    )
)

(
    defun convert (tree)
    (
        cond
        ((null tree) nil)
        (t
            (
                append (list (car tree))                             
                (if (car (left_right tree)) (list (convert (car (left_right tree)))) '())        
                (if (cadr (left_right tree)) (list (convert (cadr (left_right tree)))) '())
            )
        )
    )
)

(write (convert '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0)))