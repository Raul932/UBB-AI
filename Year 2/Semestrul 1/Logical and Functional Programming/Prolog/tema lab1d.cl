(defun is-set-helper (lst seen)
  (cond
    ((null lst) t)
    ((member-p (car lst) seen) nil)
    (t (is-set-helper (cdr lst) (cons (car lst) seen)))))

(defun is-set (lst)
  (is-set-helper lst nil))