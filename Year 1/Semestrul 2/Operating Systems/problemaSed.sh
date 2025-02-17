#!/bin/bash

#luam fiecare fila pe rand si cautam in ea
for file in "$@"
do
    #aici verificam daca exista fisierul respectiv, daca nu exista trecem la else
    if [[ -f "$file" ]]; then
        #aici modificam textul
        sed -i 's/^\([^:]*\):\([^:]*\):\([^:]*\)/\1:\2:\1/' "$file"
    else
        echo "$file does not exist."
    fi
done
