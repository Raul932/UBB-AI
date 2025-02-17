(defun add-carry (lst carry)
  (cond
    ((null lst) (if (zerop carry) nil (list carry)))
    (t (let* ((sum (+ (car lst) carry))
              (digit (mod sum 10))
              (new-carry (floor sum 10)))
         (cons digit (add-carry (cdr lst) new-carry))))))

(defun list-successor (lst)
  (reverse (add-carry (reverse lst) 1)))