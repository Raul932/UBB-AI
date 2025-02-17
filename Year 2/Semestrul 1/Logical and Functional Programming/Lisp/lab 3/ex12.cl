(
    defun replac(tree init fin)
    (
        cond
        ((null tree) nil)
        ((atom tree)
         (
            cond
            ((equal tree init) fin)
            (t tree)
         )
        )
        (t
            (mapcar #'(lambda(X) (replac X init fin)) tree)
        )
    )
)
(write (replac '(a (b (c) b) (d) (e (f))) 'b 'o))