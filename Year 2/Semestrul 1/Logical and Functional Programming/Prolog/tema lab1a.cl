(defun delete-nth (n lst)
  (cond
    ((null lst) nil)
    ((zerop n) (cdr lst)) 
    (t (cons (car lst) (delete-nth (1- n) (cdr lst))))))