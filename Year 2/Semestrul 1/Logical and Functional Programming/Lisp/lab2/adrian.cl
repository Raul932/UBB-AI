(
    defun inorder(tree)
    (
        cond
        ((null tree) nil)
        (t
            (append (inorder (cadr tree)) (list (car tree)) (inorder (caddr tree)))
        )
    )
)
(
    defun preorder(tree)
    (
        cond
        ((null tree) nil)
        (t
            (append (list (car tree)) (preorder (cadr tree)) (preorder (caddr tree)))
        )
    )
)
(
    defun has_node(tree node)
    (
        cond
        ((null tree) nil)
        ((equal (car tree) node) t)
        (t
            (or (has_node (cadr tree) node) (has_node (caddr tree) node))
        )  
    )
)
(
    defun path(tree node)
    (
        cond
        ((null tree) nil)
        ((equal node (car tree)) (list node))
        ((has_node (cadr tree) node) (append (list (car tree)) (path (cadr tree) node)))
        ((has_node (caddr tree) node) (append (list (car tree)) (path (caddr tree) node)))
    )
)
(
    defun depth(tree)
    (
        cond
        ((null tree) 0)
        (t
            (+ 1 (max (depth (cadr tree)) (depth (caddr tree))))
        )
    )
)



(write (depth '(A (B) (C (D) (E)))))