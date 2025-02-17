(
    defun multi-level-list (lst maxi)
    (
        cond
            ((null lst) maxi)
            ((atom lst)(
                cond
                ((< maxi lst) lst)
                (t maxi)
            ))
            (t (apply #'max (mapcar #'(lambda(lst) (multi-level-list lst maxi)) lst))
            )
    )
)

(
    defun depth (lst)
    (
        cond
        ((null lst) 1)
        ((atom lst) 0)
        (t
            (+ 1 (apply #'max(mapcar #'depth lst)))
        )
    )
)

(
    defun atoms (lst)
    (
        cond 
        ((null lst) nil)
        ((atom lst) (list lst))
        (t
            (mapcan #'atoms lst) 
        )
    )
)

(
    defun check (lst elem)
    (
        cond 
        ((null lst) nil)
        ((atom lst)
        (
            cond
            ((equal lst elem) t)
            (t nil)
        )
        )
        (t
            (mapcan #'(lambda(lst) (check lst elem)) lst) 
        )
    )
)

(
    defun sum(lst)
    (
        cond 
        ((null lst) 0)
        ((atom lst) lst)
        (t
            (apply #'+ (mapcar #'sum lst))
        )
    )
)

(
    defun checknode(tree node)
    (
        cond
        ((null tree) nil)
        ((atom tree)
            (
            cond
            ((equal tree node) (list t))
            (t nil)
            )
        )
        (t
            (mapcan #'(lambda(tree) (checknode tree node)) tree)
        )
    )
)

(
    defun product(lst)
    (
        cond
        ((null lst) 1)
        ((atom lst) lst)
        (t
            (apply #'* (mapcar #'(lambda(lst)(product lst)) lst))
        )
    )
)

(
    defun diff-even-odd(lst)
    (
        cond
        ((null lst) nil)
        ((atom lst)
            (
            cond
            ((= (mod lst 2) 0) lst)
            (t (* lst -1))
            )
        )
        (t
            (apply #'+ (mapcar #'diff-even-odd lst))
        )
    )
)
(
    defun maxim(lst maxi)
    (
        cond
        ((null lst) maxi)
        ((atom lst)
        (
            cond
            ((> lst maxi) lst)
            (t maxi)
        )
        )
        (t
            (apply #'max (mapcar #'(lambda(lst)(maxim lst maxi)) lst))
        )
    )
)

(
    defun subs(lst elem lst1)
    (
        cond
        ((null lst) nil)
        ((atom lst)
        (
            cond
            ((equal lst elem) lst1)
            (t lst)
        )
        )
        (t
            (mapcar #'(lambda(lst)(subs lst elem lst1)) lst)
        )
    )  
)

(
    defun nodes-at-level-k(tree k cnt)
    (
        cond
        ((null tree) 0)
        ((atom tree)
         (
            cond
            ((= k cnt) 1)
            (t 0)
         )
        )
        (t
            (reduce #'+(mapcar #'(lambda(tree)(nodes-at-level-k tree k (+ 1 cnt))) tree ))
        )
    )
)

(
    defun delete-atom(lst elem)
    (
        cond
        ((null lst) nil)
        ((atom lst)
         (
            cond
            ((not(equal lst elem)) (list lst))
            (t nil)
         )
        )
        (t
            (remove nil (mapcar #'(lambda(X) (delete-atom X elem)) lst))
        )
    )
)

(
    defun subs-node(tree node repl)
    (
        cond
        ((null tree) nil)
        ((atom tree)
         (
            cond
            ((equal tree node) repl)
            (t tree)
         )
        )
        (t
            (mapcar #'(lambda(tree)(subs-node tree node repl)) tree)
        )
    )
)

(
    defun subs-elem(lst elem repl)
    (
        cond
        ((null lst) nil)
        ((atom lst)
        (
            cond
            ((equal lst elem) repl)
            (t lst)
        )
        )
        (t
            (mapcar #'(lambda(lst)(subs-elem lst elem repl)) lst)
        )
    )
)

(
    defun height(tree cnt)
    (
        cond
        ((null tree) 0)
        ((atom tree) cnt)
        (t
            (apply #'max (mapcar #'(lambda (X)(height X (+ 1 cnt))) tree))
        )
    )
)

(
    defun nr-atoms(lst)
    (
        cond
        ((null lst) 0)
        ((atom lst) 1)
        (t
            (apply #'+ (mapcar #'(lambda(x)(nr-atoms x)) lst))
        )
    )
)

(
    defun replace-odd(tree elem lvl)
    (
        cond
        ((null tree) nil)
        ((atom tree)
         (
            cond
            ((= (mod lvl 2) 0) elem)
            (t tree)
         )
        )
        (t
            (mapcar #'(lambda(x)(replace-odd x elem (+ 1 lvl))) tree)
        )
    )
)

(
    defun replace-k(lst k lvl)
    (
        cond
        ((null lst) nil)
        ((atom lst)
         (
            cond
            ((= k lvl) 0)
            (t lst)
         )
        )
        (t
            (mapcar #'(lambda(x)(replace-k x k (+ 1 lvl))) lst)
        )
    )
)

(
    defun multiply-by-level(tree lvl)
    (
        cond
        ((null tree) nil)
        ((atom tree)
         (
            cond
            ((numberp tree) (* tree lvl))
            (t tree)
         )
        )
        (t
            (mapcar #'(lambda(x)(multiply-by-level x (+ 1 lvl))) tree)
        )
    )
)

(
    defun list-level-k(tree k lvl)
    (
        cond
        ((null tree) nil)
        ((atom tree)
         (
            cond
            ((= k lvl) (list tree))
            (t nil)
         )
        )
        (t
            (mapcan #'(lambda(x)(list-level-k x k (+ 1 lvl))) tree)
        )
    )
)
(
    defun h()
    (
        function (lambda(x)(append x (mapcar #'(lambda(l)(car l)) x)))
    )
)
(
    defun Fc(x)
    #'(lambda(y)(mapcar y (cddr x)))
)

(
    defun replace-e-odd(tree e e1 lvl)
    (
        cond
            ((null tree) nil)
            ((atom tree)
             (
                cond
                ((= (mod lvl 2) 0) 
                 (
                    cond
                    ((equal e tree) e1)
                    (t tree))
                 )
                 (t tree)
                )
             )
            (t
                (mapcar #'(lambda(x)(replace-e-odd x e e1 (+ lvl 1))) tree)
            )
    )
)

(defun max-size (tree size)
  (cond
    ((null tree) size)                       ; If tree is null, return size
    ((atom tree) (+ size 1))                 ; If tree is an atom, increment size by 1
    (t                                       ; Otherwise, process the list
                    ; Include the current node in the size
            (apply #'max(mapcar #'(lambda (x) (max-size x (+ 1 size))) tree)))))
;(write (multi-level-list '(((1 2) 3) (4 5) 2) 0))
;(write (depth '(1 (2 (3 (4 (5)) 6)))))
;(write (atoms '(A (B) C (D (E)))))
;(write (check '(A (B) C (D (E))) 'D))
;(write (sum '(1 (2 (3 (4 (5)) 6)))))
;(write (checknode '(A (B (C)) (D) (E (F))) 'G))
;(write (product '(1 (2 (3 (4 (5)) 6)))))
;(write (diff-even-odd '(1 (2 (3 (4 (5)) 6)))))
;(write (maxim '(((1 2) 3) (4 5) 2) 0))
;(write (subs '(A (B) C (D (E))) 'C '(D D (E))))
;(write (nodes-at-level-k '(a (b (c)) (d) (e (f))) 2 0))
;(write (delete-atom '(a b (c (d (e e) c) b)) 'e))
;(write (subs-node '(A (B) C (D (E))) 'E 'F))
;(write (subs-elem '(A (B) C (D (E))) 'E 'F))
;(write (height '(a (b (c)) (d) (e (f))) 0))
;(write (nr-atoms '(A (B) C (D (E)))))
;(write (replace-k '(a (b (g))(c (d (e))(f))) 4 0))
;(write (multiply-by-level '(1 (2 (g))(12 (3 (e))(55))) 0))
;(write (list-level-k '(a (b (g))(c (d (e))(f))) 3 0))
;(write (funcall (h) '((2 3)(4 5))))
;(write (funcall (Fc (QUOTE ((1) (2) (3) (4) (5)))) #'car))
;(write (replace-e-odd '(a (b (g))(c (d (b))(b))) 'b 'z 0))
(write (max-size '(A (B (C)) (D) (E (F))) 0))