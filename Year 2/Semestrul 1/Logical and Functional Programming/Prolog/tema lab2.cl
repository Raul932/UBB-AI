(defun convert-tree (tree)
  (if (null tree)
      nil
      (let ((node (car tree))            
            (no-subtrees (cadr tree))     
            (rest (cddr tree)))          
        (cons node (convert-subtrees rest no-subtrees)))))

(defun convert-subtrees (subtrees n)
  (if (zerop n)
      nil
      (let* ((converted (convert-tree subtrees))
             (remaining (skip-subtree subtrees)))
        (cons converted (convert-subtrees remaining (1- n))))))

(defun skip-subtree (tree)
  (if (null tree) 
      nil
      (let ((no-subtrees (cadr tree)))
        (skip-n-subtrees (cddr tree) no-subtrees))))

(defun skip-n-subtrees (tree n)
  (if (zerop n) 
      tree
      (skip-n-subtrees (skip-subtree tree) (1- n))))