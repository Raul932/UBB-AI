(defun member-p (x lst)
  (cond
    ((null lst) nil)
    ((equal x (car lst)) t)
    (t (member-p x (cdr lst)))))

(defun append-unique (x lst)
  (if (member-p x lst)
      lst
      (cons x lst)))

(defun atoms-set (lst)
  (cond
    ((null lst) nil)
    ((atom (car lst)) (append-unique (car lst) (atoms-set (cdr lst))))
    (t (atoms-set (append (car lst) (cdr lst))))))