#!/bin/bash

for file in "$@"; do
    if [[ ! -f "$file" ]]; then
        echo "File not found: $file"
        continue
    fi

    sum=0
    lines_with_numbers=0
    lines_without_numbers=0

    while read -r line; do
        if [[ "$line" =~ [0-9] ]]; then
            ((lines_with_numbers++))
            for number in $(echo $line | grep -o '[0-9]\+'); do
                sum=$((sum + number))
            done
        else
            ((lines_without_numbers++))
        fi
    done < "$file"

    if [ $lines_without_numbers -eq 0 ]; then
        ratio="Infinity"
    else
        ratio="$lines_with_numbers/$lines_without_numbers"
    fi

    echo "File: $file, Sum = $sum, l1/l2 = $ratio"
done

